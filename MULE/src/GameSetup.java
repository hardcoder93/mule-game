import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class GameSetup extends JPanel {

	private JButton btnNewButton;
	private JComboBox<String> playerCountBox;
	private JComboBox<String> difficultyBox;
	private JComboBox<String> mapTypeBox;
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

	public JButton getButton(){
		return btnNewButton;
	}
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GameSetup() {
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);*/
		this.setLayout(null);
		
		JLabel lblNumberOfPlayer = new JLabel("Number of Players:");
		lblNumberOfPlayer.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblNumberOfPlayer.setBounds(185, 150, 182, 29);
		add(lblNumberOfPlayer);
		
		playerCountBox = new JComboBox();
		playerCountBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		playerCountBox.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		playerCountBox.setToolTipText("");
		playerCountBox.setBounds(214, 180, 82, 27);
		add(playerCountBox);
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		lblDifficulty.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblDifficulty.setBounds(214, 286, 153, 29);
		add(lblDifficulty);
		
		difficultyBox = new JComboBox();
		difficultyBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		difficultyBox.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Standard", "Tournament"}));
		difficultyBox.setToolTipText("");
		difficultyBox.setBounds(200, 315, 124, 27);
		add(difficultyBox);
		
		JLabel lblMapType = new JLabel("Map Type:");
		lblMapType.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblMapType.setBounds(214, 219, 153, 29);
		add(lblMapType);
		
		mapTypeBox = new JComboBox();
		mapTypeBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		mapTypeBox.setModel(new DefaultComboBoxModel(new String[] {"Standard", "Random"}));
		mapTypeBox.setBounds(191, 247, 133, 27);
		add(mapTypeBox);
		
		btnNewButton = new JButton("Next");
		btnNewButton.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnNewButton.setBounds(777, 543, 117, 29);
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("GameSetupImage.png"));
		lblNewLabel.setBounds(0, 0, 900, 600);
		add(lblNewLabel);
	}
	
	public int getPlayerCount(){
		return Integer.parseInt((String)playerCountBox.getSelectedItem());
	}
	
	public String getDifficulty(){
		return (String)difficultyBox.getSelectedItem();
	}
	
	public String getMapType(){
		return (String)mapTypeBox.getSelectedItem();
	}
}
