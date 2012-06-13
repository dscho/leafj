package sandbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import sandbox.practiceTable.addColListener;
import sandbox.practiceTable.addRowListener;
import sandbox.practiceTable.cancelListener;
import sandbox.practiceTable.doneListener;

public class OptionsTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				OptionsTable ot = new OptionsTable();
			}
		});
	}
	
	JFrame frame;
	JTable table;
	JPanel buttonPanel;
	
	public OptionsTable() {
		frame = new JFrame("Options");
		table = new JTable(5,5);
		setTable(table);
		buttonPanel = new JPanel();
		setButtons(buttonPanel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.add(scrollPane,BorderLayout.WEST);
		frame.add(buttonPanel,BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);	
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
					frame.setVisible(false);
			}
		});
	}
	
	public void setTable(JTable t) {
		t.setShowHorizontalLines(true);
		t.setShowVerticalLines(true);
		t.setGridColor(Color.BLACK);
	}
	
	public void setButtons(JPanel bp) {
		bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));
		JButton addRowButton = new JButton("Add Row");
		addRowButton.addActionListener(new addRowListener());
		JButton addColumnButton = new JButton("Add Column");
		addColumnButton.addActionListener(new addColListener());

		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new doneListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelListener());
		
		
		JTextArea info = new JTextArea("Each column represents one type of annotation.\n\n" + 
										"Place the name for the data type in the first row.\n" +
										"If you want to have a pull-down list of allowable data\n" +
										"then place allowable values in subsequent rows\n" +
										"otherwise leave blank and a text entry field will be presented\n");
		info.setEditable(false);
		bp.add(info);
		bp.add(addRowButton);
		bp.add(addColumnButton);
		bp.add(doneButton);
		bp.add(cancelButton);
	}
	
	class addRowListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("row listener");
			DefaultTableModel tmodel = (DefaultTableModel) table.getModel();
			tmodel.addRow(new Vector<Object>());
		}
	}

	class addColListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("col listener");
			TableColumn tc = new TableColumn();
			tc.setHeaderValue("new column");
			table.addColumn(tc);
		}
	}
	
	class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Done listener");
			frame.setVisible(false);
			for(int col = 0; col < table.getColumnCount();col++) {
				for(int row = 0; row < table.getRowCount();row++) {
					System.out.println(table.getValueAt(row, col));
			}
		}
		frame.dispose();
		}
	}
	
	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Cancel listener");
			frame.dispose();
		}

}
}
