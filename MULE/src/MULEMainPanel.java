import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * MULEMainPanel is the main panel in the game which controls the current 
 * display in the JFrame. 
 * 
 * @author Chris Jenkins
 */
@SuppressWarnings("serial")
public class MULEMainPanel extends JPanel{
	//Instance Data
	MULEGameEngine engine;
	Timer updater;
	
	private StartScreen startPanel = new StartScreen(); //game startup screen.
	private GameSetup gameSetupPanel = new GameSetup(); //The panel that allows choice of game type.
	private PlayerSetup playerSetupPanel = new PlayerSetup(); //The panel that allows each player to make a character.
	private GameplayPanel gameplayPanel = new GameplayPanel(); //The panel which displays main gameplay.
	private CardLayout cardLayout = new CardLayout(); //CardLayout allows us to easily switch between screens.
	
	//The screen IDs are used to tell the CardLayout which screen to display, and to tell the 
	//button listeners which button was pressed.
	private final String startID = "START";
	private final String gameSetupID = "GAMESETUP";
	private final String playerSetupID = "PLAYERSETUP";
	private final String gameplayID = "GAMEPLAY";
	
	/**
	 * Constructs a MULEMainPanel with a set size, adds the 4 game screens, 
	 * and assigns listeners to the necessary buttons.
	 */
	public MULEMainPanel(){
		setLayout(cardLayout);
		setPreferredSize(new Dimension(900,600));
		
		
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
	}
	
	private void runGameLoop(){
		updater = new Timer(1000/60, new GameUpdater());
		updater.start();
	}
	
	/**
	 * The NextListener class creates button listeners for the "Next" buttons
	 * on the menu screens, allowing users to switch between screens and 
	 * start the game.
	 * 
	 * @author Chris Jenkins
	 */
	private class NextListener implements ActionListener{
		String ID;
		
		public NextListener(String id){
			ID = id;
		}
		
		public void actionPerformed(ActionEvent e){
			switch(ID){
			case startID: //If the start button is pressed, display the game setup screen.
				cardLayout.show(MULEMainPanel.this, gameSetupID);
				break;
			case gameSetupID: //If the game setup button is pressed, create game engine and show player screen.
				engine = new MULEGameEngine(gameSetupPanel.getDifficulty(), 
											gameSetupPanel.getMapType(), 
											gameSetupPanel.getPlayerCount());
				playerSetupPanel.setPlayerNumber(engine.getNextPlayerSlot() + 1);
				cardLayout.show(MULEMainPanel.this, playerSetupID);
				break;
			case playerSetupID: //If player button is pressed, check that fields are filled, then go to next or game screen.
				if (playerSetupPanel.getPlayerName().equals("")){
					playerSetupPanel.showNoInputLabel();
					break;
				}
				playerSetupPanel.clearNoInputLabel();
				engine.addPlayer(playerSetupPanel.getPlayerName(), playerSetupPanel.getPlayerColor(), 
						playerSetupPanel.getPlayerRace());
				int currPlayer = engine.getNextPlayerSlot();
				if(currPlayer!=-1){
					playerSetupPanel.removeColor(playerSetupPanel.getPlayerColor());
					playerSetupPanel.clearPlayerName();
					playerSetupPanel.setPlayerNumber(engine.getNextPlayerSlot() + 1);
					cardLayout.show(MULEMainPanel.this, playerSetupID);
				}else {
					GameState.setState(GameState.PLAYING_MAP);
					gameplayPanel.setMapAndPlayers(engine.getMap(), engine.getPlayers());
					gameplayPanel.setActivePlayer(engine.getActivePlayer());
					setFocusable(true);
					addKeyListener(new PlayerControls());					
					cardLayout.show(MULEMainPanel.this, gameplayID);
					runGameLoop();
				}
				break;
			}
		}
	}
	
	private class PlayerControls implements KeyListener{
		public void keyPressed(KeyEvent e){
			if(GameState.playing()){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
					engine.movePlayer(0,-1); //Y coords start at upper left, so up is negative.
					break;
				case KeyEvent.VK_DOWN:
					engine.movePlayer(0, 1); //Y coords start at upper left, so down is positive.
					break;
				case KeyEvent.VK_LEFT:
					engine.movePlayer(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
					engine.movePlayer(1, 0);
					break;
				}
			}else if(GameState.getState()==GameState.WAITING){ 
				if(!engine.getMap().isTownTile(engine.getActivePlayer().getX(), engine.getActivePlayer().getY()))
					GameState.setState(GameState.PLAYING_MAP);
				else GameState.setState(GameState.PLAYING_TOWN);
			}
		}
		
		//The following methods are unused in this application but have to be implemented.
		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
	}
	
	private class GameUpdater implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(GameState.playing()){
				gameplayPanel.repaint();
			}else{
				updater.stop();
			}
		}
	}
}
