import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author John Certusi (jcertusi3)
 *
 */
public class MULEMap implements Drawable{

	// Map Size
	public final int HEIGHT = 5;
	public final int WIDTH = 9;
	private final int NUM_TILES = HEIGHT * WIDTH;
	private final int TOWN_LOCATION = (HEIGHT * WIDTH) / 2;

	// Map Elements
	private final String M1 = "M1"; // One Mountain
	private final String M2 = "M2";
	private final String M3 = "M3";
	private final String P = "P"; // Plain
	private final String V = "V"; // Vertical River
	private final String H = "H"; // Horizontal River
	private final String B = "B"; // Intersecting Rivers
	private final String R = "R";
	private final String TOWN = "Town";
	public static String BIG_MAP = "BIGMAP";
	public static String TOWN_MAP = "TOWNMAP";
	
	private ArrayList<Tile> tileList;
	private ArrayList<Integer> alteredTiles;

	private String activeMap = BIG_MAP;
	
	/**
	 * Constructs the Map object based on either a standard, predefined map or a
	 * randomly generated one
	 * 
	 * @param type the type of map; "Standard" or "Random"
	 */
	public MULEMap(String type) {
		int ID = 0;
		tileList = new ArrayList<Tile>();
		alteredTiles = new ArrayList<Integer>();
		String[] mapArea = type.equals("Random") ? createRandomMap() : createStandardMap();
		for (int y = 0; y < HEIGHT; y++){
			for (int x = 0; x < WIDTH; x++){
				tileList.add(new Tile(mapArea[ID], ID, x, y));
				ID++;
			}
		}
	}

	/**
	 * Sets the map to the standard map for the M.U.L.E. game.
	 */
	private String[] createStandardMap() {

		// Default map
		String standMap = "P,P,M1,P,V,P,M3,P,P," +
						  "P,M1,P,P,V,P,P,P,M3," +
						  "M3,P,P,P,Town,P,P,P,M1," +
						  "P,M2,P,P,V,P,M2,P,P," +
						  "P,P,M2,P,V,P,P,P,M2";
		return standMap.split(",");
	}

	/**
	 * Sets the map to a randomly generated one.
	 */
	private String[] createRandomMap() {
		
		final int MAX_MOUNTAINS = 10;
		final int MAX_RIVERS = 2;
		
		Random randGen = new Random();
		String[] mapArea = new String[WIDTH * HEIGHT];
		int randInt;
		String type;
		
		//Set up Town
		mapArea[TOWN_LOCATION] = TOWN;
		
		//Set up Rivers
		for (int i = randGen.nextInt(MAX_RIVERS + 1); i > 0; i--){
			//type = randGen.nextDouble() < .5 ? V : H;
			type = V;
			randInt = type.equals(V) ? randGen.nextInt(WIDTH) : randGen.nextInt(HEIGHT) * WIDTH;
			for (int j = (type.equals(V) ? HEIGHT : WIDTH); j > 0; j--){
				if (mapArea[randInt] == null)
					mapArea[randInt] = type;
				else if ((mapArea[randInt] != TOWN) && (mapArea[randInt] != type))
					mapArea[randInt] = B;
				randInt += type.equals(V) ? WIDTH : 1;
			}
		}
		
		//Set up Mountains
		for (int i = randGen.nextInt(MAX_MOUNTAINS + 1); i > 0; i--){
			randInt = randGen.nextInt(3);
			type = randInt == 0 ? M1 : randInt == 1 ? M2 : M3;
			randInt = randGen.nextInt(NUM_TILES);
			while (mapArea[randInt] != null)
				randInt = randGen.nextInt(NUM_TILES);
			mapArea[randInt] = type;
		}
		
		//Fill empty areas with Plains
		for (int i = 0; i < NUM_TILES; i++)
			mapArea[i] = mapArea[i] == null ? P : mapArea[i];
			
		return mapArea;
	}
	
	/**
	 * Set a map tile's owner
	 * 
	 * @param xCoord		x coordinate location
	 * @param yCoord		y coordinate location
	 * @param playerNum		player number (index of player array)
	 * @param playerColor	player color (as a String)
	 */
	public void setTileOwner(int xCoord, int yCoord, Player player){
		int ID = calculateID(xCoord, yCoord);
		tileList.get(ID).setOwner(player);
		alteredTiles.add(ID);
	}
	
	/**
	 * Remove the owner from a tile
	 * 
	 * @param xCoord	x coordinate of tile
	 * @param yCoord	y coordinate of tile
	 */
	public void removeTileOwner(int xCoord, int yCoord){
		int ID = calculateID(xCoord, yCoord);
		getTile(ID).removeOwner();
		System.out.print(getTile(ID).isVacant());
		alteredTiles.add(ID);
	}
	
	/**
	 * Getter for tileList
	 * @return	tileList
	 */
	public ArrayList<Tile> getTiles(){
		return tileList;
	}
	
	/**
	 * Calculate a tiles ID based on its map position
	 * @param xCoord	x coordinate
	 * @param yCoord	y coordinate
	 * @return			tile ID
	 */
	public int calculateID(int xCoord, int yCoord){
		return WIDTH*yCoord + xCoord;
	}
	
	/**
	 * Get the tile at a specific location
	 * @param xCoord	x coordinate
	 * @param yCoord	y coordinate
	 * @return			tile at (x,y)
	 */
	public Tile getTileFromCoords(int xCoord, int yCoord){
		return tileList.get(WIDTH*yCoord + xCoord);
	}
	
	/**
	 * gets a tile based on its ID
	 * @param ID	tile ID
	 * @return		tile with ID
	 */
	public Tile getTile(int ID){
		return tileList.get(ID);
	}

	/**
	 * Gets a list of altered Tiles. Used for updating borders on the map
	 * @return	list of altered Tiles
	 */
	public ArrayList<Integer> getAlteredTiles() {
		return alteredTiles;		
	}
	
	private Tile getTileFromLocation(int xLoc, int yLoc){
		int xCoord = xLoc / MapImages.TILE_SIZE.width;
		int yCoord = yLoc / MapImages.TILE_SIZE.height;
		return getTileFromCoords(xCoord, yCoord);
	}
	
	/**
	 * Returns the string constant specifying which map is currently active.
	 * 
	 * @return The active map's String constant.
	 */
	public String getActiveMap(){
		return activeMap;
	}
	
	/**
	 * Sets the active map to the input String, but only if it is one of 
	 * the accepted String constants.
	 * 
	 * @param inMap The desired active map.
	 */
	public void setActiveMap(String inMap){
		if(inMap.equals(BIG_MAP) || inMap.equals(TOWN_MAP))
			activeMap = inMap;
	}
	
	/**
	 * Checks to see if the input location is on the town tile in the 
	 * center of the map.
	 * 
	 * @param xLoc The x-coordinate.
	 * @param yLoc The y-coordinate.
	 * @return True if on the town tile, false if not.
	 */
	public boolean isTownTile(int xLoc, int yLoc){
		return getTileFromLocation(xLoc, yLoc).getType().equals(TOWN);
	}
	
	/**
	 * This method checks to see if the input location is on a river tile.
	 * 
	 * @param xLoc The x-coordinate of the position.
	 * @param yLoc The y-coordinate of the position.
	 * @return True if on a river, false if not.
	 */
	public boolean isRiverTile(int xLoc, int yLoc){
		if(GameState.getState().equals(GameState.PLAYING_MAP))
			return (getTileFromLocation(xLoc, yLoc).getType().equals(R) ||
					getTileFromLocation(xLoc, yLoc).getType().equals(H));
		return false;
	}
	
	/**
	 * This method checks to see if the input location is on a mountain tile.
	 * 
	 * @param xLoc The x-coordinate of the position.
	 * @param yLoc The y-coordinate of the position.
	 * @return True if on a mountain, false if not.
	 */
	public boolean isMountainTile(int xLoc, int yLoc){
		if(GameState.getState().equals(GameState.PLAYING_MAP)){
			String type = getTileFromLocation(xLoc, yLoc).getType();
			return (type.equals(M1) || type.equals(M2) || type.equals(M3));
		} return false;
	}
	
	public boolean isTileVacant(int xLoc, int yLoc){
		return getTileFromLocation(xLoc, yLoc).isVacant();
	}
	
	/**
	 * This method tests whether or not the input x-y coordinates are a 
	 * valid position for the player to have.
	 * 
	 * @param xLoc The x-coordinate to test.
	 * @param yLoc The y-coordinate to test.
	 * @return True if valid, false if not.
	 */
	public boolean isValidLocation(int xLoc, int yLoc){
		//If inside the town, we need to be able to exit (move off the screen to right or left)
		if(GameState.getState().equals(GameState.PLAYING_TOWN)){
			if(yLoc>=185 && yLoc<=415)
				if(xLoc>=-Player.PLAYER_WIDTH/2 && 
						xLoc<=WIDTH*MapImages.TILE_SIZE.width-Player.PLAYER_WIDTH/2)
					return true;
		}
		if (xLoc >= 0 && xLoc < (WIDTH * MapImages.TILE_SIZE.width - Player.PLAYER_WIDTH))
			if (yLoc >= 0 && yLoc < (HEIGHT * MapImages.TILE_SIZE.height - Player.PLAYER_HEIGHT))
				return true;
		
		return false;
	}
	
	/**
	 * This method checks if the player is currently off the map; this is used
	 * to test if the player is leaving the store.
	 * 
	 * @param xLoc The current x-location of the player.
	 * @param yLoc The current y-location of the player.
	 * @return True if off the map, false if not.
	 */
	public boolean isOffMap(int xLoc, int yLoc){
		return (xLoc < 0 || xLoc+Player.PLAYER_WIDTH > WIDTH*MapImages.TILE_SIZE.width || 
				yLoc < 0 || yLoc+Player.PLAYER_HEIGHT > HEIGHT * MapImages.TILE_SIZE.height);
	}
	
	/**
	 * This method moves the player to the appropriate new location when 
	 * switching between maps.
	 * 
	 * @param xLoc The current x-location of the player.
	 * @return The new x-location of the player.
	 */
	public int mapSwitchX(int xLoc){
		if(xLoc > WIDTH*MapImages.TILE_SIZE.width/2){ //If true, player is entering/exiting on the right.
			if(isOffMap(xLoc, 0)) //If true, player is leaving town; put player to the right of town tile.
				return 500;
			return (WIDTH*MapImages.TILE_SIZE.width-Player.PLAYER_WIDTH); //Player is entering town.
		}else{ //Player is entering/exiting on the left.
			if(isOffMap(xLoc, 0)) //If true, player is leaving town; put player to the left of town tile.
				return (400-Player.PLAYER_WIDTH);
			return 0; //Player is entering town.
		}
	}
	
	@Override
	public void draw(Graphics g) {
		switch (GameState.getState()){
		case GameState.PLAYING_TOWN:
			g.drawImage(MapImages.TOWN_MAP, 0, 0, 900, 500, null);
			break;
		default:
			for (int i = 0; i < NUM_TILES; i++)
				tileList.get(i).draw(g);
			break;
		}

	}

	public boolean isBuyable(Point coords) {
		if (isValidMouseClick(coords))
			if (!isTownTile(coords.x, coords.y) && isTileVacant(coords.x, coords.y))
				return true;
		return false;
	}

	private boolean isValidMouseClick(Point coords) {
		return ((coords.x >= 0 && coords.x < WIDTH * MapImages.TILE_SIZE.width) &&
				(coords.y >= 0 && coords.y < HEIGHT * MapImages.TILE_SIZE.height));
	}

	public Point calculateCoordsFromLocation(Point location){
		int xCoord = location.x / MapImages.TILE_SIZE.width;
		int yCoord = location.y / MapImages.TILE_SIZE.height;
		return new Point(xCoord, yCoord);
	}
	
	public void setTileOwner(Point location, Player player) {
		Point tileCoords = calculateCoordsFromLocation(location);
		setTileOwner(tileCoords.x, tileCoords.y, player);
	}

}
