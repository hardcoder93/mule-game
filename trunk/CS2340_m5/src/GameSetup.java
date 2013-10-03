import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class GameSetup extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameSetup frame = new GameSetup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameSetup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumberOfPlayer = new JLabel("Number of Player");
		lblNumberOfPlayer.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblNumberOfPlayer.setBounds(185, 150, 182, 29);
		contentPane.add(lblNumberOfPlayer);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(214, 180, 82, 27);
		contentPane.add(comboBox);
		
		JLabel lblDifficulty = new JLabel("Difficulty\n");
		lblDifficulty.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblDifficulty.setBounds(214, 286, 153, 29);
		contentPane.add(lblDifficulty);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Standard", "Tournament"}));
		comboBox_1.setToolTipText("");
		comboBox_1.setBounds(200, 315, 124, 27);
		contentPane.add(comboBox_1);
		
		JLabel lblMapType = new JLabel("Map Type\n");
		lblMapType.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblMapType.setBounds(214, 219, 153, 29);
		contentPane.add(lblMapType);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"River", "Plain", "1 Mountain", "2 Mountain", "3 Mountain"}));
		comboBox_2.setBounds(191, 247, 133, 27);
		contentPane.add(comboBox_2);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnNewButton.setBounds(777, 543, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("GameSetupImage.png"));
		lblNewLabel.setBounds(0, 0, 900, 575);
		contentPane.add(lblNewLabel);
	}
}
