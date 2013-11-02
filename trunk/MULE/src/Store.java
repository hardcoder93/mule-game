
/**
 * The Store class is the store in the M.U.L.E. game. It sells food, 
 * energy, smithore, crystite, and mules. The starting quantities and 
 * prices of these goods are determined by the game difficulty.
 * 
 * @author John Certusi (jcertusi3)
 */
public class Store {
	Goods food, energy, smithore, crystite, mules;		//goods that can be sold
	
	/**
	 * Constructor for the Store. Game difficulty determines starting 
	 * prices and quantities
	 * @param difficulty the game difficulty setting 
	 */
	public Store(String difficulty){
		switch (difficulty){
		case "Beginner":					//beginner difficulty
			food = new Goods(30, 16);
			energy = new Goods(25, 16);
			smithore = new Goods(50, 0);
			crystite = new Goods(100, 0);
			mules = new Goods(100, 25);
			break;
		default:							//all other difficulties
			food = new Goods(30, 8);
			energy = new Goods(25, 8);
			smithore = new Goods(50, 8);
			crystite =  new Goods(100, 0);
			mules = new Goods(100, 14);
		}
	}
	
	/**
	 * sellGoods sells some quantity of a good. This simply reduces the 
	 * quantity. It does not add it to the player who buys it
	 * @param type		the type of good to be sold
	 * @param quantity	the number of goods to be sold
	 */
	public void sellGoods(String type, int quantity){
		switch (type){
		case "Food":
			food.sellQuantity(quantity);
			break;
		case "Energy":
			energy.sellQuantity(quantity);
			break;
		case "Smithore":
			smithore.sellQuantity(quantity);
			break;
		case "Crystite":
			crystite.sellQuantity(quantity);
			break;
		case "Mules":
			mules.sellQuantity(quantity);
			break;
		}
	}
	
	public int getCurrentPrice(String type){
		switch (type){
		case "Food":
			return food.getCurrentPrice();
		case "Energy":
			return energy.getCurrentPrice();
		case "Smithore":
			return smithore.getCurrentPrice();
		case "Crystite":
			return crystite.getCurrentPrice();
		case "Mules":
			return mules.getCurrentPrice();
		}
		return -1;
	}
	
	
	/**
	 * The Goods class is a container for a specific type of good.
	 * It is used to hold the current number of the good in the store 
	 * as well as the current price.
	 * @author root
	 *
	 */
	private class Goods {
		private int currentPrice;
		private int quantity;
		
		private Goods(int currentPrice, int quantity){
			this.currentPrice = 0;
			this.quantity = 0;
		}
		
		public int getCurrentPrice() {
			return currentPrice;
		}

		public void setCurrentPrice(int price){
			this.currentPrice = price;
		}
		
		private void sellQuantity(int quantity){
			this.quantity = quantity;
		}
	}
}