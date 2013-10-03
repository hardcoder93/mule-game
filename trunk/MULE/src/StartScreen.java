import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class StartScreen extends JFrame {

	private JPanel contentPane;
	private JButton btnQuit;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen frame = new StartScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JButton getButton(){
		return btnQuit;
	}

	/**
	 * Create the frame.
	 */
	public StartScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnQuit = new JButton("START");
		btnQuit.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnQuit.setBounds(777, 543, 117, 29);
		contentPane.add(btnQuit);

		ImageIcon image = new ImageIcon("StartImage.png");
		JLabel lblNewLabel = new JLabel(image);

		lblNewLabel.setBounds(6, 6, 900, 575);
		contentPane.add(lblNewLabel);
	}









}

