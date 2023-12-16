package nbradham.inv;

import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

final class Manager {

	static final Scanner SCAN = new Scanner(System.in);

	private final HashMap<String, ItemCategory> cats = new HashMap<>();

	private void start() {
		Menu main = new Menu("Inventory Manager",
				new Option("Edit Item Categories",
						() -> new Menu(() -> String.format("Edit Item Categories%n%s%n", cats.keySet()),
								new Option("Add", () -> {
									String iName = getStr("Enter Item Name (* to cancel):", s -> cats.containsKey(s),
											"That name already exists. Enter a different one (* to cancel):");
									if (iName.equals("*"))
										return;
									ItemCategory ic = new ItemCategory(iName);
									cats.put(iName, ic);
									new Menu(String.format("Edit Table (%s)", iName),
											new Option("Edit Data Fields", () -> {
												DataField df = null;
												System.out.printf(
														"Edit Data Fields (%s)%n  Field  Type  Default  Restrictions%n------------------------------------%n",
														ic.name());
												HashMap<String, DataField> fields = ic.fields();
												fields.forEach((k, v) -> System.out.printf("%c %6s %5s %9s %s%n",
														v == df ? '*' : ' ', k, v.type(), v.defVal(),
														v.restrictions()));
												new Menu("", new Option("Add", () -> {
													String fName = getStr("Enter Field Name (* to cancel):",
															s -> fields.containsKey(s),
															"That fields already exists. Enter a different one (* to cancel):");
													// TODO Continue.
												})).open();
											})).open();
								})).open()));
		main.open();
		SCAN.close();
	}

	private static String getStr(String prompt, Invalidater v, String retry) {
		System.out.println("Enter Item Name (* to cancel):");
		String name;
		while (v.notValid(name = SCAN.nextLine()))
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