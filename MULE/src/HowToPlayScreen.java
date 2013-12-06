import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;


public class HowToPlayScreen extends JPanel {
	JButton btnToStartScreen;
	
	/**
	 * Create the panel.
	 */
	public HowToPlayScreen() {
		setLayout(null);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0,0,900,600);
		backgroundLabel.setIcon(new ImageIcon("howToPlayImage.png"));
		
		btnToStartScreen = new JButton("Back to Main Menu");
		btnToStartScreen.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnToStartScreen.setBounds(682, 555, 200, 30);
		btnToStartScreen.setBackground(new Color(255, 255, 255, 150));
		add(btnToStartScreen);
	}
	
	public JButton getToStartScreenButton(){
		return btnToStartScreen;
	}
}
