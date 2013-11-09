import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Jpanel object for M.U.L.E. game that displays everything that goes on during the 
 * normal game play. 
 * 		1) 	must use setMapAndPlayers(MULEMap, Players[]) before displaying anything
 * 		2) 	must use repaint() directly after a Tile is altered to change update the 
 * 			panel
 * 
 * @author John Certusi (jcertusi3)
 *
 */
@SuppressWarnings("serial")
public class GameplayPanel extends JPanel {
	private final String STORE = "STORE";
	private final String PUB = "PUB";
	private final String NONE = "NONE";
	
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)
	private Player activePlayer;
	private Store store;
	private JLabel[] moneyLabels = new JLabel[4];
	private JLabel[] foodLabels = new JLabel[4];
	private JLabel[] energyLabels = new JLabel[4];
	private JLabel[] oreLabels = new JLabel[4];
	private JLabel label;
	private JLabel label2;
	private String buildingDisplayed;
	
	private StoreMenu storeMenu;
	private PubMenu pubMenu;

	//Screen States
	private String panelState;
	
	JButton nextScreenButton;
	
    /**
     * Makes a new gameplayPanel
     */
    public GameplayPanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        panelState = "init";
        buildingDisplayed = "none";

        storeMenu = new StoreMenu(Color.BLUE,Color.YELLOW,this);
        pubMenu = new PubMenu(Color.GREEN, Color.BLUE, this);
        
        nextScreenButton = new JButton("");
        nextScreenButton.setFont(new Font("Narkisim", Font.BOLD, 13));
        nextScreenButton.setBounds(783, 571, 117, 29);
        nextScreenButton.setOpaque(false);
        nextScreenButton.setContentAreaFilled(false);
        add(nextScreenButton);
        
        label = new JLabel();
        label2 = new JLabel();
        label2.setBounds(250,0,400,30);
        label2.setFont(new Font("Narkisim", Font.BOLD, 16));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setForeground(Color.yellow);
        label2.setBackground(Color.black);
        label2.setOpaque(true);
        add(label2);
        
    }
    
    public void setLandGrantLabel(int round){
    	if (round < 0)
    		label2.setText("");
    	else if (round < 3)
    		label2.setText("Round " + round + ": Land is free this round!");
    	else
    		label2.setText("Round " + round + ":Land costs $300 this round!");
    }
    
    public void addLandGrantLabel(boolean add){
    	if (add)
    		//add(label2);
    		label2.setVisible(true);
    	else
    		label2.setVisible(false);
    }
    
  
    
    public void addLabel(JLabel label){
    	label.setBounds(0, 0, 159, 100);
    	add(label);
    }
    
    public void updateLabel(JLabel label){
    	this.label = label;
    }
    
    public JButton getButton(){
    	return nextScreenButton;
    }
    
    public void setButtonText(String text){
    	nextScreenButton.setText(text);
    }
    
    public void resetButton(){
    	nextScreenButton.setText("Pass");
    }
   
	public void setUpScoreboard () {
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("IMAGES/BackGround.png"));
		lblNewLabel.setBounds(741, 500, 159, 100);
		add(lblNewLabel);
		
		JLabel playerMoney = null;
		JLabel playerMoney_1 = null;
		JLabel playerMoney_2 = null;
		JLabel playerMoney_3 = null;

		JLabel playerEnergy = null;
		JLabel playerEnergy_1 = null;
		JLabel playerEnergy_2 = null;
		JLabel playerEnergy_3 = null;
		
		JLabel playerFood = null;
		JLabel playerFood_1 = null;
		JLabel playerFood_2 = null;
		JLabel playerFood_3 = null;
		
		JLabel playerOre = null;
		JLabel playerOre_1 = null;
		JLabel playerOre_2 = null;
		JLabel playerOre_3 = null;
		
		
		
		
		if (playerList.length == 2) {
    		JLabel Name = new JLabel("Name:") ;
    		JLabel Money = new JLabel("Money:") ;
    		JLabel Energy = new JLabel("Energy:") ;
    		JLabel Food = new JLabel("Food:") ;
    		JLabel Ore = new JLabel("Ore:") ;
    		JLabel playerName = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel playerName_1 = new JLabel(""+ playerList[1].getName()) ;
        	playerMoney = new JLabel(""+""+ playerList[0].getMoney()) ; 
        	playerMoney_1 = new JLabel(""+ playerList[1].getMoney()) ;
        	playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	
        	Name.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Money.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Food.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Ore.setFont(new Font("Narkisim", Font.BOLD, 20));
        	
        	Name.setBounds(0, 460, 180, 100);
            add(Name);
            Money.setBounds(0, 480, 180, 100);
            add(Money);
            Energy.setBounds(0, 500, 180, 100);
            add(Energy);
            Food.setBounds(0, 520, 180, 100);
            add(Food);
            Ore.setBounds(0, 540, 180, 100);
            add(Ore);
        	
        	playerName.setBounds(155, 460, 180, 100);
            add(playerName);
            playerName_1.setBounds(305, 460, 180, 100);
            add(playerName_1);
            
            playerMoney.setBounds(155, 480, 180, 100);
            add(playerMoney);
            playerMoney_1.setBounds(305, 480, 180, 100);
            add(playerMoney_1);
            
            playerEnergy.setBounds(155, 500, 180, 100);
            add(playerEnergy);
            playerEnergy_1.setBounds(305, 500, 180, 100);
            add(playerEnergy_1);
            
            playerFood.setBounds(155, 520, 180, 100);
            add(playerFood);
            playerFood_1.setBounds(305, 520, 180, 100);
            add(playerFood_1);
            
            playerOre.setBounds(155, 540, 180, 100);
            add(playerOre);
            playerOre_1.setBounds(305, 540, 180, 100);
            add(playerOre_1);
            
           
    	}else 
    		if (playerList.length == 3) {
    		JLabel Name = new JLabel("Name:") ;
        	JLabel Money = new JLabel("Money:") ;
        	JLabel Energy = new JLabel("Energy:") ;
        	JLabel Food = new JLabel("Food:") ;
        	JLabel Ore = new JLabel("Ore:") ;
    		JLabel playerName = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel playerName_1 = new JLabel(""+ playerList[1].getName()) ;
        	JLabel playerName_2 = new JLabel(""+ playerList[2].getName()) ;
        	playerMoney = new JLabel(""+ playerList[0].getMoney()) ; 
        	playerMoney_1 = new JLabel(""+ playerList[1].getMoney()) ;
        	playerMoney_2 = new JLabel(""+ playerList[2].getMoney()) ;
        	playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	playerEnergy_2 = new JLabel(""+ playerList[2].getEnergy()) ;
        	playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	playerFood_2 = new JLabel(""+ playerList[2].getFood()) ;
        	playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	playerOre_2 = new JLabel(""+ playerList[2].getOre()) ;
        	
        	Name.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Money.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Food.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Ore.setFont(new Font("Narkisim", Font.BOLD, 20));
        	
        	Name.setBounds(0, 460, 180, 100);
            add(Name);
            Money.setBounds(0, 480, 180, 100);
            add(Money);
            Energy.setBounds(0, 500, 180, 100);
            add(Energy);
            Food.setBounds(0, 520, 180, 100);
            add(Food);
            Ore.setBounds(0, 540, 180, 100);
            add(Ore);
        	
        	playerName.setBounds(155, 460, 180, 100);
            add(playerName);
            playerName_1.setBounds(305, 460, 180, 100);
            add(playerName_1);
            playerName_2.setBounds(455, 460, 180, 100);
            add(playerName_2);
            
            playerMoney.setBounds(155, 480, 180, 100);
            add(playerMoney);
            playerMoney_1.setBounds(305, 480, 180, 100);
            add(playerMoney_1);
            playerMoney_2.setBounds(455, 480, 180, 100);
            add(playerMoney_2);
            
            playerEnergy.setBounds(155, 500, 180, 100);
            add(playerEnergy);
            playerEnergy_1.setBounds(305, 500, 180, 100);
            add(playerEnergy_1);
            playerEnergy_2.setBounds(455, 500, 180, 100);
            add(playerEnergy_2);
            
            playerFood.setBounds(155, 520, 180, 100);
            add(playerFood);
            playerFood_1.setBounds(305, 520, 180, 100);
            add(playerFood_1);
            playerFood_2.setBounds(455, 520, 180, 100);
            add(playerFood_2);
            
            playerOre.setBounds(155, 540, 180, 100);
            add(playerOre);
            playerOre_1.setBounds(305, 540, 180, 100);
            add(playerOre_1);
            playerOre_2.setBounds(455, 540, 180, 100);
            add(playerOre_2);
    	}
         
    	else {
    		JLabel Name = new JLabel("Name:") ;
        	JLabel Money = new JLabel("Money:") ;
        	JLabel Energy = new JLabel("Energy:") ;
        	JLabel Food = new JLabel("Food:") ;
        	JLabel Ore = new JLabel("Ore:") ;
    		
    		JLabel playerName = new JLabel(""+ playerList[0].getName()) ; 
        	JLabel playerName_1 = new JLabel(""+ playerList[1].getName()) ;
        	JLabel playerName_2 = new JLabel(""+ playerList[2].getName()) ;
        	JLabel playerName_3 = new JLabel(""+ playerList[3].getName()) ;
        	playerMoney = new JLabel(""+ playerList[0].getMoney()) ; 
        	playerMoney_1 = new JLabel(""+ playerList[1].getMoney()) ;
        	playerMoney_2 = new JLabel(""+ playerList[2].getMoney()) ;
        	playerMoney_3 = new JLabel(""+ playerList[3].getMoney()) ;
        	
        	playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	playerEnergy_2 = new JLabel(""+ playerList[2].getEnergy()) ;
        	playerEnergy_3 = new JLabel(""+ playerList[3].getEnergy()) ;
        	
        	playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	playerFood_2 = new JLabel(""+ playerList[2].getFood()) ;
        	playerFood_3 = new JLabel(""+ playerList[3].getFood()) ;
        	
        	playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	playerOre_2 = new JLabel(""+ playerList[2].getOre()) ;
        	playerOre_3 = new JLabel(""+ playerList[3].getOre()) ;
    		
        	Name.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Money.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Food.setFont(new Font("Narkisim", Font.BOLD, 20));
        	Ore.setFont(new Font("Narkisim", Font.BOLD, 20));
        	
        	Name.setBounds(0, 460, 180, 100);
            add(Name);
            Money.setBounds(0, 480, 180, 100);
            add(Money);
            Energy.setBounds(0, 500, 180, 100);
            add(Energy);
            Food.setBounds(0, 520, 180, 100);
            add(Food);
            Ore.setBounds(0, 540, 180, 100);
            add(Ore);
        	
        	playerName.setBounds(155, 460, 180, 100);
            add(playerName); 
            playerName_1.setBounds(305, 460, 180, 100);
            add(playerName_1);
            playerName_2.setBounds(455, 460, 180, 100);
            add(playerName_2);
            playerName_3.setBounds(610, 460, 180, 100);
            add(playerName_3);
            
            playerMoney.setBounds(155, 480, 180, 100);
            add(playerMoney);
            playerMoney_1.setBounds(305, 480, 180, 100);
            add(playerMoney_1);
            playerMoney_2.setBounds(455, 480, 180, 100);
            add(playerMoney_2);
            playerMoney_3.setBounds(610, 480, 180, 100);
            add(playerMoney_3);
            
            playerEnergy.setBounds(155, 500, 180, 100);
            add(playerEnergy);
            playerEnergy_1.setBounds(305, 500, 180, 100);
            add(playerEnergy_1);
            playerEnergy_2.setBounds(455, 500, 180, 100);
            add(playerEnergy_2);
            playerEnergy_3.setBounds(610, 500, 180, 100);
            add(playerEnergy_3);
            
            playerFood.setBounds(155, 520, 180, 100);
            add(playerFood);
            playerFood_1.setBounds(305, 520, 180, 100);
            add(playerFood_1);
            playerFood_2.setBounds(455, 520, 180, 100);
            add(playerFood_2);
            playerFood_3.setBounds(610, 520, 180, 100);
            add(playerFood_3);
            
            playerOre.setBounds(155, 540, 180, 100);
            add(playerOre);
            playerOre_1.setBounds(305, 540, 180, 100);
            add(playerOre_1);
            playerOre_2.setBounds(455, 540, 180, 100);
            add(playerOre_2);
            playerOre_3.setBounds(610, 540, 180, 100);
            add(playerOre_3);
         
    	}
       
			moneyLabels[0] = playerMoney;
			moneyLabels[1] = playerMoney_1;
			if (playerMoney_2 != null)
				moneyLabels[2] = playerMoney_2;
			if (playerMoney_3 != null)
				moneyLabels[3] = playerMoney_3;
			
			foodLabels[0] = playerFood;
			foodLabels[1] = playerFood_1;
			if (playerMoney_2 != null)
				foodLabels[2] = playerFood_2;
			if (playerMoney_3 != null)
				foodLabels[3] = playerFood_3;
			
			energyLabels[0] = playerEnergy;
			energyLabels[1] = playerEnergy_1;
			if (playerMoney_2 != null)
				energyLabels[2] = playerEnergy_2;
			if (playerMoney_3 != null)
				energyLabels[3] = playerEnergy_3;
			
			oreLabels[0] = playerOre;
			oreLabels[1] = playerOre_1;
			if (playerMoney_2 != null)
				oreLabels[2] = playerOre_2;
			if (playerMoney_3 != null)
				oreLabels[3] = playerOre_3;
		}
         
    
	
	/**
     * Sets the map and player list inside the panel. Needed to display the map
     * and Tile characteristics
     * 
     * @param gameMap		map for the game
     * @param playerList	players in the game
     */
    public void setMapAndPlayers(MULEMap gameMap, Player[] playerList, Store store){
    	this.gameMap = gameMap;
    	this.playerList = playerList;
    	this.store = store;
    	setUpScoreboard();
    }
    
    /**
     * Sets the activePlayer instance variable to the input player object.
     * 
     * @param p The player to set.
     */
    public void setActivePlayer(Player p){
    	activePlayer = p;
    }
    
    public void disableButton(){
    	if (nextScreenButton.isEnabled())
    		nextScreenButton.setEnabled(false);
    }
    
    public void enableButton(){
    	if (!nextScreenButton.isEnabled())
    		nextScreenButton.setEnabled(true);
    }
    
    public void updateScoreboard(){
    	for (int i = 0; i < playerList.length; i++){
    		moneyLabels[i].setText("" + playerList[i].getMoney());
    	}	
    	for (int i = 0; i < playerList.length; i++){
        	energyLabels[i].setText("" + playerList[i].getEnergy());
    	}
    	for (int i = 0; i < playerList.length; i++){
    		foodLabels[i].setText("" + playerList[i].getFood());
    	}
    	for (int i = 0; i < playerList.length; i++){
    		oreLabels[i].setText("" + playerList[i].getOre());
  
    	}
    }
    
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
    	if (this.gameMap != null){
    		if(GameState.getState().equals(GameState.WAITING)){
    			g.setColor(Color.BLACK);
    			g.fillRect(0, 0, 900, 500);
    			g.setColor(Color.WHITE);
    			g.drawString("PAUSED - Press any key to continue", 350, 250);
    		}else{
    			super.paintComponent(g);
    			
    			gameMap.draw(g);
    			if (GameState.playing()){
    				activePlayer.draw(g);
    				if (buildingDisplayed.equals(STORE))
    					storeMenu.draw(g);
    				else if (buildingDisplayed.equals(PUB))
    					pubMenu.draw(g);
    			}
    		}
    	}
    }

    public void displayPub(int winnings){
    	buildingDisplayed = PUB;
    	pubMenu.displayPub(activePlayer, winnings);
    }
    
    public void removeBuilding(){
    	if (buildingDisplayed.equals(STORE))
    		storeMenu.removeStoreMenu();
    	else if (buildingDisplayed.equals(PUB))
    		pubMenu.removePub();
    	buildingDisplayed = NONE;
    }
    
	public void displayStoreMenu() {
		buildingDisplayed = STORE;
		storeMenu.displayStoreMenu(activePlayer, store);
	}

	public void setStore(Store store) {
		this.store = store;
		storeMenu.setStore(store);
	}

	public ArrayList<Object> getMenuEntries() {
		return storeMenu.getEntries();
	}
	
	public JButton getMenuButton(){
		return storeMenu.getButton();
	}




	public void setErrorMessage(int cost) {
		storeMenu.setErrorMessage(cost);		
	}
}
    
