
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class OptionsTable extends JDialog {
	JButton button;
	JTable table;
	JScrollPane scrollPane;
	JPanel buttonPanel;
	JTextField colNameField;

	ArrayList<ArrayList <String>> data = new ArrayList<ArrayList<String>>();
	ArrayList<String> colNames = new ArrayList<String>();
	int rows = 0; //initial number of rows

	OptionsTable(JFrame owner) {
		super(owner, true);

		getInitialValues();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(100, 100);
		button = new JButton("OK");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		
		buttonPanel = new JPanel();
		setButtons(buttonPanel);
		
		add(scrollPane,BorderLayout.WEST);
		add(buttonPanel,BorderLayout.EAST);
		pack();

	}

	public void editTable() {
		setVisible(true);
		//		return "hello";
	}
	
	public int getNumberOfFields() {
		return colNames.size();
	}
	
	public String getFieldName(int field) {
		return colNames.get(field);
	}
	
	public String[] getFieldValues(int field) {
		//need to trim null values out of the ArrayList before returning
		ArrayList<String> returnValues = new ArrayList<String>();
		if (data.get(field).get(0) != null) { //insert a blank item first if list is not blank
											  //this causes the first item in the list to be blank
								   			  //should help prevent carry-over mistakes
			returnValues.add("");
		}
		for (String item: data.get(field)) {
			if (item != null) returnValues.add(item);
		}		
		return returnValues.toArray(new String[0]);//adding the new String[] ensures that a String[] is returned
	}

	private void getInitialValues() {

		System.out.println("Can't open defaults files; generating from scratch");
		colNames.addAll(Arrays.asList(new String[] {
				"set", "treatment","replicate","genotype","date"
		}));
		data.add(new ArrayList<String>(Arrays.asList(new String[]{// "set"
				"A","B","C","D"
		})));
		data.add(new ArrayList<String>(Arrays.asList(new String[]{// "treatment"
				"control","treated",null,null
		})));
		data.add(new ArrayList<String>(Arrays.asList(new String[]{// "replicate"
				"1","2","3","4"
		})));
		data.add(new ArrayList<String>(Arrays.asList(new String[]{// "genotype"
				"Col","Ler","Ws","Cvi"
		})));	
		data.add(new ArrayList<String>(Arrays.asList(new String[]{// "date"
				null,null,null,null
		})));
		rows=4;

	}


	private void setTable() {
		table = new JTable(rows,data.size());
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.getColumnModel().setColumnSelectionAllowed(true);
		table.setModel(new ALTableModel(rows, colNames, data));
	}

	private void setButtons(JPanel bp) {
		bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));
		JButton addRowButton = new JButton("Add Row");
		addRowButton.addActionListener(new addRowListener());
		addRowButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		JButton addColumnButton = new JButton("Add Column");
		addColumnButton.addActionListener(new addColListener());
		addColumnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		JButton deleteColumnButton = new JButton("Delete Column(s)");
		deleteColumnButton.addActionListener(new deleteColListener());
		deleteColumnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new doneListener());
		doneButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelListener());
		cancelButton.setAlignmentX(Component.LEFT_ALIGNMENT);


		JTextArea info = new JTextArea("Each column represents one type of annotation.\n\n" + 
				"If you want to have a pull-down list of allowable data\n" +
				"then place allowable values in the rows\n" +
				"otherwise leave blank and a text entry field will be presented\ntest2\n");
		info.setEditable(false);
		info.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		bp.add(info);
		bp.add(addRowButton);
		
		JPanel addColPanel = new JPanel();
		addColPanel.add(addColumnButton);
		colNameField = new JTextField("New column name",20);
		addColPanel.add(colNameField);
		addColPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		
//		bp.add(addColumnButton);
		bp.add(addColPanel);
		bp.add(deleteColumnButton);
		bp.add(doneButton);
		bp.add(cancelButton);
	}
	
	
	class addRowListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("row listener");
			((ALTableModel) table.getModel()).addRow();
//			((DefaultTableModel) table.getModel()).addRow(new Object[table.getColumnCount()]);
		}
	}

	class addColListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("col listener");
					((ALTableModel) table.getModel()).addColumn(colNameField.getText());
		}
	}
	
	class deleteColListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete column listener");
			((ALTableModel) table.getModel()).deleteColumns(
					table.getColumnModel().getSelectedColumns());
//			((DefaultTableModel) table.getModel()).addRow(new Object[table.getColumnCount()]);
		}
	}

	class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Done listener");
			setVisible(false);
			for(int col = 0; col < table.getColumnCount();col++) {
				for(int row = 0; row < table.getRowCount();row++) {
					System.out.println(table.getValueAt(row, col));
				}
			}
			dispose();
		}
	}

	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Cancel listener");
			dispose();
		}
	}

	
	final class ALTableModel extends AbstractTableModel {
		int rows;
		ArrayList<String> colNames;
		ArrayList<ArrayList<String>> values;

		public ALTableModel(int rows, ArrayList<String> colNames, ArrayList<ArrayList<String>> values) {
			this.rows = rows;
			this.colNames = colNames;
			this.values = values;
		}

		public void deleteColumns(int[] columns) {
			for (int c: columns){
				values.remove(c);
				colNames.remove(c);
				this.fireTableStructureChanged();
			}
		}

		@Override
		public String getColumnName(int column) {
			return colNames.get(column);
		}

		@Override
		public int getColumnCount() {
			return colNames.size();
		}
		@Override
		public int getRowCount() {
			return rows;
		}
		@Override
		public Object getValueAt(int row, int col) {
			System.out.println("requested value for row " + row + ", col " + col);
			if (row + 1 > values.get(col).size()) {
				return null;
			} else {
				System.out.println(values.get(col).get(row));
				return values.get(col).get(row);
			}
		}

		public void addColumn(String colName) {
			values.add(new ArrayList<String>());
			colNames.add(colName);
			this.fireTableStructureChanged();
		}

		public void addRow() {
			rows++;
			this.fireTableStructureChanged();
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		@Override
		public void setValueAt(Object newValue, int row, int col) {
			System.out.println("setting new value at row " + row + ", column " + col);
			System.out.println("current number of columns is " + values.size());
			System.out.println("current size of column is " + values.get(col).size());
			if (row + 1 > values.get(col).size()) {
				System.out.println("expanding");
				//add empty entries up through the cell that we want to set
				for (int s = values.get(col).size(); s < row +1; s++) {
					System.out.println("loop.  s=" + s + " row = " + row);
					values.get(col).add(null);
				}
			} 
			values.get(col).set(row, newValue.toString()); 
		}

		public Class<?> getColumnClass(int col) {
			return String.class;
		}
	}
	
}
