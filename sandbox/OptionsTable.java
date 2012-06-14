package sandbox;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class OptionsTable {

	JFrame frame;
	JTable table;
	JPanel buttonPanel;
	ArrayList<ArrayList <String>> data = new ArrayList<ArrayList<String>>();
	ArrayList<String> colNames = new ArrayList<String>();
	int rows = 0; //initial number of rows

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				OptionsTable ot = new OptionsTable();
			}
		});
	}

	private void getInitialValues() {
		File file = new File("leafJ_defaults.txt");
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));

			String header = input.readLine();
			colNames.addAll(Arrays.asList(header.split("\t")));

			while (true) { 
				String line = input.readLine();
				if (line == null) break;
				if (line.trim().length() == 0) continue;
				ArrayList<String> fields = new ArrayList<String>(Arrays.asList(line.split("\t")));
				data.add(fields);	
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
					"control","treated"
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
		frame = new JFrame("Options");
//		table = new JTable(5,5);
		getInitialValues();
		setTable();
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

	private void setTable() {
		table = new JTable(rows,data.size());
		table.setC
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.setModel(new tableModel(rows,colNames,data));
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
				"If you want to have a pull-down list of allowable data\n" +
				"then place allowable values in the rows\n" +
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
			final String colName;
			System.out.println("col listener");
			final TableColumn tc = new TableColumn();
			final JFrame getColumn = new JFrame("Enter column name");
			final JTextField colNameField = new JTextField(25);
			colNameField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					tc.setHeaderValue(colNameField.getText());
					getColumn.dispose();
					table.addColumn(tc);
				}
			});
			getColumn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getColumn.getContentPane().add(colNameField);
			getColumn.pack();
			getColumn.setVisible(true);
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
	
	static class tableModel extends AbstractTableModel {
		
		int rows;
		ArrayList<String> colNames;
		ArrayList<ArrayList<String>> values;
		
		public tableModel(int rows, ArrayList<String> colNames, ArrayList<ArrayList<String>> values) {
			this.rows = rows;
			this.colNames = colNames;
			this.values = values;
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
			return values.get(row).get(col);
		}

		public Class<?> getColumnClass(int col) {
			return String.class;
		}
	}
}

