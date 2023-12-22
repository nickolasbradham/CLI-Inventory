package nbradham.inv;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JOptionPane;

public final class Manager {
	private static final HashSet<Character> SPECIALS = new HashSet<>(Arrays.asList(new Character[] { ' ', '\r' }));

	private final HashMap<String, Category> catagories = new HashMap<>();

	private void start() {
		boolean run = true;
		String s;
		while (run) {
			try {
				switch (System.in.read()) {
				case 'a':
					switch (System.in.read()) {
					case 'c':
						if (catagories.containsKey(s = token()))
							System.out.println("Category already exists.");
						else
							catagories.put(s, new Category());
						System.out.printf("'%s'%n", s);
						break;
					case '\r':
						System.out.println("Missing more input. Use 'ha' for help.");
						break;
					default:
						System.out.println("(Add) Unknown type.");
					}
					break;
				case 'h':
					switch (System.in.read()) {
					case 'h':
						System.out.println("(h)elp - Gives help on commands.\n  Usage: h[command]");
						break;
					case 'q':
						System.out.println("(q)uit - Quits the program.");
						break;
					case '\r':
						System.out.println(
								"Commands are single characters and extra flags are not seperated by spaces.\nParameters are split by spaces and quotes are used to enter strings containing spaces.\nUse h[command] for details on a specific command.\n  Example: 'hq' shows help on the quit command.\nAvailable commands:\n  (h)elp - Shows this prompt.\n  (q)uit - Quits the program.");
						break;
					default:
						System.out.println("(Help) Unknown command.");
					}
					break;
				case 'q':
					run = false;
					break;
				default:
					System.out.println("Unknown command. Use 'h' for help.");
				}
				System.in.skip(System.in.available());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String token() throws IOException {
		StringBuilder sb = new StringBuilder();
		char c, l = 0;
		if ((c = (char) System.in.read()) == '\'')
			while ((c = (char) System.in.read()) != '\'' || l == '\\')
				sb.append(l = c);
		else {
			do
				sb.append(c);
			while (!SPECIALS.contains(c = (char) System.in.read()));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length > 0 && !args[0].equals("nocon")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Manager().start();
	}
}