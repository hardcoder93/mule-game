import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;



public class Driver {
	public static void main(String[] args) throws HeadlessException, IOException {
		
		JFrame frame = new JFrame();
		frame.add(new MULEMainPanel());
        frame.pack();
		frame.setBounds(100, 100, 900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
}
