import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * MULEMainPanel is the main panel in the game which controls the current 
 * display in the JFrame. 
 * 
 * @author Chris
 *
 */
public class MULEMainPanel extends JPanel{
	//Instance Data
	private JPanel startPanel; //Change this later to startPanel class?
	private JPanel gameSetupPanel; //Change this later to gameSetupPanel class?
	private JPanel playerSetupPanel; //Change this later to playerSetupPanel class?
	private JPanel gameplayPanel; //Change this later to gamePlayPanel class?
	private CardLayout cardLayout = new CardLayout();
	
	public MULEMainPanel(){
		setLayout(cardLayout);
		//create and add the 4 panels to the main panel here.
		
		//decide how to handle button listeners - if separate classes will be 
		//more difficult, if we build the panels as inner classes it will 
		//probably be easier - pros and cons?
	}
}
