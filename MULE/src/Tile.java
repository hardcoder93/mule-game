import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;


/**
 * Tile class for M.U.L.E. game
 * A collection of these tiles make up the Map in the game
 * 
 * @author John Certusi (jcertusi3)
 */
public class Tile implements Drawable{
	
	private final int TILE_BORDER_WIDTH = 10;
	
	int id;
	int xCoord;
	int yCoord;
	int xLoc;
	int yLoc;
	String type;
	Color ownerColor;
	Player owner;			//player number
	boolean vacant;
	boolean wombat;
	char direction;
	Image tileImage;
	
	/**
	 * Constructor for Tile class
	 * @param type		type of Tile ("P","R","M1","M2", or "M3")
	 * @param direction	orientation of the tile (important for river)
	 * @param id		unique id
	 */
	public Tile(String type, int id, int xCoord, int yCoord){
		this.type = "VHB".contains(type) ? "R" : type;
		this.direction = "VHB".contains(type) ? type.charAt(0) : 'V';
		this.id = id;
		this.wombat = false;	
		this.vacant = true;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.xLoc = xCoord * tileImages.TILE_SIZE.width;
		this.yLoc = yCoord * tileImages.TILE_SIZE.height;
		this.setImage();
	}

	/**
	 * Set the owner of the tile
	 * @param owner	name of the owner for the Tile
	 */
	public void setOwner(Player owner){
		this.owner = owner;
		try {
		    Field field = Color.class.getField(owner.getColor());
		    this.ownerColor = (Color)field.get(null);
		} catch (Exception e) {
		    this.ownerColor = null;
		}
		this.vacant = false;
	}
	
	/**
	 * Remove the owner of the tile. Sets the vacant value to true
	 */
	public void removeOwner(){
		this.vacant = true;
	}
	
	/**
	 * Set the Tile to have the wombat or to not have the wombat
	 * @param hasWombat	true if Tile should have wombat; fals eif not
	 */
	public void setWombat(boolean hasWombat){
		this.wombat = hasWombat;
	}
	
	/**
	 * Getter for the id of the Tile
	 * @return	id of Tile
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * Getter for the type of the Tile
	 * @return	type of tile ("P","R","M1","M2", or "M3")
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Determines if a tile has the Wombat. 
	 * @return	true if tile has wombat; false if tile does not
	 */
	public boolean hasWombat(){
		return wombat;
	}
	
	/**
	 * Determines if a tile is vacant 
	 * @return	true if tile is vacant; false if tile is not
	 */
	public boolean isVacant(){
		return vacant;
	}
	
	/**
	 * Getter for the owner
	 * @return the owner as the player #
	 */
	public Player getOwner(){
		return owner;
	}

	/**
	 * Get the y coordinate for the tile
	 * @return	y coordinate
	 */
	public int getYCoord() {
		return yCoord;
	}

	/**
	 * Get the x coordinate for the tile
	 * @return	x coordinate
	 */
	public int getXCoord() {
		return xCoord;
	}
	
	public void setImage(){
		switch (this.type) {
		case "M1": tileImage = tileImages.MOUNTAIN1;
			break;
		case "M2": tileImage = tileImages.MOUNTAIN2;
			break;
		case "M3": tileImage = tileImages.MOUNTAIN3;
			break;
		case "R": tileImage = tileImages.RIVER;
			break;
		case "Town": tileImage = tileImages.TOWN;
			break;
		default: tileImage = tileImages.PLAINS;
			break;
	}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(tileImage, xLoc, yLoc, null);
		if (!vacant)
			drawBorder(g);
	}
	
	/**
	 * Draws a border around a tile in a specified color. Used when a player owns a tile
	 * 
	 * @param color		player's color (as a String)
	 * @param xLoc		x pixel location of left side of tile
	 * @param yLoc		y pixel location of top of tile
	 * @param g			graphics object
	 */
	private void drawBorder(Graphics g) {
		g.setColor(ownerColor);
		// draw rectangle
		for (int i = 0; i < TILE_BORDER_WIDTH; i++)
			g.drawRect(xLoc + i, yLoc + i, tileImages.TILE_SIZE.width - 2 * i, tileImages.TILE_SIZE.height - 2 * i);		
	}

}