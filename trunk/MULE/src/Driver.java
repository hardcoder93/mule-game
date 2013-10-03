import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;



public class Driver {
	public static void main(String[] args) throws HeadlessException, IOException {
		
		JFrame frame = new JFrame();
		frame.add(new MULEMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		/*StartScreen start = new StartScreen();
		GameSetup setup = new GameSetup();
		PlayerSetup engine = new PlayerSetup();
		
		engine.setVisible(true);
		setup.setVisible(true);
		start.setVisible(true);*/
		

	}
}
