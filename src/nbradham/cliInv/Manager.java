package nbradham.cliInv;

import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

final class Manager {

	private static final String NOCON = "-nocon";

	private File f;

	private void start() {
		Scanner scan = new Scanner(System.in);
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length == 0 || !args[0].equals(NOCON)) {
			JOptionPane.showMessageDialog(null, "Launch from the CLI.");
			return;
		}
		new Manager().start();
	}
}