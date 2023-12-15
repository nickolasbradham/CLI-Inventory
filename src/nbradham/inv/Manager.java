package nbradham.inv;

import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

final class Manager {

	static final Scanner SCAN = new Scanner(System.in);

	private final HashMap<String, ItemCategory> cats = new HashMap<>();

	private void start() {
		Menu main = new Menu("Main",
				new Option("Edit Item Categories", () -> new Menu("Edit Item Categories", new Option("Add", () -> {
					System.out.println("Enter Item Name (* to cancel):");
					String name;
					while (cats.containsKey(name = SCAN.nextLine()))
						System.out.println("That name already exists. Enter a different one (* to cancel):");
					if (name.equals("*"))
						return;
					ItemCategory ic = new ItemCategory();
					cats.put(name, ic);
					// TODO Enter edit mode.
				})).open()));
		main.open();
		SCAN.close();
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length != 1 && !args[0].equals("nocon")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Manager().start();
	}
}