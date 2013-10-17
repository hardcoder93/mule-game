import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author Yuna Lee (ylee385)
 *
 */


public class Player implements Drawable{
	private final int PLAYER_WIDTH = 50;
	private final int PLAYER_HEIGHT = 50;
	
	private String name;
	private String level;
	private String race;
	private String color;
	private int money;
	private int food;
	private int energy;
	private int ore;
	private Image pImage;
	private int xCoord = 500;
	private int yCoord = 250;

	public Player(String name, String level, String race, String color){
		this.name = name;
		this.level = level;
		this.race = race;
		this.color = color;
		this.ore = 0;
		setMoney();
		setResources();
		setImage();
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
	
	public void setImage(){
		pImage = new ImageIcon("IMAGES/"+race+"_"+color.toLowerCase()+".png").getImage();
	}
	
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
	
	public int getX(){
		return xCoord;
	}
	
	public int getY(){
		return yCoord;
	}
	
	public void draw(Graphics g){
		g.drawImage(pImage, xCoord, yCoord, PLAYER_WIDTH, PLAYER_HEIGHT, null, null);
	}
	
	
	public void setLocation(int x, int y){
		xCoord = x;
		yCoord = y;
	}
	public void move(int speed, int distX, int distY){
		xCoord+=distX;
		yCoord+=distY;
	}
}
