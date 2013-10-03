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
	private MuleMap map;
	private Player[] players;
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
			map = new MuleMap(mapType); //Assuming class MULEMap has constructor with boolean 
			break;			
		case "Random": 
			map = new MuleMap(mapType);	  //where true=standard and false=random
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
		try{
			while(players[i]!=null) //Get the player number (ie. next open slot in player array)
				i++;
			return i;
		}catch(IndexOutOfBoundsException e){
			return -1;
		}
	}
}
