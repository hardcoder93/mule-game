import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



/**
 * The TurnStartPanel class extends JPanel and creates a panel that displays whose turn it is.
 * The panel contains a JLabel displaying whose turn it is and a button to begin the player's turn
 * 
 * @author John Certusi (jcertusi)
 *
 */
@SuppressWarnings("serial")
public class TurnStartPanel extends JPanel {

	private final Color BACKGROUND_COLOR = new Color(0,0,0,255);		//background color for panel
	private final Color CLEAR = new Color(255,255,255,0);
	
	private JButton startTurnButton;		//button to begin turn
	private JLabel label, roundLabel;		//label showing whose turn it is
	private boolean inRound;

	/**
	 * Create the frame.
	 */
	public TurnStartPanel() {
		setLayout(null);
		this.setBackground(BACKGROUND_COLOR);
		
		startTurnButton = new JButton("Next");
		startTurnButton.setFont(new Font("Narkisim", Font.BOLD, 14));
		startTurnButton.setBounds(777, 543, 117, 29);
		add(startTurnButton);

		label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBounds(0, 0, 900, 600);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setOpaque(false);
		label.setFont(new Font("Narkisim", Font.BOLD, 20));
		
		roundLabel = new JLabel("");
		roundLabel.setForeground(Color.WHITE);
		roundLabel.setBounds(0, 0, 900, 400);
		roundLabel.setHorizontalAlignment(JLabel.CENTER);
		roundLabel.setVerticalAlignment(JLabel.CENTER);
		roundLabel.setOpaque(false);
		roundLabel.setFont(new Font("Narkisim", Font.BOLD, 14));
	}
	
	/**
	 * Sets the text in the playerLabel to "It is <name of player>'s turn"
	 * @param player	the player whose turn it is
	 */
	public void setPlayerLabel(Player player){
		remove(roundLabel);
		/*if (inRound)
			this.setBackground(CLEAR);
		else
			this.setBackground(BACKGROUND_COLOR);*/
		inRound = false;
		label.setText("<html><div style=\"text-align: center;\">" + "It is " + player.getName() + "'s turn!" +  "</html>");
		add(startTurnButton);
		add(label);
	}
	
	public void setPubLabel(Player player, int money){
		label.setText("<html><div style=\"text-align: center;\">" + player.getName() + " received $" + money + " by gambling.<br>Now " + player.getName() + " has $" + player.getMoney()  + "</html>");
	}
	/**
	 * Getter for the startTurnButton
	 * @return	startTurnButton
	 */
	public JButton getButton(){
		return startTurnButton;
	}

	public void setRoundLabel(int currentRound) {
		//remove(startTurnButton);
		inRound = true;
		
		label.setText("<html><div style=\"text-align: center;\">" + "Starting Round " + currentRound + "</html>");
		add(label);
	}

	public void setTimeIsUpLabel() {
		remove(roundLabel);
		label.setText("<html><div style=\"text-align: center;\">" + "Your turn  is over!<br>The timer reached zero!" + "</html>");		
	}
}

