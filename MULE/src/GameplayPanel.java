import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;


/**
 * 
 * @author John Certusi (jcertusi3)
 *
 */
public class GameplayPanel extends JPanel {
	JTextArea playerTextArea1 = new JTextArea();
	JTextArea playerTextArea2 = new JTextArea();
	JTextArea playerTextArea3 = new JTextArea();
	JTextArea playerTextArea4 = new JTextArea();
	JTextArea[] playerText = {playerTextArea1, playerTextArea2, playerTextArea3, playerTextArea4};

	private JTable table;

	/**
	 * Create the panel.
	 */
	public GameplayPanel() {
		setLayout(null);
		
		playerTextArea1.setEditable(false);
		playerTextArea1.setBounds(12, 12, 106, 139);
		add(playerTextArea1);
		
		playerTextArea2.setEditable(false);
		playerTextArea2.setBounds(119, 12, 106, 139);
		add(playerTextArea2);
		
		playerTextArea3.setEditable(false);
		playerTextArea3.setBounds(227, 12, 106, 139);
		add(playerTextArea3);
		
		playerTextArea4.setEditable(false);
		playerTextArea4.setBounds(332, 12, 106, 139);
		add(playerTextArea4);
				
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.setBounds(12, 183, 426, 80);
		add(table);
	}
	
	public void setPanel(Player[] playerArray, MULEMap myMap){
		
		TableModel tblModel = table.getModel();
		String[] playerInfo = new String[4];
		ArrayList<Tile> tileInfo = myMap.getTiles();
		Tile tmpTile;
		
		System.out.print(playerArray.length);

		for (int i = 0; i < 4 ; i++){
			if (i < playerArray.length){
				playerInfo[i] = "Player: ".concat(Integer.toString(i));
				playerInfo[i] = playerInfo[i].concat("\nName: ".concat(playerArray[i].getName()));
				playerInfo[i] = playerInfo[i].concat("\nDifficulty: \n    ".concat(playerArray[i].getLevel()));
				playerInfo[i] = playerInfo[i].concat("\nRace: ".concat(playerArray[i].getRace()));
				playerInfo[i] = playerInfo[i].concat("\nColor: ".concat(playerArray[i].getColor()));
			} else 
				playerInfo[i] = "No Player";
		}
		
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 9; j++){
				tmpTile = tileInfo.get(i*9 + j);
				tblModel.setValueAt(tmpTile.getType(), i, j);
			}
		}	
		
		playerTextArea1.setText(playerInfo[0]);
		playerTextArea2.setText(playerInfo[1]);
		playerTextArea3.setText(playerInfo[2]);
		playerTextArea4.setText(playerInfo[3]);
	}
}
