package sandbox;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class TestTwoClasses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String result;
				final DialogTest dt = new DialogTest(null);
				result = "blah";
						dt.showBox();
				System.out.println("done! result = " + result);
			}		
		});
	}
}

