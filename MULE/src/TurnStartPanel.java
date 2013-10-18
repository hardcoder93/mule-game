import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TurnStartPanel extends JPanel {

	private JButton startTurnButton;
	private JLabel playerLabel;

	/**
	 * Create the frame.
	 */
	public TurnStartPanel() {
		setLayout(null);
		this.setBackground(Color.BLACK);
		
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
	
	public void setPlayerLabel(Player player){
		playerLabel.setText("<html><div style=\"text-align: center;\">" + "It is " + player.getName() + "'s turn!" +  "</html>");
	}
	
	public JButton getButton(){
		return startTurnButton;
	}
}

