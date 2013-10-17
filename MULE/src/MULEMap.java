import java.awt.Graphics;
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
	private final int PLAYER_WIDTH = 50;

	private ArrayList<Tile> tileList;
	private ArrayList<Integer> alteredTiles;

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
	
	public boolean isTownTile(int xLoc, int yLoc){
		return getTileFromLocation(xLoc, yLoc).getType().equals(TOWN);
	}
	
	public boolean isRiverTile(int xLoc, int yLoc){
		return getTileFromLocation(xLoc, yLoc).getType().equals(R);
	}
	
	public boolean isMountainTile(int xLoc, int yLoc){
		String type = getTileFromLocation(xLoc, yLoc).getType();
		return (type.equals(M1) || type.equals(M2) || type.equals(M3));
	}
	
	public boolean isTileVacant(int xLoc, int yLoc){
		return getTileFromLocation(xLoc, yLoc).isVacant();
	}
	
	public boolean isValidLocation(int xLoc, int yLoc){
		if (xLoc >= 0 && xLoc < (WIDTH * MapImages.TILE_SIZE.width - PLAYER_WIDTH))
			if (yLoc >= 0 && yLoc < (HEIGHT * MapImages.TILE_SIZE.height - PLAYER_WIDTH))
				return true;
		return false;
	}

	
	@Override
	public void draw(Graphics g) {
		switch (GameState.getState()){
		case GameState.PLAYING_MAP:
			for (int i = 0; i < NUM_TILES; i++)
				tileList.get(i).draw(g);
			break;
		case GameState.PLAYING_TOWN:
			g.drawImage(MapImages.TOWN_MAP, 0, 0, null);
			break;
		}

	}

}
