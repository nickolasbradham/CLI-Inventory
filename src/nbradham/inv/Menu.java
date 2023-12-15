package nbradham.inv;

import java.util.InputMismatchException;

final class Menu {
	private final String name;
	private final Option[] opts;
	private boolean run;

	Menu(String title, Option... options) {
		name = title;
		opts = new Option[options.length + 1];
		opts[0] = new Option("Back", () -> run = false);
		for (byte i = 0; i < options.length; ++i)
			opts[i + 1] = options[i];
	}

	void open() {
		run = true;
		while (run) {
			System.out.println(name);
			byte sel = -1;
			for (Option o : opts)
				System.out.printf("%d) %s%n", ++sel, o.Name());
			while ((sel = getSel()) < 0 || sel >= opts.length)
				System.out.print("Invalid selection.\n>");
			opts[sel].command().run();
		}
	}

	private static byte getSel() {
		byte s = -1;
		try {
			s = Manager.SCAN.nextByte();
		} catch (InputMismatchException e) {
		}
		Manager.SCAN.nextLine();
		return s;
	}
}