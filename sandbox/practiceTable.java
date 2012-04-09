package sandbox;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class practiceTable{
	private JFrame frame = new JFrame("PracticeTable");
	private JPanel buttonPanel = new JPanel();
	private JTable table = new JTable(5,5);
	private Object lock = new Object();


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
		
		buttonPanel.add(addRowButton);
		buttonPanel.add(addColumnButton);
		buttonPanel.add(doneButton);
		buttonPanel.add(cancelButton);
		return(buttonPanel);
	}

	final Runnable doPanel = new Runnable() {
		public void run() {
			System.out.println("start of doPanel");
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setOpaque(true);
			JPanel buttonPanel = makeButtonPanel();
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.add(scrollPane,BorderLayout.WEST);
			frame.add(buttonPanel,BorderLayout.EAST);
			frame.pack();
			frame.setVisible(true);	
			frame.addWindowListener(new windowClosedListener());
			System.out.println("end of doPanel");
		}
	};
	
	Thread tableThread = new Thread() {
		public void run() {
			System.out.println("start of table Thread");
			synchronized(lock) {
				while (frame.isVisible())
					try {
						lock.wait();			
					} catch (Exception e) {
						e.printStackTrace();
					}
				System.out.println("end of tableThread");
			}
		}
	};


	public static void main(String[] args){
		practiceTable pt = new practiceTable();
		pt.tableThread.start();
		System.out.println("end of main");

	}
	
	class windowClosedListener implements WindowListener {
		public void windowClosed(WindowEvent event) {
			System.out.println("window closed");
		}

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowClosing(WindowEvent arg0) {			
		}

		public void windowDeactivated(WindowEvent arg0) {			
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {	
		}

		public void windowOpened(WindowEvent arg0) {			
		}
		
	}

	class addRowListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("row listener");
		}
	}

	class addColListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("col listener");
		}
	}
	
	class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Done listener");
		}
	}
	
	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Cancel listener");
		}
	}

}
