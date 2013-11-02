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
	private JComboBox<String> storeResourcesBox;
	private JComboBox<String> buyOrSellBox;
	private JComboBox<Integer> storeQuantityBox;
	private final String[] resourceOptions = {"Food", "Energy", "Smithore", "Mules"};
	private String buildingDisplayed;
	private JLabel buildingLabel;
	private JTextArea storeInventoryLabels[];
	private final Point menuPos = new Point(150,25);
	private final Dimension menuSize = new Dimension(600,250);
	private final int menuBorderSize = 8;
	private JLabel todaysDeals;


	
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
        
        buildingLabel = new JLabel();
        buildingLabel.setBounds(menuPos.x, menuPos.y + 5, menuSize.width, menuSize.height);
        buildingLabel.setHorizontalAlignment(JLabel.CENTER);
        buildingLabel.setVerticalAlignment(JLabel.TOP);
        buildingLabel.setForeground(Color.YELLOW);
    	buildingLabel.setFont(new Font("Narkisim", Font.BOLD, 20));

        nextScreenButton = new JButton("");
        nextScreenButton.setFont(new Font("Narkisim", Font.BOLD, 13));
        nextScreenButton.setBounds(783, 571, 117, 29);
        nextScreenButton.setOpaque(false);
        nextScreenButton.setContentAreaFilled(false);
        add(nextScreenButton);
        
        initStoreMenu();
    }
    
    
    private void initStoreMenu(){
    	buyOrSellBox = new JComboBox<String>();
    	buyOrSellBox.addItem("Buy");
    	buyOrSellBox.addItem("Sell");
    	buyOrSellBox.setBounds(menuPos.x + menuSize.width/2 + 50, menuPos.y + 80, 124, 27);
		buyOrSellBox.addActionListener(new StoreMenuListener());
    	storeResourcesBox = new JComboBox<String>(resourceOptions);
		storeResourcesBox.setBounds(menuPos.x + menuSize.width/2 + 50, menuPos.y + 110, 124, 27);
		storeResourcesBox.addActionListener(new StoreMenuListener());
    	storeQuantityBox = new JComboBox<Integer>();
		storeQuantityBox.setBounds(menuPos.x + menuSize.width/2 + 50, menuPos.y + 140, 124, 27);
		todaysDeals = new JLabel("Today's Deals");
		todaysDeals.setBounds(menuPos.x, menuPos.y + 60, menuSize.width / 2 - 40, 20);
		todaysDeals.setHorizontalAlignment(JLabel.CENTER);
		todaysDeals.setForeground(Color.WHITE);
    	todaysDeals.setFont(new Font("Narkisim", Font.BOLD, 15));
        storeInventoryLabels = new JTextArea[resourceOptions.length];
        for (int i = 0; i < storeInventoryLabels.length; i++){
        	storeInventoryLabels[i] = new JTextArea();
        	storeInventoryLabels[i].setBounds(menuPos.x + 20, menuPos.y+90+i*30, 
        			menuSize.width / 2 - 5, 20); 
        	storeInventoryLabels[i].setForeground(Color.WHITE);
        	storeInventoryLabels[i].setFont(new Font("Narkisim", Font.BOLD, 12));
        	storeInventoryLabels[i].setEditable(false);
        	storeInventoryLabels[i].setOpaque(false);
        	storeInventoryLabels[i].setTabSize(7);
        }
    }
    
    public void updateInventoryLabels(){
    	int numLeft;
    	int price;
    	String numLeftString;
    	for (int i = 0; i < storeInventoryLabels.length; i++){
    		price = store.getCurrentPrice(resourceOptions[i]);
    		numLeft = store.getQuantity(resourceOptions[i]);
    		numLeftString = numLeft > 0 ? numLeft + " left!" : "SOLD OUT";
    		numLeftString = price >= 100 ? "    " + numLeftString : "      " + numLeftString;
    		storeInventoryLabels[i].setText(resourceOptions[i] + ":\t$" + price + numLeftString);
    	}
    }
    
    public void displayStoreMenu(){
    	buildingDisplayed = "Store";
    	buildingLabel.setText("Welcome to the Store!");
    	storeResourcesBox.setSelectedIndex(0);
    	updateInventoryLabels();
    	for (int i = 0; i < storeInventoryLabels.length; i++)
    		add(storeInventoryLabels[i]);
    	add(buyOrSellBox);
    	add(storeResourcesBox);
    	add(storeQuantityBox);
    	add(buildingLabel);
    	add(todaysDeals);
    }
    
    public void removeStoreMenu(){
    	buildingDisplayed = "none";
    	for (int i = 0; i < storeInventoryLabels.length; i++)
    		remove(storeInventoryLabels[i]);
    	remove(buyOrSellBox);
    	remove(storeResourcesBox);
    	remove(storeQuantityBox);
    	remove(buildingLabel);
    	remove(todaysDeals);
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
    					drawBuildingMenu(g);
    			}
    		}
    	}
    }
    
    private void drawBuildingMenu(Graphics g) {
    	g.setColor(Color.BLUE);
    	g.fillRect(menuPos.x, menuPos.y, menuSize.width, menuSize.height);
    	g.setColor(Color.YELLOW);
    	for (int i = 1; i <= menuBorderSize; i++)
    		g.drawRect(menuPos.x-i, menuPos.y-i, menuSize.width+2*i-1, menuSize.height+2*i-1);
    }

	private class StoreMenuListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			int quantity;
	        String selection = storeResourcesBox.getSelectedItem().toString();
	        String buyOrSell = buyOrSellBox.getSelectedItem().toString();
	        if (buyOrSell.equals("Buy")){
	        	quantity = store.getQuantity(selection);
		        quantity = (selection.equals("Mules") && quantity > 1) ? 1 : quantity;
	        } else 
	        	if (selection.equals("Mules"))
	        		quantity = activePlayer.hasMule() ? 1 : 0;
	        	else
	        		quantity = activePlayer.getGoods(selection);	      
	        for (int i = storeQuantityBox.getItemCount() - 1; i > quantity; i--)
	        	storeQuantityBox.removeItemAt(i);
	        for (int i = storeQuantityBox.getItemCount(); i <= quantity; i++){	        		
	        	storeQuantityBox.addItem(i);	
	        }
		}    	
    }
}
