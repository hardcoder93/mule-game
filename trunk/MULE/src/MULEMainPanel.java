import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
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
	private MULEGameEngine engine;
	private Timer updater,turnTimer, screenTimer;

	private StartScreen startPanel = new StartScreen(); //game startup screen.
	private GameSetup gameSetupPanel = new GameSetup(); //The panel that allows choice of game type.
	private PlayerSetup playerSetupPanel = new PlayerSetup(); //The panel that allows each player to make a character.
	private GameplayPanel gameplayPanel = new GameplayPanel(); //The panel which displays main gameplay.
	private CardLayout cardLayout = new CardLayout(); //CardLayout allows us to easily switch between screens.
	private JLabel turnTime = new JLabel();
	private int countDown;
	//The screen IDs are used to tell the CardLayout which screen to display, and to tell the 
	//button listeners which button was pressed.
	private final String startID = "START";
	private final String loadID = "LOAD";
	private final String gameSetupID = "GAMESETUP";
	private final String playerSetupID = "PLAYERSETUP";
	private final String gameplayID = "GAMEPLAY";
	
	private final String SAVE_FILE = "savedGame.dat";

	private Mouse landGrantMouse;
	private PlayerControls arrowKeys;
	private NextScreen spaceBar;

	private JButton landGrantBtn;
	private JButton menuButton;
	private StorePurchaseAction storeListener;

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

		turnTime.setFont(new Font("Narkisim", Font.BOLD, 20));
		turnTime.setForeground(Color.RED);
		gameplayPanel.addLabel(turnTime);

		JButton startBtn = startPanel.getNewGameButton();
		JButton loadBtn = startPanel.getLoadGameButton();
		JButton gameSetupBtn = gameSetupPanel.getButton();
		JButton playerSetupBtn = playerSetupPanel.getButton();
		landGrantBtn = gameplayPanel.getButton();
		menuButton = gameplayPanel.getMenuButton();

		storeListener = new StorePurchaseAction();

		startBtn.addActionListener(new NextListener(startID));
		loadBtn.addActionListener(new NextListener(loadID));
		gameSetupBtn.addActionListener(new NextListener(gameSetupID));
		
		playerSetupBtn.addActionListener(new NextListener(playerSetupID));
		landGrantBtn.addActionListener(new NextListener(gameplayID));
		menuButton.addActionListener(storeListener);

		//
		startBtn.addKeyListener(new EnterKeyListener(startID));		
		gameSetupBtn.addKeyListener(new EnterKeyListener(gameSetupID));
		
		playerSetupBtn.addKeyListener(new EnterKeyListener(playerSetupID));
		landGrantBtn.addKeyListener(new EnterKeyListener(gameplayID));
		landGrantBtn.addActionListener(new LandGrantButton());
		//menuButton.addActionListener(storeListener);

		arrowKeys = new PlayerControls();
		spaceBar = new NextScreen();
		setFocusable(true);


	}
	/**
	 * The runGameLoop method uses an ActionListener attached to a timer to 
	 * run a continual loop allowing the player to move around the screen.  
	 */
	private void runGameLoop(){
		updater = new Timer(1000/60, new GameUpdater()); //1000/60 means we are updating at 60 FPS.
		turnTimer =  new Timer(1000, new TurnUpdater());
		turnTimer.start();
		if (GameState.playing())
			updater.start();
	}

	private class EnterKeyListener implements KeyListener{
		String ID2;
		public EnterKeyListener(String id){
			ID2 = id;
		}
		public void keyTyped(KeyEvent e) {           
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				NextListener go = new NextListener(ID2);
				go.actionPerformed(null);
			}

		}

		public void keyReleased(KeyEvent e) {            
		}    
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
				gameSetupPanel.focusNameBox();
				break;
			case loadID:
				engine = load(SAVE_FILE);
				//GameState.setState(GameState.PLAYING_MAP);
				gameplayPanel.setMapAndPlayers(engine.getMap(), engine.getPlayers(), engine.getStore());
				gameplayPanel.setStore(engine.getStore());
				startRoundOrTurn(false);
				break;
			case gameSetupID: //If the game setup button is pressed, create game engine and show player screen.
				engine = new MULEGameEngine(gameSetupPanel.getDifficulty(), 
						gameSetupPanel.getMapType(), 
						gameSetupPanel.getPlayerCount());
				gameplayPanel.setStore(engine.getStore());
				playerSetupPanel.setPlayerNumber(engine.getNextPlayerSlot() + 1);
				cardLayout.show(MULEMainPanel.this, playerSetupID);
				playerSetupPanel.focusNameBox();
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
					playerSetupPanel.focusNameBox();
				}else {
					GameState.setState(GameState.START_ROUND);
					gameplayPanel.setMapAndPlayers(engine.getMap(), engine.getPlayers(), engine.getStore());
					gameplayPanel.repaint();
					startRoundOrTurn(true);
				}
				break;
			case gameplayID: //if gameplay button is pushed
				if (GameState.getState().equals(GameState.LANDGRANT)){
					GameState.setState(GameState.WAITING);
					endLandGrant();
					engine.nextActivePlayerIndex();
					startNextTurn();
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
					if (engine.isInBuilding() == -1){
						engine.movePlayer(0,-1); //Y coords start at upper left, so up is negative.
						if (engine.isInBuilding() == 2)
							gameplayPanel.displayStoreMenu();
					}
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					if (engine.isInBuilding() == 2)
						gameplayPanel.removeBuilding();
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
					save(SAVE_FILE);
					gameplayPanel.showMessage("Game Saved",2000);
					//GameState.setState(GameState.WAITING);
					//gameplayPanel.repaint();
				case KeyEvent.VK_SPACE:
					tryPlaceMule();
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
				engine.updateWampus();
				gameplayPanel.updateScoreboard();
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
			if (countDown>0){

				turnTime.setText(String.valueOf(countDown--));
				gameplayPanel.updateTimerLabel(turnTime);

			}
			else{
				turnTimer.stop();
				endTurn(false);
			}
		}
	}

	private class ScreenDelay implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			if (countDown>0){
				countDown--;
			} else{
				screenTimer.stop();
				if (GameState.getState().equals(GameState.LANDGRANT)){
					endLandGrant();
				} else if (GameState.playing()){
					gameplayPanel.removeBuilding();
					gameplayPanel.repaint();
					startRoundOrTurn(true);			
				}
			}
		}
	}

	private class StorePurchaseAction implements ActionListener{

		ArrayList<Object> menuEntries;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int cost;
			menuEntries = gameplayPanel.getMenuEntries();
			cost = engine.storeTransaction(menuEntries.get(0).equals("Buy"), menuEntries.get(1).toString(), 
					(menuEntries.get(2).toString()));
			if (cost > 0){
				gameplayPanel.setErrorMessage(cost);
			} else {
				gameplayPanel.updateScoreboard();
			}
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
			if(GameState.getState().equals(GameState.LANDGRANT)){
				if (!pickedTile){
					if (engine.getMap().isBuyable(location)){
						if (engine.purchaseTile(location)){
							pickedTile = true;
							gameplayPanel.updateScoreboard();
							gameplayPanel.removeButton();
							engine.raiseTile(location, true);
							gameplayPanel.repaint();
							screenTimer = new Timer(1000, new ScreenDelay());
							countDown = 1;
							screenTimer.start();
						}
					}
				}	
			}else if(GameState.getState().equals(GameState.PLAYING_MAP)){
				int reward = engine.tryWampusClick(location); 
				if(reward>0)
					showWampusMessage(reward);
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

	/**
	 * displayNextRound displays the turnStartPanel, but displays which round is
	 * about to begin. It also sets up the next round in the game engine.
	 */
	private void startNextRound() {
		GameState.setState(GameState.START_ROUND);
		engine.nextRound();
		engine.setPlayerTurnOrder();
		gameplayPanel.displayNextRound(engine.getCurrentRound());
		cardLayout.show(MULEMainPanel.this, gameplayID);
		addKeyListener(spaceBar);
	}

	/**
	 * endTurn ends the player's turn while he/she is playing the map. Anytime the player's
	 * turn should end, this should be called. It calculates and applies any money gained
	 * from gambling if the player entered the Pub.
	 * 
	 * @param gamble	true if player gambled; false if otherwise (timer ran out)
	 */
	public void endTurn(boolean gamble){
		removeKeyListener(arrowKeys);
		removeMouseListener(landGrantMouse);
		turnTime.setText("");;
		turnTimer.stop();
		int gamblingMoney = 0;
		engine.getActivePlayer().setMule(Player.NO_MULE);
		engine.resetWampus();
		if (gamble){
			gamblingMoney = engine.getGambleMoney(countDown);
			engine.getActivePlayer().addMoney(gamblingMoney);
			gameplayPanel.displayPub(gamblingMoney);
			countDown = 3;
			screenTimer = new Timer(1000, new ScreenDelay());
			screenTimer.start();
			//turnStartPanel.setPubLabel(engine.getActivePlayer(), gamblingMoney);
		} else {
			gameplayPanel.displayNoMoreTime();
			gameplayPanel.repaint();
			countDown = 2;
			screenTimer = new Timer(1000, new ScreenDelay());
			screenTimer.start();
		}
		gameplayPanel.updateScoreboard();
		gameplayPanel.repaint();
	}

	/**
	 * displayNextTurn displays the turnStartPanel and shows which player's turn is
	 * about to begin.
	 */
	private void startNextTurn() {
		removeKeyListener(spaceBar);
		GameState.setState(GameState.START_TURN);
		gameplayPanel.setActivePlayer(engine.getActivePlayer());
		gameplayPanel.displayNextTurn();
		gameplayPanel.repaint();
		cardLayout.show(MULEMainPanel.this, gameplayID);
		addKeyListener(spaceBar);
	}

	/**
	 * startLandGrant initiates the land grant stage of the turn. It must be called for
	 * each player. It displays the map and add's the MouseListeners.
	 */
	private void startLandGrant(){
		removeKeyListener(spaceBar);
		GameState.setState(GameState.LANDGRANT);
		gameplayPanel.addButton();
		gameplayPanel.removeScreenLabel();
		gameplayPanel.setLandGrantLabel(engine.getCurrentRound());
		gameplayPanel.addLandGrantLabel(true);
		gameplayPanel.enableButton();
		gameplayPanel.repaint();
		landGrantMouse = new Mouse();
		addMouseListener(landGrantMouse);
		addMouseMotionListener(landGrantMouse);
	}

	/**
	 * endLandGrant stops the land grant stage of a turn. It removes the MouseListeners
	 */
	private void endLandGrant(){
		removeMouseListener(landGrantMouse);
		removeMouseMotionListener(landGrantMouse);
		if (screenTimer != null)
			screenTimer.stop();
		gameplayPanel.addLandGrantLabel(false);
		gameplayPanel.removeButton();
		engine.raiseTile(new Point(0,0), false);
		startRoundOrTurn(true);
	}

	/**
	 * startGameLoop initializes the game loop (player moving around map). It adds the
	 * keyListener and displays the map. 
	 */
	private void startGameLoop(){
		removeKeyListener(spaceBar);
		GameState.setState(GameState.PLAYING_MAP);
		gameplayPanel.removeScreenLabel();
		gameplayPanel.setActivePlayer(engine.getActivePlayer());
		engine.getActivePlayer().resetPosition();
		setFocusable(true);
		addKeyListener(arrowKeys);			
		addMouseListener(landGrantMouse);
		cardLayout.show(MULEMainPanel.this, gameplayID);
		countDown = engine.calculateActivePlayerTurnTime();
		turnTime.setText("" + countDown);
		
		  /////////////////////////// check
		gameplayPanel.showProductionMessage(engine.getActivePlayer().calculateProduction());
		gameplayPanel.updateScoreboard();
		
		
		
		runGameLoop();
	}

	private void tryPlaceMule(){
		if(GameState.getState()==GameState.PLAYING_MAP){
			Player active = engine.getActivePlayer();
			if(active.hasMule()){
				Tile currTile = engine.getMap().getTileFromLocation(active.getCenterPoint());
				if(active.ownsTile(currTile) && !currTile.hasMule()){
					switch(active.getMule()){
					case Player.FOOD_MULE:
						currTile.setProductionType("Food");
						break;
					case Player.ENERGY_MULE:
						currTile.setProductionType("Energy");
						break;
					case Player.ORE_MULE:
						currTile.setProductionType("Smithore");
						break;
					}
					active.setMule(Player.NO_MULE);
				}else
					active.setMule(Player.NO_MULE);
			}
		}
	}
	// checks for the randomTurnEvent in the beginning of the turn
	private void randomTurnEvent() {
		if (GameState.getState().equals (GameState.PLAYING_MAP) ){
			if (engine.getActivePlayerIndex()==engine.getLowestScore()) {
				engine.randomEventForLoser();
				gameplayPanel.updateScoreboard();
			}
			else {
				engine.randomEvent();
				gameplayPanel.updateScoreboard();
			}
		}
	}
	
	/**
	 * Starts the next turn/round if the input is true. If the input is false,
	 * initiates the beginning of the current round (this case is meant to be
	 * used for loading a saved game).
	 * 
	 * @param next
	 */
	private void startRoundOrTurn(boolean next){
		System.out.println(GameState.getState());
		if(next) engine.nextActivePlayerIndex();
		if (engine.getNextState().equals(GameState.START_ROUND)){
			startNextRound();
		} else {
			startNextTurn();
		}
	}
	
	private class LandGrantButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			endLandGrant();
		}
	}

	private class NextScreen implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			String currState = GameState.getState();
			switch (arg0.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (currState.equals(GameState.START_ROUND)){
					startRoundOrTurn(true);
				} else if (currState.equals(GameState.START_TURN)){
					if (engine.getNextState().equals(GameState.LANDGRANT)){
						startLandGrant();
					} else {
						startGameLoop();
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	}

	private void showWampusMessage(int reward){
		gameplayPanel.showMessage("You caught the wambuzz! He gave you $"+reward+
				" as a reward!");
	}
	
	private void save(String filename) {
        try {
            /*
             * Create the object output stream for serialization.
             * We are wrapping a FileOutputStream since we
             * want to save to disk.  You can also save to socket
             * streams or any other kind of stream.
             */
           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
           
           /*
            * The only real call we need.  The stream buffers the output and reuses
            * data, so if you are serializing very frequently, then the object values might
            * not actually change because the old serialized object is being reused.
            * 
            * To fix this you can try writeUnshared() or you can reset the stream.
            * out.reset();
            */
           out.writeObject(engine);
           out.close();
       } catch (FileNotFoundException e) {
           System.out.println("Save file not found, Error:"+e);
       } catch (IOException e) {
           e.printStackTrace();
    	   //System.out.println("IO error on saving, Error:"+e);
       }
        
    }
	
	private MULEGameEngine load(String filename){
		MULEGameEngine eng = null;
        try {
        	//Create the input stream. Since we want to read from the disk, we wrap a file stream.
        	ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        	//Now read the saved game engine in with one call. 
        	eng = (MULEGameEngine) in.readObject();
        	in.close();
       } catch (FileNotFoundException e) {
           System.out.println("No saved game file found, error:\n"+e);
       } catch (IOException e) {
    	   e.printStackTrace();
           //System.out.println("IO error on loading, error:\n"+e);
       } catch (ClassNotFoundException e) {
           System.out.println("MULEGameEngine class not found on loading, error:\n"+e);
       }
       return eng;
	}
}


