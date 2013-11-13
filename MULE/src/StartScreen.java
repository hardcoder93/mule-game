import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class StartScreen extends JPanel {

	private JButton btnNewGame;
	private JButton btnLoadGame;

	public JButton getNewGameButton(){
		return btnNewGame;
	}
	
	public JButton getLoadGameButton(){
		return btnLoadGame;
	}

	/**
	 * Create the frame.
	 */
	public StartScreen() {
		setLayout(null);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Narkisim", Font.BOLD, 20));
		btnNewGame.setBounds(700, 495, 175, 35);
		btnNewGame.setBackground(new Color(255,255,255,150));
		add(btnNewGame);
		
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnLoadGame.setBounds(55, 500, 125, 30);
		btnLoadGame.setBackground(new Color(255,255,255,150));
		add(btnLoadGame);

		ImageIcon image = new ImageIcon("IMAGES/StartImage.png");
		JLabel lblNewLabel = new JLabel(image);

		lblNewLabel.setBounds(0, 0, 900, 600);
		add(lblNewLabel);
	}
}

