package nbradham.inv;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class Manager {

	private void start() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Inventory Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
			JTextArea box = new JTextArea("Ready.", 20, 50);
			box.setEditable(false);
			box.setBackground(Color.BLACK);
			box.setForeground(Color.WHITE);
			box.setLineWrap(true);
			box.setWrapStyleWord(true);
			frame.add(new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
			JTextArea entryField = new JTextArea(), optField = new JTextArea();
			entryField.addKeyListener(new KeyAdapter() {
				private static final String[] COMMANDS = { "Type", "Item", "Help", "Test" };
				private final ArrayList<String> hist = new ArrayList<>();
				private String str0;
				int com;

				@Override
				public final void keyTyped(KeyEvent e) {

					switch (e.getKeyChar()) {
					case '\n' -> {
						println(str0 = entryField.getText());
						entryField.setText("");
						hist.add(str0);
						System.out.printf("Submit: %s%n", str0);
					}
					case '\t' -> {
						System.out.println("Tabbed.");
						int pos = entryField.getCaretPosition() - 1;
						String text = entryField.getText();
						entryField.setText(text.substring(0, pos) + text.substring(pos + 1));
						entryField.setCaretPosition(pos);
					}
					}
					StringBuilder optBuild = new StringBuilder("Options:");
					for (String s : COMMANDS)
						if (s.startsWith(entryField.getText()))
							optBuild.append("  ").append(s);
					optField.setText(optBuild.toString());
					com = hist.size();
				}

				@Override
				public final void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (com > 0)
							entryField.setText(hist.get(--com));
						break;
					case KeyEvent.VK_DOWN:
						if (com < hist.size() - 1)
							entryField.setText(hist.get(++com));
					}
				}

				private void println(String str) {
					box.append("\n");
					box.append(str.trim());
				}
			});
			entryField.setBackground(Color.BLACK);
			entryField.setForeground(Color.WHITE);
			entryField.setCaretColor(Color.LIGHT_GRAY);
			JLabel lab = new JLabel(">");
			lab.setForeground(Color.WHITE);
			JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pane.setBackground(Color.BLACK);
			pane.add(lab);
			optField.setBackground(Color.BLACK);
			optField.setForeground(Color.GRAY);
			pane.add(entryField);
			frame.add(pane);
			frame.add(optField);
			frame.pack();
			frame.setVisible(true);
			entryField.requestFocus();
		});
	}

	public static void main(String[] args) {
		new Manager().start();
	}
}