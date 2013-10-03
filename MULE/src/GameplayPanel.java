import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * 
 * @author John Certusi (jcertusi3)
 *
 */
public class GameplayPanel extends JPanel {

	JTextArea mapTextArea = new JTextArea();
	JTextArea playerTextArea = new JTextArea();

	/**
	 * Create the panel.
	 */
	public GameplayPanel() {
		setLayout(null);
		
		mapTextArea.setTabSize(4);
		mapTextArea.setEditable(false);
		mapTextArea.setBounds(12, 193, 426, 95);
		add(mapTextArea);
		
		playerTextArea.setEditable(false);
		playerTextArea.setBounds(12, 12, 426, 139);
		add(playerTextArea);
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
		
		playerTextArea.setText(playerInfo);
		mapTextArea.setText(mapInfo);
	}
}
