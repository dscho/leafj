package sandbox;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.util.Vector;

public class practiceTable{
	private JFrame frame = new JFrame("PracticeTable");
	private JPanel buttonPanel = new JPanel();
	private JTable table = new JTable(5,5);


	public practiceTable() {
		for(int i = 1; i < 5; i++) {
			table.setValueAt(i, i, 1);
		}
		table.setFillsViewportHeight(true);
		table.setGridColor(java.awt.Color.BLACK);
		table.setShowGrid(true);
	}


	public JPanel makeButtonPanel() {
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		JButton addRowButton = new JButton("Add Row");
		addRowButton.addActionListener(new addRowListener());
		JButton addColumnButton = new JButton("Add Column");
		addColumnButton.addActionListener(new addColListener());

		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new doneListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelListener());
		
		
		JTextArea info = new JTextArea("Each column represents one type of annotation\n" + 
										"place the name for the data type in the first row\n" +
										"if you want to have a pull-down list of allowable data\n" +
										"then place allowable values in subsequent rows\n" +
										"otherwise leave blank and a text entry field will be presented\n");
		info.setEditable(false);
		buttonPanel.add(info);
		buttonPanel.add(addRowButton);
		buttonPanel.add(addColumnButton);
		buttonPanel.add(doneButton);
		buttonPanel.add(cancelButton);
		return(buttonPanel);
	}

//	final Runnable doPanel = new Runnable() {
//		public void run() {
	public void doPanel() {
			System.out.println("start of doPanel");
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setOpaque(true);
			JPanel buttonPanel = makeButtonPanel();
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
			System.out.println("end of doPanel");
		}
//	};
	

	public static void main(String[] args){
		practiceTable pt = new practiceTable();
		pt.doPanel();
		System.out.println("end of main");
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
