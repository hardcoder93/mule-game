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
	private final String fileMountain1 = "tmpTile.png";
	private final String fileMountain2 = "tmpTile.png";
	private final String fileMountain3 = "tmpTile.png";
	private final String filePlains = "tmpTile.png";
	private final String fileRiver = "tmpTile.png";
	private final String fileTown = "tmpTile.png";
	
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
			mountain1 = ImageIO.read(new File(fileMountain1));
			mountain2 = ImageIO.read(new File(fileMountain2));
			mountain3 = ImageIO.read(new File(fileMountain3));
			plains = ImageIO.read(new File(filePlains));
			river = ImageIO.read(new File(fileRiver));
			town = ImageIO.read(new File(fileTown));
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
        	drawMap(g);
        	mapDrawn = true;
        }
        updateTiles(g);
    }

    /**
     * Updates the Tiles in the Map. Currently adds or removes the border, but
     * could be used to add in the Mule and Wombat
     * @param g
     */
	private void updateTiles(Graphics g) {
		Tile currTile;
		ArrayList<Integer> tilesToPaint = gameMap.getAlteredTiles();
		while(!tilesToPaint.isEmpty()) {
			currTile = gameMap.getTile(tilesToPaint.remove(0));
			if (currTile.isVacant()){
				drawTile(currTile, currTile.getXCoord(), currTile.getYCoord(), g);
			}
			else
				drawBorder(currTile.getOwnerColor(), getXLocation(currTile.getXCoord()), 
						getYLocation(currTile.getYCoord()), g);	
		}

	}

	/**
	 * Gets the y pixel Location of a tile based on its y coordinate
	 * @param yCoord	y coordinate
	 * @return			y pixel location (top of tile)
	 */
	private int getYLocation(int yCoord) {
		return yCoord * tileSize.height + MAP_Y_OFFSET;
	}

	/**
	 * Gets the x pixel location of a tile based on its x coordinate
	 * @param xCoord	x coordinate
	 * @return			x pixel location (left side of tile)
	 */
	private int getXLocation(int xCoord) {
		return xCoord * tileSize.width + MAP_X_OFFSET;
	}

	/**
	 * Draws each Tiles image onto the panel in its (x,y) location to represent the map
	 * @param g
	 */
	private void drawMap(Graphics g) {
		Tile tileXY;
		for (int x = 0; x < gameMap.WIDTH; x++)				
			for (int y = 0; y < gameMap.HEIGHT; y++){
				tileXY = gameMap.getTile(x, y);
				drawTile(tileXY, x, y, g);
			}
	}

	/**
	 * Draws a tile based on its image, x coordinate, and y coordinate
	 * 
	 * @param tileXY	tile to be drawn
	 * @param xCoord	x coordinate
	 * @param yCoord	y coordinate
	 * @param g			graphics object
	 */
	private void drawTile(Tile tileXY, int xCoord, int yCoord, Graphics g){
		Image tileImage;
		switch (tileXY.getType()) {
			case "M1": tileImage = mountain1;
				break;
			case "M2": tileImage = mountain2;
				break;
			case "M3": tileImage = mountain3;
				break;
			case "R": tileImage = river;
				break;
			case "Town": tileImage = town;
				break;
			default: tileImage = plains;
				break;
		}
		// draw tile image
		g.drawImage(tileImage, getXLocation(xCoord), getYLocation(yCoord), null);	
	}

	/**
	 * Draws a border around a tile in a specified color. Used when a player owns a tile
	 * 
	 * @param color		player's color (as a String)
	 * @param xLoc		x pixel location of left side of tile
	 * @param yLoc		y pixel location of top of tile
	 * @param g			graphics object
	 */
	private void drawBorder(String color, int xLoc, int yLoc, Graphics g) {
		//Convert (String color) to actual Color object 
		Color c;
		try {
		    Field field = Color.class.getField(color);
		    c = (Color)field.get(null);
		} catch (Exception e) {
		    c = null;
		}
		g.setColor(c);
		
		// draw rectangle
		for (int i = 0; i < TILE_BORDER_WIDTH; i++)
			g.drawRect(xLoc + i, yLoc + i, tileSize.width - 2 * i, tileSize.height - 2 * i);		
	}
}