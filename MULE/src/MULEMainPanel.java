import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

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
	Timer updater,turnTimer;
	
	private StartScreen startPanel = new StartScreen(); //game startup screen.
	private GameSetup gameSetupPanel = new GameSetup(); //The panel that allows choice of game type.
	private PlayerSetup playerSetupPanel = new PlayerSetup(); //The panel that allows each player to make a character.
	private GameplayPanel gameplayPanel = new GameplayPanel(); //The panel which displays main gameplay.
	private CardLayout cardLayout = new CardLayout(); //CardLayout allows us to easily switch between screens.
	private TurnStartPanel turnStartPanel = new TurnStartPanel();
	
	//The screen IDs are used to tell the CardLayout which screen to display, and to tell the 
	//button listeners which button was pressed.
	private final String startID = "START";
	private final String gameSetupID = "GAMESETUP";
	private final String playerSetupID = "PLAYERSETUP";
	private final String gameplayID = "GAMEPLAY";
	private final String turnStartID = "TURNSTART";
	
	private Mouse landGrantMouse;
	private PlayerControls arrowKeys;
	
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
		add(turnStartPanel, turnStartID);
		
		JButton startBtn = startPanel.getButton();
		JButton gameSetupBtn = gameSetupPanel.getButton();
		JButton playerSetupBtn = playerSetupPanel.getButton();
		JButton turnStartBtn = turnStartPanel.getButton();
		JButton gamePlayBtn = gameplayPanel.getButton();

		startBtn.addActionListener(new NextListener(startID));		
		gameSetupBtn.addActionListener(new NextListener(gameSetupID));
		playerSetupBtn.addActionListener(new NextListener(playerSetupID));
		turnStartBtn.addActionListener(new NextListener(turnStartID));
		gamePlayBtn.addActionListener(new NextListener(gameplayID));
		
		arrowKeys = new PlayerControls();
		
	}
	/**
	 * The runGameLoop method uses an ActionListener attached to a timer to 
	 * run a continual loop allowing the player to move around the screen.  
	 */
	private void runGameLoop(){
		updater = new Timer(1000/60, new GameUpdater()); //1000/60 means we are updating at 60 FPS.
		if (GameState.playing())
			updater.start();
	}
	
	private void runTimerLoop(){
		turnTimer =  new Timer(engine.calculateActivePlayerTurnTime()*1000, new TurnUpdater());
		turnTimer.start();
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
					GameState.setState(GameState.START_ROUND);
					gameplayPanel.setMapAndPlayers(engine.getMap(), engine.getPlayers());
					displayNextRound();
				}
				break;
			case turnStartID: //if start turn button is pushed
				if (GameState.getState().equals(GameState.WAITING)){ //if the player starts his turn
					if (engine.getNextState().equals(GameState.LANDGRANT)){ //if next state is landgrant
						GameState.setState(GameState.LANDGRANT);
						startLandGrant();
					} else if (engine.getNextState().equals(GameState.PLAYING_MAP)){ //if next state is map
						GameState.setState(GameState.PLAYING_MAP);
						startGameLoop();													
					}	
				} else { //if player's turn is over
					engine.nextActivePlayerIndex();
					if (engine.getNextState().equals(GameState.START_ROUND)){
						GameState.setState(GameState.START_ROUND);
						displayNextRound();
					} else {
						GameState.setState(GameState.WAITING);
						displayNextTurn();
					}
				}
				break;
			case gameplayID: //if gameplay button is pushed
				if (GameState.getState().equals(GameState.LANDGRANT)){
					GameState.setState(GameState.WAITING);
					endLandGrant();
					engine.nextActivePlayerIndex();
					displayNextTurn();
				}
			}
		}
	}
	
	/**
	 * PlayerControls is a private inner class that acts as a KeyListener 
	 * for the gameplayPanel; it allows the player to move around the screen 
	 * using the arrow keys or WASD, as well as pause/un-pause the game.
	 * 
	 * @author Chris Jenkins (cjenkins36)
	 *
	 */
	private class PlayerControls implements KeyListener{
		@SuppressWarnings("static-access")
		public void keyPressed(KeyEvent e){
			if(GameState.playing()){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					engine.movePlayer(0,-1); //Y coords start at upper left, so up is negative.
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					engine.movePlayer(0, 1); //Y coords start at upper left, so down is positive.
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					engine.movePlayer(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					engine.movePlayer(1, 0);
					break;
				case KeyEvent.VK_ESCAPE:
					GameState.setState(GameState.WAITING);
					gameplayPanel.repaint();
				}
			}if(GameState.getState()==GameState.WAITING){ 
				if(engine.getMap().getActiveMap().equals(engine.getMap().BIG_MAP))
					GameState.setState(GameState.PLAYING_MAP);
				else {
					GameState.setState(GameState.PLAYING_TOWN);
				}
			
				updater.stop();
				runGameLoop();
			
			
			}
			
		
		}
		
		//The following methods are unused in this application but have to be implemented.
		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
	}
	
	/**
	 * GameUpdater is a private inner class that acts as an ActionListener.
	 * An instance of this class is meant to be attached to a timer that 
	 * is used for creating a game loop.
	 * 
	 * @author Chris Jenkins (cjenkins36)
	 *
	 */
	private class GameUpdater implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(GameState.playing()){
				gameplayPanel.repaint();
				// Change to Black Screen - Lauren
				if(3==engine.getMap().isInBuilding(engine.getActivePlayer().getX(), engine.getActivePlayer().getY())){
					updater.stop();
					endTurn(true);
				}
			}else{
				updater.stop();
			}
		}
	}
	
	private class TurnUpdater implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			endTurn(false);
		}
	}
	
	/**
	 * This class is a mouseInputListener that is used to detect mouse actions.
	 * Currently, it is only used for the land grant state of the game
	 * @author John Certusi (jcertusi3)
	 *
	 */
	private class Mouse implements MouseInputListener{

		private boolean pickedTile = false;
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Point location = arg0.getPoint();
			if (!pickedTile){
				if (engine.getMap().isBuyable(location)){
					if (engine.purchaseProperty(location)){
						pickedTile = true;
						gameplayPanel.updateScoreboard();
						gameplayPanel.setButtonText("Done");
						engine.raiseTile(location, true);
						gameplayPanel.repaint();
					}
				}
			}	
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			Point location = arg0.getPoint();
			if (!pickedTile){
				engine.raiseTile(location, true);
				gameplayPanel.repaint();
			}
		}
	}
	
	private void displayNextRound() {
		engine.nextRound();
		engine.setPlayerTurnOrder();
		turnStartPanel.setRoundLabel(engine.getCurrentRound());
		cardLayout.show(MULEMainPanel.this, turnStartID);
		GameState.setState(GameState.START_ROUND);
	}
	
	public void endTurn(boolean gamble){
		removeKeyListener(arrowKeys);
		turnTimer.stop();
		int gamblingMoney = 0;
		engine.getActivePlayer().resetPosition();
		if (gamble){
			gamblingMoney = engine.getGambleMoney(30);	//change 30 to amount of time left
			engine.getActivePlayer().addMoney(gamblingMoney);
			turnStartPanel.setPubLabel(engine.getActivePlayer(), gamblingMoney);
		} else {
			turnStartPanel.setTimeIsUpLabel();
		}
		gameplayPanel.updateScoreboard();
		cardLayout.show(MULEMainPanel.this, turnStartID);
	}
	
	private void displayNextTurn() {
		turnStartPanel.setPlayerLabel(engine.getActivePlayer());
		cardLayout.show(MULEMainPanel.this,turnStartID);
	}
	
	private void startLandGrant(){
		gameplayPanel.enableButton();
		gameplayPanel.resetButton();
		cardLayout.show(MULEMainPanel.this, gameplayID);
		landGrantMouse = new Mouse();
		addMouseListener(landGrantMouse);
		addMouseMotionListener(landGrantMouse);
	}
	
	private void endLandGrant(){
		gameplayPanel.disableButton();
		engine.raiseTile(new Point(0,0), false);
		removeMouseListener(landGrantMouse);
		removeMouseMotionListener(landGrantMouse);
	}
	
	private void startGameLoop(){
		gameplayPanel.setActivePlayer(engine.getActivePlayer());	
		setFocusable(true);
		addKeyListener(arrowKeys);					
		cardLayout.show(MULEMainPanel.this, gameplayID);
		runTimerLoop();
		runGameLoop();
	}
	
}


