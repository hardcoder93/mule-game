
/**
 * Tile class for M.U.L.E. game
 * A collection of these tiles make up the Map in the game
 */
public class Tile {
	int id;
	String type;
	String owner;
	boolean vacant;
	boolean wombat;
	char direction;
	
	/**
	 * Constructor for Tile class
	 * @param type		type of Tile ("P","R","M1","M2", or "M3")
	 * @param direction	orientation of the tile (important for river)
	 * @param id		unique id
	 */
	public Tile(String type, int id){
		this.type = "VHB".contains(type) ? "R" : type;
		this.direction = "VHB".contains(type) ? type.charAt(0) : 'V';
		this.id = id;
		this.owner = null;
		this.wombat = false;	
		this.vacant = true;
	}

	/**
	 * Set the owner of the tile
	 * @param owner	name of the owner for the Tile
	 */
	public void setOwner(String owner){
		this.owner = owner;
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

}