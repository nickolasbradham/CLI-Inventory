package nbradham.inv;

import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import nbradham.inv.dataFields.BooleanField;
import nbradham.inv.dataFields.DataField;

final class Manager {

	static final Scanner SCAN = new Scanner(System.in);

	private final HashMap<String, ItemCategory> cats = new HashMap<>();

	private void start() {
		System.out.println(DataField.TYPES);
		Menu main = new Menu("Inventory Manager",
				new Option("Edit Item Categories",
						() -> new Menu(() -> String.format("Edit Item Categories%n%s%n", cats.keySet()),
								new Option("New Category", () -> {
									String iName = getStr("Enter Item Name (* to cancel):", s -> !cats.containsKey(s),
											"That name already exists. Enter a different one (* to cancel):");
									if (iName.equals("*"))
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
																	v == df ? '*' : ' ', k, v.type(), v.defVal(),
																	v.restrictions())));
													return sb.toString();
												}, new Option("New Field", () -> {
													String fName = getStr("Enter Field Name:",
															s -> !fields.containsKey(s),
															"That fields already exists. Enter a different one:");
													DataField field = null;
													switch (getStr("Enter Field Type: ",
															s -> DataField.TYPES.contains(s),
															"Invalid Type. Enter Type:")) {
													case BooleanField.TYPE:
														field = new BooleanField(getStr("Enter Default Value (f/t):",
																s -> s.equals("t") | s.equals("f"),
																"Invalid value. Enter Default Value (f/t): ")
																.equals("t"));
														// TODO Continue.
													}
													fields.put(fName, field);
												})).open();
											})).open();
								})).open()));
		main.open();
		SCAN.close();
	}

	private static String getStr(String prompt, Validater v, String retry) {
		System.out.println(prompt);
		String name;
		while (!v.isValid(name = SCAN.nextLine()))
			System.out.println(retry);
		return name;
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length > 0 && !args[0].equals("nocon")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Manager().start();
	}
}