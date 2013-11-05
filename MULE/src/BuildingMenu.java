import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class BuildingMenu implements Drawable{
	
	private final int menuBorderSize = 8;
	
	int xPos, yPos, width, height;
	String type;
	JPanel parentPanel;
	JLabel welcomeMessage, menuLabel, errorMessage;
	JLabel[] comboBoxLabels;
	JTextArea[] menuItems;
	JRadioButton[] muleRadioButtons;
	JButton menuButton;
	ArrayList<JComboBox<String>> comboBoxes;
	String[][] comboBoxEntries;
	Store store;
	Player activePlayer;

	public BuildingMenu(int x, int y, int width, int height, String type, Store store, JPanel parent){
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.store = store;
		this.parentPanel = parent;
		
        welcomeMessage = new JLabel("Welcome to the " + type + "!");
        welcomeMessage.setBounds(xPos, yPos + 5, width, 30);
        welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
        welcomeMessage.setForeground(Color.YELLOW);
        welcomeMessage.setFont(new Font("Narkisim", Font.BOLD, 20));
		
		if (type.equals("Store"))
			initStoreMenu();
	}
	
	private void initStoreMenu() {
    	
		menuLabel = new JLabel("Todays Deals");
		menuLabel.setBounds(xPos, yPos + 40, width / 2 - 40, 20);
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		menuLabel.setForeground(Color.WHITE);
		menuLabel.setFont(new Font("Narkisim", Font.BOLD, 15));
		
		errorMessage = new JLabel("");
		errorMessage.setBounds(xPos, yPos + height - 30, width, 20);
		errorMessage.setHorizontalAlignment(JLabel.CENTER);
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("Narkisim", Font.BOLD, 12));

		
		JComboBox<String> tmp;
		comboBoxes = new ArrayList<JComboBox<String>>();
		comboBoxEntries = new String[3][];
		comboBoxEntries[0] = new String[]{"Buy", "Sell"};
		comboBoxEntries[1] = new String[]{"Food", "Energy", "Smithore", "Mules"};
		comboBoxEntries[2] = new String[]{"Food Mule", "Energy Mule", "Smithore Mule"};
		for (int i = 0; i < 3; i++){
			if (i < 2){
				tmp = new JComboBox<String>(comboBoxEntries[i]);
				tmp.addActionListener(new StoreMenuListener());
				tmp.setFocusable(false);
			} else {
				tmp = new JComboBox<String>();
				tmp.addItem("0");
				tmp.setFocusable(false);
			}
			tmp.setBounds(xPos + width/2 + 20, yPos + 60 + 30*i, 144, 27);
			comboBoxes.add(tmp);
		}
	    	
	    comboBoxLabels = new JLabel[3];
	    for (int i = 0; i < comboBoxLabels.length; i++){
	    	comboBoxLabels[i] = new JLabel();
	    	comboBoxLabels[i].setBounds(xPos + width/2 + 170, yPos + 60 + 30*i, 100, 27);
	    	comboBoxLabels[i].setForeground(Color.white);
	        comboBoxLabels[i].setFont(new Font("Narkisim", Font.BOLD, 10));
	    }
	    comboBoxLabels[0].setText("Buy or Sell?");
	    comboBoxLabels[1].setText("Which Resource?");
	    comboBoxLabels[2].setText("How Many?");
	    
	    menuItems = new JTextArea[comboBoxEntries[1].length + 4];
	    for (int i = 0; i < menuItems.length; i++){
	        menuItems[i] = new JTextArea();
	       	menuItems[i].setForeground(Color.WHITE);
	       	menuItems[i].setFont(new Font("Narkisim", Font.BOLD, 12));
	       	menuItems[i].setEditable(false);
	       	menuItems[i].setOpaque(false);
	       	if (i < comboBoxEntries[1].length){
		       	menuItems[i].setBounds(xPos + 40, yPos + 63 + i * 20, width / 2 - 5, 20); 
	       		menuItems[i].setTabSize(7);
	       	}else{
		       	menuItems[i].setBounds(xPos + 40, yPos + 78 + i * 20, width / 2 - 5, 20); 
	       		menuItems[i].setTabSize(4);
	       	}
	    }
	    menuItems[4].setText("Mule Outfitting:");
	    menuItems[5].setText("    Food Mule\t\t$25");
	    menuItems[6].setText("    Energy Mule\t$50");
	    menuItems[7].setText("    Smithore Mule\t$75");
	        
	    menuButton = new JButton("Complete Transaction");
	    menuButton.setBounds(xPos + width/2 + 50, yPos + 180, 200, 27);
	    menuButton.setFocusable(false);
	    menuButton.addActionListener(new StoreMenuListener());
	}
	
    public void updateInventoryLabels(){
    	int numLeft;
    	int price;
    	String numLeftString;
    	for (int i = 0; i < menuItems.length - 4; i++){
    		price = store.getCurrentPrice(comboBoxEntries[1][i]);
    		numLeft = store.getQuantity(comboBoxEntries[1][i]);
    		numLeftString = numLeft > 0 ? numLeft + " left!" : "SOLD OUT";
    		numLeftString = price >= 100 ? "    " + numLeftString : "      " + numLeftString;
    		menuItems[i].setText(comboBoxEntries[1][i] + ":\t$" + price + numLeftString);
    	}
    }
    
    public void displayStoreMenu(Player activePlayer, Store store){
    	this.store = store;
    	this.activePlayer = activePlayer;
    	comboBoxes.get(0).setSelectedIndex(0);
    	comboBoxes.get(1).setSelectedIndex(0);
    	comboBoxes.get(2).setSelectedIndex(0);
    	updateInventoryLabels();
    	for (int i = 0; i < menuItems.length; i++)
    		parentPanel.add(menuItems[i]);
    	for (int i = 0; i < comboBoxLabels.length; i++)
    		parentPanel.add(comboBoxLabels[i]);
    	for (int i = 0; i < comboBoxes.size(); i++)
    		parentPanel.add(comboBoxes.get(i));
    	parentPanel.add(welcomeMessage);
    	parentPanel.add(menuLabel);
    	parentPanel.add(errorMessage);
    	parentPanel.add(menuButton);
    }
    
    public void removeStoreMenu(){
    	for (int i = 0; i < menuItems.length; i++)
    		parentPanel.remove(menuItems[i]);
    	for (int i = 0; i < comboBoxLabels.length; i++)
    		parentPanel.remove(comboBoxLabels[i]);
    	for (int i = 0; i < comboBoxes.size(); i++)
    		parentPanel.remove(comboBoxes.get(i));
    	parentPanel.remove(welcomeMessage);
    	parentPanel.remove(menuLabel);
    	parentPanel.remove(errorMessage);
    	parentPanel.remove(menuButton);
    }

    public ArrayList<Object> getEntries(){
    	ArrayList<Object> entries = new ArrayList<Object>();
    	for (int i = 0; i < comboBoxes.size(); i++)
    		entries.add(comboBoxes.get(i).getSelectedItem());
    	return entries;
    }

	@Override
	public void draw(Graphics g) {
    	g.setColor(Color.BLUE);
    	g.fillRect(xPos, yPos, width, height);
    	g.setColor(Color.YELLOW);
    	g.fillRect(xPos + width/2 - 3, yPos + 60, 6, height - 105);
    	for (int i = 1; i <= menuBorderSize; i++)
    		g.drawRect(xPos-i, yPos-i, width+2*i-1, height+2*i-1);	
    }
	
	private class StoreMenuListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			int quantity;
	        String selection = comboBoxes.get(1).getSelectedItem().toString();
	        String buyOrSell = comboBoxes.get(0).getSelectedItem().toString();
	        parentPanel.remove(errorMessage);
	        
	        if (buyOrSell.equals("Buy")){
	        	quantity = store.getQuantity(selection);
	        	if (selection.equals("Mules") && quantity > 0)
	        		setMuleBox();
	        	else
	        		setQuantityBox(quantity);
	        } else {
	        	if (selection.equals("Mules"))
	        		quantity = activePlayer.hasMule() ? 1 : 0;
	        	else 
	        		quantity = activePlayer.getGoods(selection);
	        	setQuantityBox(quantity);
	        }
	        updateInventoryLabels();
		}
	}
	
	private void setMuleBox(){
		comboBoxes.get(2).setModel(new DefaultComboBoxModel<String>(comboBoxEntries[2]));
		comboBoxLabels[2].setText("Which Type?");
		if (activePlayer.hasMule()){
			menuButton.setEnabled(false);
		} else {
			menuButton.setEnabled(true);
		}
	}
	
	private void setQuantityBox(int quantity){
		comboBoxLabels[2].setText("How Many?");
		comboBoxes.get(2).setModel(new DefaultComboBoxModel<String>());
        for (int i = comboBoxes.get(2).getItemCount(); i <= quantity; i++)  		
        	comboBoxes.get(2).addItem("" + i);	
        menuButton.setEnabled(true);
	}
	
	public JButton getButton() {
		return menuButton;
	}

	public void setErrorMessage(int cost) {
		errorMessage.setText("Not enough money! You need $" + cost + ".");
		parentPanel.add(errorMessage);		
	}
	
}