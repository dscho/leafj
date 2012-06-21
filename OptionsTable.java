

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.table.AbstractTableModel;

import ij.Prefs;

public class OptionsTable {

	JFrame frame;
	JTable table;
	JPanel buttonPanel;
	ArrayList<ArrayList <String>> data = new ArrayList<ArrayList<String>>();
	ArrayList<String> colNames = new ArrayList<String>();
	int rows = 0; //initial number of rows
	private String prefsPath = ij.Prefs.getDefaultDirectory();
	private String fileName = "LeafJ_defaults.txt";
	private File defaultFile = new File(prefsPath,fileName);

	private void getInitialValues() {
		try {
			System.out.println("trying to open " + defaultFile);
			BufferedReader input = new BufferedReader(new FileReader(defaultFile));
			String header = input.readLine();
			colNames.addAll(Arrays.asList(header.split("\t")));
			
			for (int i = 0; i <= colNames.size();i++) {
				data.add(new ArrayList<String>());
			}

			while (true) { 
				String line = input.readLine();
				System.out.println(line);
				if (line == null) break;
				if (line.trim().length() == 0) continue;
				String[] cells = line.split("\t");
				int i = 0;
				for (String cell:cells) {
					data.get(i).add(cell);
					i++;
				}
//				data.add(new ArrayList<String>(Arrays.asList(line.split("\t"))));	
				rows++;
			}
			input.close();

		} catch (IOException e) {
			System.out.println("Can't open defaults files; generating from scratch");
			colNames.addAll(Arrays.asList(new String[] {
					"set", "treatment","replicate","genotype"
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
			rows=4;
		}
	}

	public OptionsTable() {
		getInitialValues();
	}

	private void setTable() {
		table = new JTable(rows,data.size());
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.getColumnModel().setColumnSelectionAllowed(true);
//		table.setModel(new DefaultTableModel());
		table.setModel(new ALTableModel(rows, colNames, data));
	}

	public void editTable() {
		SwingUtilities.invokeLater(new Runnable() { //note could also be invoke and wait?
			public void run () {
				frame = new JFrame("Options");
				setTable();
				buttonPanel = new JPanel();
				setButtons(buttonPanel);
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setOpaque(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		});
	}
	
	public void addDoneListener()
	
	public void setButtons(JPanel bp) {
		bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));
		JButton addRowButton = new JButton("Add Row");
		addRowButton.addActionListener(new addRowListener());
		JButton addColumnButton = new JButton("Add Column");
		addColumnButton.addActionListener(new addColListener());
		JButton deleteColumnButton = new JButton("Delete Column(s)");
		deleteColumnButton.addActionListener(new deleteColListener());
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new doneListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelListener());

		JTextArea info = new JTextArea("Each column represents one type of annotation.\n\n" + 
				"If you want to have a pull-down list of allowable data\n" +
				"then place allowable values in the rows\n" +
				"otherwise leave blank and a text entry field will be presented\ntest2\n");
		info.setEditable(false);
		bp.add(info);
		bp.add(addRowButton);
		bp.add(addColumnButton);
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
			final JFrame getColumn = new JFrame("Enter column name");
			final JTextField colNameField = new JTextField(25);
			colNameField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					((ALTableModel) table.getModel()).addColumn(colNameField.getText());
//					((DefaultTableModel) table.getModel()).addColumn(new Object[table.getColumnCount()]);
					
					getColumn.dispose();
				}
			});
			getColumn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getColumn.getContentPane().add(colNameField);
			getColumn.pack();
			getColumn.setLocationRelativeTo(table);
			getColumn.setVisible(true);
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
	
	static class ALTableModel extends AbstractTableModel {
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

