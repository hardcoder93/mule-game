import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.text.Position;

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
	//Game play objects
	private MULEMap gameMap;			//map of game
	private Player[] playerList;		//list of players (playerList.length = # of players)
	private Player activePlayer;
	private Store store;
	private JLabel[] moneyLabels = new JLabel[4];
	private JLabel label;
	private String buildingDisplayed;
	
	private BuildingMenu storeMenu;

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

        storeMenu = new BuildingMenu(150,25,600,250,"Store",store,this);
        
        nextScreenButton = new JButton("");
        nextScreenButton.setFont(new Font("Narkisim", Font.BOLD, 13));
        nextScreenButton.setBounds(783, 571, 117, 29);
        nextScreenButton.setOpaque(false);
        nextScreenButton.setContentAreaFilled(false);
        add(nextScreenButton);
        
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
        	JLabel playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	JLabel playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	JLabel playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	JLabel playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	JLabel playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	JLabel playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	
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
        	JLabel playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	JLabel playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	JLabel playerEnergy_2 = new JLabel(""+ playerList[2].getEnergy()) ;
        	JLabel playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	JLabel playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	JLabel playerFood_2 = new JLabel(""+ playerList[2].getFood()) ;
        	JLabel playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	JLabel playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	JLabel playerOre_2 = new JLabel(""+ playerList[2].getOre()) ;
        	
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
        	
        	JLabel playerEnergy = new JLabel(""+ playerList[0].getEnergy()) ; 
        	JLabel playerEnergy_1 = new JLabel(""+ playerList[1].getEnergy()) ;
        	JLabel playerEnergy_2 = new JLabel(""+ playerList[2].getEnergy()) ;
        	JLabel playerEnergy_3 = new JLabel(""+ playerList[3].getEnergy()) ;
        	
        	JLabel playerFood = new JLabel(""+ playerList[0].getFood()) ; 
        	JLabel playerFood_1 = new JLabel(""+ playerList[1].getFood()) ;
        	JLabel playerFood_2 = new JLabel(""+ playerList[2].getFood()) ;
        	JLabel playerFood_3 = new JLabel(""+ playerList[3].getFood()) ;
        	
        	JLabel playerOre = new JLabel(""+ playerList[0].getOre()) ; 
        	JLabel playerOre_1 = new JLabel(""+ playerList[1].getOre()) ;
        	JLabel playerOre_2 = new JLabel(""+ playerList[2].getOre()) ;
        	JLabel playerOre_3 = new JLabel(""+ playerList[3].getOre()) ;
    		
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
    	for (int i = 0; i < playerList.length; i++)
    		moneyLabels[i].setText("" + playerList[i].getMoney());
    }
    
    
    /**
     * Overrides the panel's paintComponent Method
     * This method should not be called directly. Instead use repaint()
     */
    public void paintComponent(Graphics g) {
    	if (panelState != null && !panelState.equals(GameState.getState())){
    		super.paintComponent(g);
    		panelState = GameState.getState();
    	}
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
    				if (!buildingDisplayed.equals("none"))
    					storeMenu.draw(g);
    			}
    		}
    	}
    }

	public void displayStoreMenu() {
		buildingDisplayed = "Store";
		storeMenu.displayStoreMenu(activePlayer, store);
	}

	public void removeStoreMenu() {
		buildingDisplayed = "none";
		storeMenu.removeStoreMenu();
	}

	public void setStore(Store store2) {
		this.store = store2;
	}

	public ArrayList<Object> getMenuEntries() {
		return storeMenu.getEntries();
	}
	
	public JButton getMenuButton(){
		return storeMenu.getButton();
	}
}
    
