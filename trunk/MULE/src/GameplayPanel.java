import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;



/**
 * Jpanel object for M.U.L.E. game that displays everything that goes on during the 
 * normal game play. 
 * 		1) 	must use setMapAndPlayers(MULEMap, Players[]) before displaying anything
 * 		2) 	must use repaint() directly after a Tile is altered to change update the 
 * 			panel
 * 
 * @author John Certusi (jcertusi3)
 *
 */
@SuppressWarnings("serial")
public class GameplayPanel extends JPanel {
	
	//Screen States
	private final String INSIDE_TOWN = "INSIDE_TOWN";
	private final String GAME_MAP = "GAME_MAP";
	private String currentState;
	
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)

    /**
     * Makes a new gameplayPanel
     */
    public GameplayPanel() {
        setBackground(Color.BLACK);
        setLayout(null);
        currentState = GAME_MAP;
    }
    
    /**
     * Sets the map and player list inside the panel. Needed to display the map
     * and Tile characteristics
     * 
     * @param gameMap		map for the game
     * @param playerList	players in the game
     */
    public void setMapAndPlayers(MULEMap gameMap, Player[] playerList){
    	this.gameMap = gameMap;
    	this.playerList = playerList;
    }
    
    public void setState(String state){
    	currentState = state;
    }
    
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
    	switch (currentState){
    	case GAME_MAP:
    		if (this.gameMap != null){
    			super.paintComponent(g);
    			gameMap.draw(g);
    			/////////////////////////////////////////temporary test code<<<
    			for(int i=0; i<playerList.length; i++){
    				playerList[i].move(0, i*100, i*100);
    				playerList[i].draw(g);
    			}
    			/////////////////////////////////////////temporary test code<<<
    		}
    		break;
    	case INSIDE_TOWN:
    		super.paintComponent(g);
    		break;
    	}
    }
}
