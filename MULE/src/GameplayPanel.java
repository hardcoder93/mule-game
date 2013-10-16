import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	
	//Map display attributes
	private final int MAP_X_OFFSET = 0;			//increment to move map away from left edge of screen
	private final int MAP_Y_OFFSET = 0;			//increment to move map away from top edge of screen
	private final int TILE_BORDER_WIDTH = 10;	//width of border for an owned tile (must be positive)
	
	//Image file names --- change from "tmpTile.png"
	private final String IMAGE_FILE_MOUNTAIN1 = "IMAGES/mountain_1.png";
	private final String IMAGE_FILE_MOUNTAIN2 = "IMAGES/mountain_2.png";
	private final String IMAGE_FILE_MOUNTAIN3 = "IMAGES/mountain_3.png";
	private final String IMAGE_FILE_PLAINS = "IMAGES/Grass.png";
	private final String IMAGE_FILE_RIVER = "IMAGES/River.png";
	private final String IMAGE_FILE_TOWN = "IMAGES/techTower.png";
	
	//Images
	private Image mountain1;
	private Image mountain2;
	private Image mountain3;
	private Image plains;
	private Image river;
	private Image town;
	private Dimension tileSize;
	
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)
	private boolean mapDrawn = false;	//used to determine if map needs to be drawn
	
    /**
     * Makes a new gameplayPanel
     */
    public GameplayPanel() {
    	//Read in Tile images from files
        try {
			mountain1 = ImageIO.read(new File(IMAGE_FILE_MOUNTAIN1));
			mountain2 = ImageIO.read(new File(IMAGE_FILE_MOUNTAIN2));
			mountain3 = ImageIO.read(new File(IMAGE_FILE_MOUNTAIN3));
			plains = ImageIO.read(new File(IMAGE_FILE_PLAINS));
			river = ImageIO.read(new File(IMAGE_FILE_RIVER));
			town = ImageIO.read(new File(IMAGE_FILE_TOWN));
		} catch (IOException e) {
			System.out.print("One or more Tile Image files does not exist");
		}
        
        setBackground(Color.BLACK);
        
        //set tile size (based on image size)
        tileSize = new Dimension(mountain1.getWidth(null), mountain1.getHeight(null));
        setPreferredSize(tileSize);
        setMinimumSize(tileSize);
        setMaximumSize(tileSize);
        setSize(tileSize);
        setLayout(null);
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
    
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        if (this.gameMap != null && !mapDrawn){
        	super.paintComponent(g);
        	gameMap.draw(g);
        	/////////////////////////////////////////temporary test code<<<
        	for(int i=0; i<playerList.length; i++){
        		playerList[i].move(0, i*100, i*100);
        		playerList[i].draw(g);
        	}
        	/////////////////////////////////////////temporary test code<<<
        	mapDrawn = true;
        }
    }
}
