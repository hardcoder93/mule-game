import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * The TurnStartPanel class extends JPanel and creates a panel that displays whose turn it is.
 * The panel contains a JLabel displaying whose turn it is and a button to begin the player's turn
 * 
 * @author John Certusi (jcertusi)
 *
 */
@SuppressWarnings("serial")
public class TurnStartPanel extends JPanel {

	private final Color BACKGROUND_COLOR = Color.black;		//background color for panel
	
	private JButton startTurnButton;		//button to begin turn
	private JLabel playerLabel;				//label showing whose turn it is

	/**
	 * Create the frame.
	 */
	public TurnStartPanel() {
		setLayout(null);
		this.setBackground(BACKGROUND_COLOR);
		
		startTurnButton = new JButton("START TURN");
		startTurnButton.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		startTurnButton.setBounds(777, 543, 117, 29);
		add(startTurnButton);

		playerLabel = new JLabel("");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBounds(400, 250, 200, 100);
		playerLabel.setAlignmentX(CENTER_ALIGNMENT);
		playerLabel.setAlignmentY(CENTER_ALIGNMENT);
		add(playerLabel);
	}
	
	/**
	 * Sets the text in the playerLabel to "It is <name of player>'s turn"
	 * @param player	the player whose turn it is
	 */
	public void setPlayerLabel(Player player){
		playerLabel.setText("<html><div style=\"text-align: center;\">" + "It is " + player.getName() + "'s turn!" +  "</html>");
	}
	
	public void setPubLabel(Player player, int money){
		playerLabel.setText("<html><div style=\"text-align: center;\">" + player.getName() + " received $" + money + " by gambling. \n Now " + player.getName() + " has $" + player.getMoney()  + "</html>");
	}
	/**
	 * Getter for the startTurnButton
	 * @return	startTurnButton
	 */
	public JButton getButton(){
		return startTurnButton;
	}
}

