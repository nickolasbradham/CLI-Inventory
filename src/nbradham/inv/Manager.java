package nbradham.inv;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

import nbradham.inv.dataFields.BooleanField;
import nbradham.inv.dataFields.DataField;
import nbradham.inv.dataFields.DateField;
import nbradham.inv.dataFields.FloatField;
import nbradham.inv.dataFields.IntField;
import nbradham.inv.dataFields.SelectionField;
import nbradham.inv.dataFields.StringField;

public final class Manager {

	static final Scanner SCAN = new Scanner(System.in);

	private final HashMap<String, ItemCategory> cats = new HashMap<>();

	private void start() {
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
															.append(")\n  Field  Data Type  Default  Restrictions\n-----------------------------------------\n");
													fields.forEach((k,
															v) -> sb.append(String.format("%c %-6s %-10s %-8s %s%n",
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
													switch (getStr(
															String.format("Enter Field Type %s:", DataField.TYPES),
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
														break;
													case IntField.TYPE:
														int iMin = getInt("Minimum", Integer.MIN_VALUE, f -> true),
																iMax = getInt("Maximum", Integer.MAX_VALUE,
																		f -> (int) f >= iMin);
														field = new IntField(iMin, iMax, getInt("Default", 0,
																i -> (int) i >= iMin && (int) i <= iMax));
														break;
													case SelectionField.TYPE:
														String[] vals = getStr(
																"Enter options split by semicolon (At least two options required):",
																s -> ((String) s).split(";").length > 1,
																"Only one option detected. Try again:").split(";");
														Arrays.sort(vals);
														field = new SelectionField(vals, getStr(String.format(
																"Enter Default value (leave blank for default [%s]):",
																vals[0]),
																s -> ((String) s).isEmpty()
																		|| Arrays.binarySearch(vals, s) > -1,
																"Invalid value. Retry:"));
														break;
													case StringField.TYPE:
														String str = getStr(
																"Enter regex restriction (leave blank for default [.*])",
																s -> {
																	try {
																		Pattern.compile((String) s);
																	} catch (PatternSyntaxException e) {
																		return false;
																	}
																	return true;
																}, "Invalid regex. Retry:"),
																regex = str.isEmpty() ? ".*" : str;
														field = new StringField(
																Pattern.compile(regex.isEmpty() ? ".*" : regex),
																getStr(String.format("Enter Default value:"),
																		s -> Pattern.matches(regex, (CharSequence) s),
																		"Invalid value. Retry:"));
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
		String str;
		while (!v.isValid(str = SCAN.nextLine()))
			System.out.println(retry);
		return str;
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

	private static int getInt(String prompt, int defaultVal, Validator v) {
		String str = getStr(String.format("Enter %s value (leave blank for default [%d]):", prompt, defaultVal), s -> {
			try {
				int i = Integer.parseInt((String) s);
				return v.isValid(i);
			} catch (NumberFormatException e) {
				return ((String) s).isEmpty();
			}
		}, "Invalid value. Retry:");
		return str.isEmpty() ? defaultVal : Integer.parseInt(str);
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length > 0 && !args[0].equals("nocon")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Manager().start();
	}
}