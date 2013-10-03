import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;



public class Driver {
	public static void main(String[] args) throws HeadlessException, IOException {
		
		StartScreen start = new StartScreen();
		GameSetup setup = new GameSetup();
		PlayerSetup engine = new PlayerSetup();
		
		engine.setVisible(true);
		setup.setVisible(true);
		start.setVisible(true);
		

	}
}
