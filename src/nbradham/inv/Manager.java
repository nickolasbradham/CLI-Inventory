package nbradham.inv;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

final class Manager {

	private static final String[] DATA_TYPES = new String[] { "Boolean", "Int", "Float", "String" };

	private void start() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Inventory");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			JMenuBar bar = new JMenuBar();
			JMenu fileMenu = new JMenu("File"), typeMenu = new JMenu("Type List");
			JMenuItem fileNew = new JMenuItem("New"), fileOpen = new JMenuItem("Open"),
					fileSave = new JMenuItem("Save"), typeAdd = new JMenuItem("Add Type"),
					typeDel = new JMenuItem("Delete Type");
			fileMenu.add(fileNew);
			fileMenu.add(fileOpen);
			fileMenu.add(fileSave);
			bar.add(fileMenu);

			JComboBox<ItemTable> type = new JComboBox<>();
			typeAdd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog diag = new JDialog(frame, true);
					diag.setTitle("New Item");
					diag.setLayout(new BoxLayout(diag.getContentPane(), BoxLayout.PAGE_AXIS));
					JPanel pane = new JPanel(new GridLayout(0, 2));
					pane.add(new JLabel("Type Name:", JLabel.RIGHT));
					JTextField nameField = new JTextField();
					pane.add(nameField);
					pane.add(new JLabel("Data Fields:", JLabel.RIGHT));
					JTable fields = new JTable(new DefaultTableModel(new String[] { "Field", "Type" }, 0));
					JComboBox<String> combo = new JComboBox<>(DATA_TYPES);
					combo.setSelectedIndex(0);
					fields.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
					JSpinner spin = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
					spin.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							int sv = (int) spin.getValue();
							DefaultTableModel model = (DefaultTableModel) fields.getModel();
							while (sv > fields.getRowCount())
								model.addRow(new Object[] { "", DATA_TYPES[0] });
							while (sv < fields.getRowCount())
								model.removeRow(model.getRowCount() - 1);
						}
					});
					pane.add(spin);
					diag.add(pane);
					diag.add(new JScrollPane(fields));
					JButton ok = new JButton("OK");
					ok.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							diag.dispose();
							// TODO Add item.
						}
					});
					diag.add(ok);
					diag.pack();
					diag.setVisible(true);
				}
			});
			typeMenu.add(typeAdd);

			typeMenu.add(typeDel);
			bar.add(typeMenu);
			frame.setJMenuBar(bar);

			DefaultTableModel dtmA = new DefaultTableModel(2, 3);
			JTable table = new JTable(dtmA);

			JPanel opts = new JPanel();
			opts.add(new JLabel("Type:"));
			opts.add(type);
			frame.add(opts, BorderLayout.PAGE_START);

			frame.add(new JScrollPane(table), BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);
		});
	}

	public static void main(String[] args) {
		new Manager().start();
	}
}