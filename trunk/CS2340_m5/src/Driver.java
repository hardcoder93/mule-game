import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;



public class Driver {
	public static void main(String[] args) throws HeadlessException, IOException {
		
		StartScreen start = new StartScreen();
		PlayerSetup setup = new PlayerSetup();
		GameEngine engine = new GameEngine();
		
		engine.setVisible(true);
		setup.setVisible(true);
		start.setVisible(true);
		

	}
}
