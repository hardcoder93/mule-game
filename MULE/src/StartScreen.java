import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class StartScreen extends JPanel {

	private JButton btnQuit;

	public JButton getButton(){
		return btnQuit;
	}

	/**
	 * Create the frame.
	 */
	public StartScreen() {
		setLayout(null);
		
		btnQuit = new JButton("START");
		btnQuit.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnQuit.setBounds(777, 543, 117, 29);
		add(btnQuit);

		ImageIcon image = new ImageIcon("StartImage.png");
		JLabel lblNewLabel = new JLabel(image);

		lblNewLabel.setBounds(6, 6, 900, 575);
		add(lblNewLabel);
	}
}

