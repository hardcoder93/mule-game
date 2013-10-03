import java.awt.CardLayout;

import javax.swing.JButton;
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
	MULEGameEngine engine;
	
	private StartScreen startPanel = new StartScreen(); //Change this later to startPanel class?
	private GameSetup gameSetupPanel = new GameSetup(); //Change this later to gameSetupPanel class?
	private PlayerSetup playerSetupPanel = new PlayerSetup(); //Change this later to playerSetupPanel class?
	private JPanel gameplayPanel = new JPanel(); //Change this later to gamePlayPanel class?
	private CardLayout cardLayout = new CardLayout();
	
	private final String startID = "START";
	private final String gameSetupID = "GAMESETUP";
	private final String playerSetupID = "PLAYERSETUP";
	private final String gameplayID = "GAMEPLAY";
	
	public MULEMainPanel(){
		setLayout(cardLayout);
		
		add(startPanel, startID);
		add(gameSetupPanel, gameSetupID);
		add(playerSetupPanel, playerSetupID);
		add(gameplayPanel, gameplayID);
		
		JButton startBtn = startPanel.getNextButton();
		JButton gameSetupBtn = startPanel.getNextButton();
		JButton playerSetupBtn = startPanel.getNextButton();

		startBtn.add(new NextListener(startID));		
		gameSetupBtn.add(new NextListener(gameSetupID));
		playerSetupBtn.add(new NextListener(playerSetupID));
		
		cardLayout.show(startPanel, startID);
		//decide how to handle button listeners - if separate classes will be 
		//more difficult, if we build the panels as inner classes it will 
		//probably be easier - pros and cons?
	}
	
	private class NextListener implements ActionListener{
		String ID;
		
		public NextListener(String id){
			ID = id;
		}
		
		public void actionPerformed(ActionEvent e){
			switch(ID){
			case startID:
				cardLayout.show(gameSetupPanel, gameSetupID);
				break;
			case gameSetupID:
				engine = new MULEGameEngine(gameSetupPanel.getDifficulty(), 
											gameSetupPanel.getMapType(), 
											gameSetupPanel.getPlayerCount());
				cardLayout.show(playerSetupPanel, playerSetupID);
				break;
			case playerSetupID:
				int currPlayer = engine.getNextPlayerSlot();
				if(currPlayer!=-1){
					//May need to reset the fields on the player setup screen here.
					cardLayout.show(playerSetupPanel, playerSetupID);
				}else cardLayout.show(gameplayPanel, gameplayID);
				break;
			}
		}
	}
}
