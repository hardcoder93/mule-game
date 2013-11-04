import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * 
 * @author Yuna Lee (ylee385), Chris Jenkins (cjenkins36)
 *
 */


public class Player implements Drawable{
	//Constants
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 50;
	
	public static final int NO_MULE = 0;
	public static final int FOOD_MULE = 1;
	public static final int ENERGY_MULE = 2;
	public static final int ORE_MULE = 3;
	
	//Instance Data
	private String name;
	private String level;
	private String race;
	private String color;
	private int money;
	private int food;
	private int energy;
	private int ore;
	private int crystite;
	private Image pImage;
	private Image mImage = new ImageIcon("IMAGES/mulePlaceholder.png").getImage();
	private int xCoord = 500;
	private int yCoord = 250;
	private int score;
	private ArrayList<Tile> ownedTiles;
	private int mule;
	private int muleX = xCoord;
	private int muleY = yCoord;

	public Player(String name, String level, String race, String color, Store store){
		this.name = name;
		this.level = level;
		this.race = race;
		this.color = color;
		this.ore = 0;
		this.ownedTiles = new ArrayList<Tile>();
		mule = NO_MULE;
		setMoney();
		setResources();
		setImage();
		calculateScore(store);
	}
	

	public void setMoney() {
		switch (race) {
		case "Flapper": 
			money = 1600;
			break;
		case "Bonzoid":
		case "Ugaite":
		case "Buzzite": 
			money = 1000;
			break;
		case "Human":
			money = 600;
			break;
		}
	}
	public void addMoney(int money) {
		this.money += money;
		
	}
	public void setResources() {
		switch (level) {
		case "Beginner":
			food = 8;
			energy = 4;
			break;
		case "Standard":
			food = 4;
			energy = 2;
			break;
		case "Tournament": 
			food = 4;
			energy = 2;
			break;
		}
	}
	
	/**
	 * Sets the player's image based on the player's race and color.
	 */
	public void setImage(){
		pImage = new ImageIcon("IMAGES/"+race+"_"+color.toLowerCase()+".png").getImage();
	}
	
	/**
	 * Gets the player's image.
	 * 
	 * @return The image associated with the player.
	 */
	public Image getImage(){
		return pImage;
	}
	
	public String getName(){
		return name;
	}
	public String getLevel(){
		return level;
	}
	public String getRace(){
		return race;
	}
	public String getColor(){
		return color;
	}
	public int getMoney(){
		return money;
	}
	public int getFood(){
		return food;
	}
	public int getEnergy(){
		return energy;
	}
	public int getOre(){
		return ore;
	}
	
	/**
	 * Gets the current x-position of the player.
	 * 
	 * @return Current x-position.
	 */
	public int getX(){
		return xCoord;
	}
	
	/**
	 * Gets the current y-position of the player.
	 * 
	 * @return Current y-position.
	 */
	public int getY(){
		return yCoord;
	}
	
	public Point getCenterPoint(){
		return (new Point(xCoord+PLAYER_WIDTH/2, yCoord+PLAYER_HEIGHT/2));
	}
	
	/**
	 * Draws the player image at the current location with the set width and 
	 * height.
	 */
	public void draw(Graphics g){
		if(hasMule())
			g.drawImage(mImage, muleX, muleY, PLAYER_WIDTH, PLAYER_HEIGHT, null, null);
		g.drawImage(pImage, xCoord, yCoord, PLAYER_WIDTH, PLAYER_HEIGHT, null, null);
	}
	
	/**
	 * Sets the player's location to the input x-y coordinates.
	 * 
	 * @param x The new x coordinate.
	 * @param y The new y coordinate.
	 */
	public void setLocation(int x, int y){
		xCoord = x;
		yCoord = y;
	}
	
	/**
	 * Set's the player's location to the input point.
	 * 
	 * @param p The new point at which to put the player.
	 */
	public void setLocation(Point p){
		setLocation(p.x, p.y);
	}
	
	/**
	 * Moves the player to a new location based on some input distance and 
	 * speed.
	 * 
	 * @param speed Desired speed by which to move the player.
	 * @param distX Base distance to move the player in the x-direction.
	 * @param distY Base distance to move the player in the y-direction.
	 */
	public void move(int speed, int distX, int distY){
		int totX = speed*distX;
		int totY = speed*distY;
		xCoord+=totX;
		yCoord+=totY;
		//In the following lines, "tot/abs(tot)" is used to get the sign.
		if(totX!=0)
			muleX = xCoord-PLAYER_WIDTH*(totX/Math.abs(totX));
		else
			muleX = xCoord;
		if(totY!=0)
			muleY = yCoord-PLAYER_HEIGHT*(totY/Math.abs(totY));
		else
			muleY = yCoord;
	}

	public void subtractMoney(int i) {
		money -= i;
	}
	
	/**
	 * Purchases a tile on the map and updates the player and the purchased
	 * tile. If the round < 2 the tile is free, if not it costs 300
	 * @param tile	tile to be purchased
	 * @param round	current round
	 * @return true if player has enough money to buy the tile; false if not
	 */
	public boolean purchaseTile(Tile tile, int round){
		if (money >= 300 || round < 3){
			tile.setOwner(this);
			ownedTiles.add(tile);
			money = round < 2 ? money : money - 300;
			return true;
		}
		return false;
	}
	
	/**
	 * calculateScore sets the score based on:
	 * 		money:	$1 = 1 point
	 * 		Land:	1 plot = 500 + outfit price;
	 * 		Goods:	1 mule = 35 points
	 * 				food, energy, smithore, crystite = current price
	 * @param store is used to determine the current price
	 */
	public void calculateScore(Store store){
		score =  food * store.getCurrentPrice("Food") +
				energy * store.getCurrentPrice("Energy") +
				ore * store.getCurrentPrice("Smithore") +
				crystite * store.getCurrentPrice("Crystite");
		for (int i = 0; i < ownedTiles.size(); i++)
			score += ownedTiles.get(i).getValue();
	}
	
	public int getScore(){
		return score;
	}

	public void resetPosition() {
		xCoord = 500;
		yCoord = 250;
	}

	/**
	 * This method returns the int value representing the mule that the player 
	 * currently has (not placed on property). If the player currently has no
	 * unplaced mule, the method returns 0; otherwise it returns 1, 2, or 3 for
	 * food, energy, or ore mule, respectively.
	 * 
	 * @return 0 for no mule or 1, 2, or 3 for food, energy or ore mule.
	 */
	public int getMule() {
		return mule;
	}
	
	/**
	 * This method returns true if the player currently has any of the three
	 * types of mule. If not, returns false.
	 * 
	 * @return True if player has an unplaced mule, false if not.
	 */
	public boolean hasMule(){
		return (getMule()!=NO_MULE);
	}


	public int getGoods(String selection) {
		switch (selection){
		case "Food":
			return food;
		case "Smithore":
			return ore;
		case "Energy":
			return energy;
		}
		return -1;
	}
	
	/**
	 * Sets the mule variable representing the mule that is currently following
	 * the player. Input must be one of the mule constants defined in this class.
	 * 
	 * @param mule An int value representing one of the four mule constants.
	 */
	public void setMule(int mule){
		if(mule==NO_MULE || mule==FOOD_MULE || mule==ENERGY_MULE || mule==ORE_MULE){		
			this.mule = mule;
			muleX = xCoord;
			muleY = yCoord;
		}
	}

	public void purchaseGoods(String type, String quantity, int currentPrice) {
		if (!type.equals("Mules"))
			money -= Integer.parseInt(quantity) * currentPrice;
		else 
			money -= currentPrice;
		if (type.equals("Food"))
			food += Integer.parseInt(quantity);
		else if (type.equals("Smithore"))
			ore += Integer.parseInt(quantity);
		else if (type.equals("Energy"))
			energy += Integer.parseInt(quantity);
		else if (type.equals("Mules")){
			if (quantity.equals("Food Mule"))
				mule = FOOD_MULE;
			else if (quantity.equals("Energy Mule"))
				mule = ENERGY_MULE;
			else if (quantity.equals("Smithore Mule"))
				mule = ORE_MULE;
		}
	}
	
	public void sellGoods(String type, int quantity, int currentPrice){
		money += quantity * currentPrice;
		if (type.equals("Food"))
			food -= quantity;
		else if (type.equals("Smithore"))
			ore -= quantity;
		else if (type.equals("Energy"))
			energy -= quantity;
		else if (type.equals("Mules"));
			mule = NO_MULE;
	}

	
	
	public boolean ownsTile(Tile tile){
		return (ownedTiles.contains(tile));
	}
}







