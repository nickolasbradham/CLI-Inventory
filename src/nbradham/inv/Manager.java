package nbradham.inv;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import nbradham.inv.dataFields.BooleanField;
import nbradham.inv.dataFields.DataField;
import nbradham.inv.dataFields.DateField;
import nbradham.inv.dataFields.FloatField;

public final class Manager {

	static final Scanner SCAN = new Scanner(System.in);

	private final HashMap<String, ItemCategory> cats = new HashMap<>();

	private void start() {
		System.out.println(DataField.TYPES);
		Menu main = new Menu("Inventory Manager",
				new Option("Edit Item Categories",
						() -> new Menu(() -> String.format("Edit Item Categories%n%s%n", cats.keySet()),
								new Option("New Category", () -> {
									String iName = getStr("Enter Item Name (leave empty to cancel):",
											s -> !cats.containsKey(s), "That name already exists. Retry:");
									if (iName.isEmpty())
										return;
									ItemCategory ic = new ItemCategory(iName);
									cats.put(iName, ic);
									new Menu(String.format("Edit Category (%s)", iName),
											new Option("Edit Data Fields", () -> {
												DataField df = null;
												HashMap<String, DataField> fields = ic.fields();
												new Menu(() -> {
													StringBuilder sb = new StringBuilder("Edit Data Fields (")
															.append(ic.name())
															.append(")\n  Field  Type  Default  Restrictions\n------------------------------------\n");
													fields.forEach((k,
															v) -> sb.append(String.format("%c %-6s %-5s %-8s %s%n",
																	v == df ? '*' : ' ', k, v.type(), v.defValStr(),
																	v.restrictions())));
													return sb.toString();
												}, new Option("New Field", () -> {
													String fName = getStr("Enter Field Name (leave empty to cancel):",
															s -> !fields.containsKey(s),
															"That fields already exists. Retry:");
													if (fName.isEmpty())
														return;
													DataField field = null;
													switch (getStr("Enter Field Type [bool, date, float]:",
															s -> DataField.TYPES.contains(s), "Invalid Type. Retry:")) {
													case BooleanField.TYPE:
														field = new BooleanField(getStr("Enter Default Value (f/t):",
																s -> s.equals("t") | s.equals("f"),
																"Invalid value. Retry:").equals("t"));
														break;
													case DateField.TYPE:
														DateGetter dgMin = DateField.getDateGetter(() -> LocalDate.MIN,
																"Minimum", s -> true),
																dgMax = DateField.getDateGetter(() -> LocalDate.MAX,
																		"Maximum", s -> {
																			LocalDate d = LocalDate
																					.parse((CharSequence) s);
																			return d.isAfter(dgMin.getDate())
																					|| d.isEqual(dgMin.getDate());
																		});
														field = new DateField(dgMin, dgMax, DateField
																.getDateGetter(DateField.DG_CUR, "Default", s -> {
																	LocalDate d = LocalDate.parse((CharSequence) s);
																	return (d.isAfter(dgMin.getDate())
																			|| d.isEqual(dgMin.getDate()))
																			&& (d.isBefore(dgMax.getDate())
																					|| d.isEqual(dgMax.getDate()));
																}));
														break;
													case FloatField.TYPE:
														float min = getFlt("Minimum", Float.NEGATIVE_INFINITY,
																f -> true),
																max = getFlt("Maximum", Float.POSITIVE_INFINITY,
																		f -> (float) f >= min);
														field = new FloatField(min, max, getFlt("Default", 0,
																f -> (float) f >= min && (float) f <= max));
														// TODO Continue.
													}
													fields.put(fName, field);
												})).open();
											})).open();
								})).open()));
		main.open();
		SCAN.close();
	}

	public static String getStr(String prompt, Validator v, String retry) {
		System.out.println(prompt);
		String name;
		while (!v.isValid(name = SCAN.nextLine()))
			System.out.println("Invalid value. Retry:");
		return name;
	}

	private static float getFlt(String prompt, float defaultVal, Validator v) {
		String str = getStr(String.format("Enter %s value (leave blank for default [%f]):", prompt, defaultVal), s -> {
			try {
				float f = Float.parseFloat((String) s);
				return v.isValid(f);
			} catch (NumberFormatException e) {
				return ((String) s).isEmpty();
			}
		}, "Invalid value. Retry:");
		return str.isEmpty() ? defaultVal : Float.parseFloat(str);
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length > 0 && !args[0].equals("nocon")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Manager().start();
	}
}