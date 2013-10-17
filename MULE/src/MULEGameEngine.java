/**
 * The MULEGameEngine class handles the main game objects and their interactions
 * while the game is running (ie. players, store, map, etc.)
 * 
 * @author Chris Jenkins
 *
 */
public class MULEGameEngine {
	//Instance Data
	private String difficulty;
	private MULEMap map;
	private Player[] players;
	private int activePlayerInd = 0;
	private int currentRound = 0;
	private Player store;
		
	/**
	 * Builds a MULEGameEngine object, setting the difficulty of the game,
	 * creating the map, and initializing the empty player list.
	 * 
	 * @param difficulty The difficulty of the game.
	 * @param mapType The map upon which the game is to be played.
	 * @param numPlayers The number of players in the game.
	 */
	public MULEGameEngine(String difficulty, String mapType, int numPlayers){
		this.difficulty = difficulty;
		
		switch(mapType){
		case "Standard": 
			map = new MULEMap(mapType);
			break;			
		case "Random": 
			map = new MULEMap(mapType);
			break;
		}
		
		players = new Player[numPlayers];
		store = new Player("STORE", difficulty, "", "");
	}
	
	/**
	 * This method adds a player to the list of players; it finds the next 
	 * open spot in the list, and inserts a Player object with the specified
	 * attributes in that spot. Returns a boolean that indicates whether the
	 * player was added successfully or if the list was already full.
	 *  
	 * @param name The player's name.
	 * @param color The player's color.
	 * @param race The player's race.
	 * @return True if player was inserted, false if the list is full.
	 */
	public boolean addPlayer(String name, String color, String race){
		int index = getNextPlayerSlot();
		if(index==-1) return false;
		players[index] = new Player(name, difficulty, race, color);
		return true;
	}
	
	/**
	 * Gets the next open position in the list of players and returns that
	 * index. If the list is full, it returns -1.
	 * 
	 * @return The index of the next slot, or -1 if full.
	 */
	public int getNextPlayerSlot(){
		int i=0;
		while(i<players.length && players[i]!=null) //Get the player number (ie. next open slot in player array)
			i++;
		if(i==players.length) return -1;
		return i;
	}
	
	/**
	 * Gets the map object from the game engine.
	 * 
	 * @return The map.
	 */
	public MULEMap getMap(){
		return map;		
	}
	
	/**
	 * Gets the players that are in the game, excluding the store.
	 * 
	 * @return An array of Player objects representing the players in the game.
	 */
	public Player[] getPlayers(){
		return players;
	}
	
	public Player getActivePlayer(){
		return players[activePlayerInd];
	}
	
	public int getCurrentRound(){
		return currentRound;
	}
	
	public void nextRound(){
		currentRound++;
	}
	
	public void movePlayer(int distX, int distY){
		Player active = players[activePlayerInd];
		int newX = active.getX()+distX;
		int newY = active.getY()+distY;
		if(map.isValidLocation(newX, newY)){
			if(GameState.getState().equals(GameState.PLAYING_MAP) &&
					map.isTownTile(newX, newY)){
				GameState.setState(GameState.PLAYING_TOWN);
			}
			if(onRiverTile() || onMountainTile())
				active.move(1, distX, distY);
			else if(map.isValidLocation(active.getX()+10*distX, active.getY()+10*distY))
				active.move(10, distX, distY);
		}
	}
	
	public boolean onRiverTile(){
		Player active = players[activePlayerInd];
		return (map.isRiverTile(active.getX(), active.getY()) ||
				map.isRiverTile(active.getX()+Player.PLAYER_WIDTH, active.getY()+Player.PLAYER_HEIGHT));
	}
	
	public boolean onMountainTile(){
		Player active = players[activePlayerInd];
		return (map.isMountainTile(active.getX(), active.getY()) ||
				map.isMountainTile(active.getX()+Player.PLAYER_WIDTH, active.getY()+Player.PLAYER_HEIGHT));
	}
}
