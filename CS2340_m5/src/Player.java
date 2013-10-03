/**
 * 
 * @author Yuna Lee (ylee385)
 *
 */


public class Player {
	private String name;
	private String level;
	private String race;
	private String color;
	private int money;
	private int food;
	private int energy;
	private int ore;

	public Player(String name, String level, String race, String color){
		this.name = name;
		this.level = level;
		this.race = race;
		this.color = color;
		this.ore = 0;
		setMoney();
		setResources();
	}

	public void setMoney() {
		switch (getRace()) {
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

	public void setResources() {
		switch (getLevel()) {
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
	

}
