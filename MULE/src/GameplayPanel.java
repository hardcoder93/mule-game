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
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)
	private Player activePlayer;
	
	//Screen States
	private String panelState;
	
    /**
     * Makes a new gameplayPanel
     */
    public GameplayPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
        panelState = "init";
    }
   
	public void setUpScoreboard () {
    	if (playerList.length == 2) {
    		JLabel lblNewLabel = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel lblNewLabel_1 = new JLabel(""+ playerList[1].getName()) ;
        	JLabel lblNewLabel_4 = new JLabel("NULL");
    		
        	lblNewLabel.setBounds(65, 460, 180, 100);
            add(lblNewLabel);
             
            
            lblNewLabel_1.setBounds(245, 460, 180, 100);
            add(lblNewLabel_1);
            
            lblNewLabel_4.setBounds(785, 460, 180, 100);
            add(lblNewLabel_4);
    	}else
    	if (playerList.length == 3) {
    		JLabel lblNewLabel = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel lblNewLabel_1 = new JLabel(""+ playerList[1].getName()) ;
        	JLabel lblNewLabel_2 = new JLabel(""+ playerList[2].getName()) ;
        	JLabel lblNewLabel_4 = new JLabel("NULL");
    		lblNewLabel.setBounds(65, 460, 180, 100);
            add(lblNewLabel);
             
            
            lblNewLabel_1.setBounds(245, 460, 180, 100);
            add(lblNewLabel_1);
            
            lblNewLabel_2.setBounds(425, 460, 180, 100);
            add(lblNewLabel_2);
            
            lblNewLabel_4.setBounds(785, 460, 180, 100);
            add(lblNewLabel_4);
    	}
         
    	else {
    		JLabel lblNewLabel = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel lblNewLabel_1 = new JLabel(""+ playerList[1].getName()) ;
        	JLabel lblNewLabel_2 = new JLabel(""+ playerList[2].getName()) ;
        	JLabel lblNewLabel_3 = new JLabel(""+ playerList[3].getName()) ;
        	JLabel lblNewLabel_4 = new JLabel("NULL");
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
    	setUpScoreboard();
    }
    
    /**
     * Sets the activePlayer instance variable to the input player object.
     * 
     * @param p The player to set.
     */
    public void setActivePlayer(Player p){
    	activePlayer = p;
    }
    
    
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
    	if (panelState != null && !panelState.equals(GameState.getState())){
    		super.paintComponent(g);
    		panelState = GameState.getState();
    	}
    	if (this.gameMap != null){
    		super.paintComponent(g);
    		if(GameState.getState().equals(GameState.WAITING)){
    			g.setColor(Color.BLACK);
    			g.fillRect(0, 0, 900, 500);
    			g.setColor(Color.WHITE);
    			g.drawString("PAUSED - Press any key to continue", 350, 250);
    		}else{
    		gameMap.draw(g);
    		activePlayer.draw(g);}
    	}
    }
}
