import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private StartScreen startPanel = new StartScreen();
	private GameSetup gameSetupPanel = new GameSetup();
	private PlayerSetup playerSetupPanel = new PlayerSetup();
	private JPanel gameplayPanel = new JPanel();
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
		
		JButton startBtn = startPanel.getButton();
		JButton gameSetupBtn = gameSetupPanel.getButton();
		JButton playerSetupBtn = playerSetupPanel.getButton();

		startBtn.addActionListener(new NextListener(startID));		
		gameSetupBtn.addActionListener(new NextListener(gameSetupID));
		playerSetupBtn.addActionListener(new NextListener(playerSetupID));
		
		//cardLayout.show(startPanel, startID);
	}
	
	private class NextListener implements ActionListener{
		String ID;
		
		public NextListener(String id){
			ID = id;
			System.out.println(toString());
		}
		
		public void actionPerformed(ActionEvent e){
			switch(ID){
			case startID:
				cardLayout.show(MULEMainPanel.this, gameSetupID);
				break;
			case gameSetupID:
				engine = new MULEGameEngine(gameSetupPanel.getDifficulty(), 
											gameSetupPanel.getMapType(), 
											gameSetupPanel.getPlayerCount());
				cardLayout.show(MULEMainPanel.this, playerSetupID);
				break;
			case playerSetupID:
				/*engine.addPlayer(playerSetupPanel.getPlayerName(), 
								 playerSetupPanel.getColor(), 
								 playerSetupPanel.getRace());*/
				int currPlayer = engine.getNextPlayerSlot();
				if(currPlayer!=-1){
					//May need to reset the fields on the player setup screen here.
					cardLayout.show(MULEMainPanel.this, playerSetupID);
				}else cardLayout.show(MULEMainPanel.this, gameplayID);
				break;
			}
		}
	}
}