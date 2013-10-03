import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;



public class GameplayPanel extends JPanel {

	JTextArea textArea_1 = new JTextArea();
	JTextArea textArea = new JTextArea();



	/**
	 * Create the panel.
	 */
	public GameplayPanel() {
		setLayout(null);
		textArea_1.setTabSize(4);
		
		textArea_1.setEditable(false);
		textArea_1.setBounds(12, 193, 426, 95);
		add(textArea_1);
		
		textArea.setEditable(false);
		textArea.setBounds(12, 12, 426, 139);
		add(textArea);
	}
	
	public void setPanel(Player[] playerArray, MULEMap myMap){
		String playerInfo = new String();
		String mapInfo = new String();
		ArrayList<Tile> tileInfo = myMap.getTiles();
		Tile tmpTile;

		
		for (int i = 0; i < playerArray.length; i++){
			playerInfo = playerInfo.concat("Player: ".concat(Integer.toString(i)));
			playerInfo = playerInfo.concat("\tName: ".concat(playerArray[i].getName()));
			playerInfo = playerInfo.concat("\tDifficulty: ".concat(playerArray[i].getLevel()));
			playerInfo = playerInfo.concat("\tRace: ".concat(playerArray[i].getRace()));
			playerInfo = playerInfo.concat("\tColor: ".concat(playerArray[i].getColor()));
			playerInfo = playerInfo.concat("\n\n");
		}
		
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 9; j++){
				tmpTile = tileInfo.get(i*9 + j);
				mapInfo = mapInfo.concat(tmpTile.getType().concat("\t"));
			}
			mapInfo = mapInfo.concat("\n");
		}	
		
		textArea.setText(playerInfo);
		textArea_1.setText(mapInfo);
	}
}
