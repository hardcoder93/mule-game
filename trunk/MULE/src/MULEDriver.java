import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;



public class MULEDriver {
	public static void main(String[] args) throws HeadlessException, IOException {

		StartScreen start = new StartScreen();
		GameSetup setup = new GameSetup();
		PlayerSetup engine = new PlayerSetup();


		while (!start.go()){
			start.setVisible(true);
		}
		start.setVisible(false);
		engine.setVisible(true);
	}
}



