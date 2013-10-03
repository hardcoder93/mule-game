/**
 * The MULEGameEngine class handles the main game objects and their interactions
 * while the game is running (ie. players, store, map, etc.)
 * 
 * @author Chris Jenkins
 *
 */
public class MULEGameEngine {
	//Instance Data
	private int difficulty;
	private MULEMap map;
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
	public MULEGameEngine(int difficulty, String mapType, int numPlayers){
		this.difficulty = difficulty;
		
		switch(mapType){
		case "Standard": 
			map = new MULEMap(true); //Assuming class MULEMap has constructor with boolean 
			break;			
		case "Random": 
			map = new MULEMap(false);	  //where true=standard and false=random
			break;
		}
		
		players = new Player[numPlayers];
		store = new Player("STORE", "", "");
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
		int i=0;
		try{
			while(players[i]!=null) //Get the player number (ie. next open slot in player array)
				i++;
			players[i] = new Player(name, color, race);
			return true;
		}catch(IndexOutOfBoundsException e){
			return false;
		}
	}
}
