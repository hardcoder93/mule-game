import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
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
	JLabel lblNewLabel = new JLabel();
	JLabel lblNewLabel_1 = new JLabel();
	JLabel lblNewLabel_2 = new JLabel();
	JLabel lblNewLabel_3 = new JLabel();
	JLabel lblNewLabel_4 = new JLabel();
	//Screen States
	private final String INSIDE_TOWN = "INSIDE_TOWN";
	private final String GAME_MAP = "GAME_MAP";
	
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)
	

	
    /**
     * Makes a new gameplayPanel
     */
    public GameplayPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
        
       
        lblNewLabel.setBounds(65, 460, 180, 100);
        add(lblNewLabel);
        
       
        lblNewLabel_1.setBounds(245, 460, 180, 100);
        add(lblNewLabel_1);
        
        
        lblNewLabel_2.setBounds(425, 460, 180, 100);
        add(lblNewLabel_2);
        
        
        lblNewLabel_3.setBounds(605, 460, 180, 100);
        add(lblNewLabel_3);
        
        
        lblNewLabel_4.setBounds(785, 460, 180, 100);
        add(lblNewLabel_4);
        
       
        
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
    
    
    public void setPlayerName (Player[] playerList) {
    	this.playerList = playerList;
    	if (playerList[2]==null) {
    		lblNewLabel.setText("" + playerList[0].getName());
        	lblNewLabel.setText("" + playerList[1].getName());
    	}
    	if (playerList[3]==null) {
    		lblNewLabel.setText("" + playerList[0].getName());
        	lblNewLabel.setText("" + playerList[1].getName());
        	lblNewLabel.setText("" + playerList[2].getName());
    	}
    	else {
    		lblNewLabel.setText("" + playerList[0].getName());
        	lblNewLabel.setText("" + playerList[1].getName());
        	lblNewLabel.setText("" + playerList[2].getName());
        	lblNewLabel.setText("" + playerList[3].getName());
    	}
    	
    }
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
    	
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
    }
}
