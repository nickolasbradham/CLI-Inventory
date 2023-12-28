package nbradham.inv;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class Manager {
	private void start() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			JTextArea box = new JTextArea("Ready.");
			box.setEditable(false);
			frame.add(new JScrollPane(box), BorderLayout.CENTER);
			JTextArea field = new JTextArea();
			field.addKeyListener(new KeyAdapter() {
				private final HashMap<String, Category> catagories = new HashMap<>();
				private final ArrayList<String> hist = new ArrayList<>();
				private String s;
				int com, pos;

				@Override
				public final void keyTyped(KeyEvent e) {
					switch (e.getKeyChar()) {
					case '\n':
						println(s = field.getText());
						field.setText("");
						hist.add(s.trim());
						pos = -1;
						switch (nextChar()) {
						case 'a':
							if ((s = token()).isBlank()) {
								println("Must specify a category name.");
								break;
							}
							if (catagories.containsKey(s))
								println("Category already exists.");
							else
								catagories.put(s, new Category());
							println(catagories.toString());
							break;
						case 'h':
							switch (nextChar()) {
							case 'a':
								println("(a)dd - Adds a category of item.\n  Usage: a<newCatagory>");
								break;
							case 'h':
								println("(h)elp - Gives help on commands.\n  Usage: h[command]");
								break;
							case 'q':
								println("(q)uit - Quits the program.");
								break;
							case '\n':
								println("Commands are single characters and extra flags are not seperated by spaces.\nParameters are split by spaces and quotes are used to enter strings containing spaces.\nUse h[command] for details on a specific command.\n  Example: 'hq' shows help on the quit command.\nAvailable commands:\n  (a)dd - Adds an item category.\n  (h)elp - Shows this prompt.\n  (q)uit - Quits the program.");
								break;
							default:
								println("(Help) Unknown command.");
							}
							break;
						case 'q':
							frame.dispose();
							break;
						default:
							println("Unknown command. Use 'h' for help.");
						}
						box.setCaretPosition(box.getDocument().getLength());
						break;
					case '\t':
						// TODO: Auto complete.
					}
					com = hist.size();
				}

				@Override
				public final void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (com > 0)
							field.setText(hist.get(--com));
						break;
					case KeyEvent.VK_DOWN:
						if (com < hist.size() - 1)
							field.setText(hist.get(++com));
					}
				}

				private char nextChar() {
					return s.charAt(++pos);
				}

				private String token() {
					int i;
					return s.charAt(++pos) == '\'' ? s.substring(++pos, pos = s.indexOf('\'', pos))
							: s.substring(pos++, pos = (i = s.indexOf(' ', pos)) > -1 ? i : s.length());
				}

				private void println(String str) {
					box.append("\n");
					box.append(str.trim());
				}
			});
			frame.add(field, BorderLayout.PAGE_END);
			frame.pack();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
			field.requestFocus();
		});
	}

	public static void main(String[] args) {
		new Manager().start();
	}
}