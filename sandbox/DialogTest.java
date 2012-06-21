package sandbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DialogTest extends JDialog {
	static JFrame frame;
	JButton button;

	DialogTest(JFrame owner) {
		super(owner, true);

		//frame = new JFrame("Hello there!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(100, 100);
		button = new JButton("OK");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		add(button);
		pack();

	}

	public void showBox() {
		setVisible(true);
//		return "hello";
	}

}
