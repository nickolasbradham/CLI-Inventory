package nbradham.cliInv;

import java.io.File;

import javax.swing.JOptionPane;

final class Manager {

	private static final String NOCON = "-nocon";

	private final File f;

	private Manager(File file) {
		f = file;
	}

	private void start() {
		if (!f.exists())
			System.out.printf("Could not find \"%s\". If you save, a new file will be created.%n", f);
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length == 0 || !args[0].equals(NOCON)) {
			JOptionPane.showMessageDialog(null, "Launch from the CLI.");
			return;
		}
		int i = args.length - 1;
		if (args.length == 0 || args[i].equals(NOCON)) {
			System.out.printf("Program arguments: [%s] <file>%n", NOCON);
			return;
		}
		new Manager(new File(args[i])).start();
	}
}