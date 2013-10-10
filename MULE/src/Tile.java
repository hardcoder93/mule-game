
/**
 * Tile class for M.U.L.E. game
 * A collection of these tiles make up the Map in the game
 * 
 * @author John Certusi (jcertusi3)
 */
public class Tile {
	int id;
	int xCoord;
	int yCoord;
	String type;
	String ownerColor;
	int owner;			//player number
	boolean vacant;
	boolean wombat;
	char direction;
	
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
	}

	/**
	 * Set the owner of the tile
	 * @param owner	name of the owner for the Tile
	 */
	public void setOwner(int owner, String color){
		this.owner = owner;
		this.ownerColor = color;
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
	public int getOwner(){
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

	/**
	 * Get the player color for the tile's owner
	 * @return player color (as a String)
	 */
	public String getOwnerColor() {
		return ownerColor;
	}

}