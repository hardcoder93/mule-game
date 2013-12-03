import java.util.ArrayList;
import java.util.Random;


public class AI {
	
	public static boolean landGrant(Player player, MULEMap map, int round){
		Tile tileToBuy;
		String tilePickMethod = "RANDOM";
			
		if ((round > 2) && (player.getMoney() < 600))
			return false;
		else 
			tileToBuy = pickTile(player, map, tilePickMethod);
		
		if (tileToBuy == null)
			return false;
		
		return player.purchaseTile(tileToBuy, round);
	}

	private static Tile pickTile(Player player, MULEMap map, String type) {
		if (type.equals("RANDOM")){
			return pickTileRandom(player, map, type);
		}
		return null;
	}

	private static Tile pickTileRandom(Player player, MULEMap map, String type) {
		Random rand = new Random();
		ArrayList<Tile> tileList = map.getTiles();
		ArrayList<Tile> availableTiles = new ArrayList<Tile>();
		
		for (Tile t : tileList)
			if ((!t.getType().equals("Town")) && (t.isVacant()))
				availableTiles.add(t);
			
		return availableTiles.get(rand.nextInt(availableTiles.size()));
	}
}
