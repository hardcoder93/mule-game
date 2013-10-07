import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class PlayerSetup extends JPanel {

	//private JPanel contentPane;
	private static JTextField textField;
	private JButton btnNewButton;
	private JComboBox playerNameBox;
	private static JComboBox raceBox;
	private static JComboBox<String> colorBox;
	private String[] colorOptions = {"Red", "Blue", "Green", "Yellow"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerSetup frame = new PlayerSetup();
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
	 * Create the frame.
	 * @throws IOException 
	 * @throws HeadlessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PlayerSetup(){ //throws HeadlessException, IOException {
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);*/

		textField = new JTextField();
		textField.setBounds(185, 178, 153, 28);
		add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("Next");
		btnNewButton.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnNewButton.setBounds(950, 686, 117, 29);
		add(btnNewButton);

		JLabel lblNameOfPlayer = new JLabel("Name of Player");
		lblNameOfPlayer.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblNameOfPlayer.setBounds(196, 150, 182, 29);
		add(lblNameOfPlayer);

		colorBox = new JComboBox();
		colorBox.setModel(new DefaultComboBoxModel(colorOptions));
		colorBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		colorBox.setBounds(196, 314, 124, 27);
		add(colorBox);

		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblColor.setBounds(225, 286, 153, 29);
		add(lblColor);

		raceBox = new JComboBox();
		raceBox.setModel(new DefaultComboBoxModel(new String[] {"Flapper", "Human", "Others"}));
		raceBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		raceBox.setBounds(208, 247, 104, 27);
		add(raceBox);

		JLabel lblRace = new JLabel("Race:");
		lblRace.setFont(new Font("American Typewriter", Font.BOLD, 17));
		lblRace.setBounds(229, 218, 53, 29);
		add(lblRace);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("PlayerSetupImage.png"));
		label.setBounds(0, 0, 900, 575);
		add(label);
	}
	
	
	public void clearPlayerName(){
		textField.setText("");
	}
	
	/**
	 * Removes a color from the list of color options
	 * Used when a player selects a color
	 * 
	 * @param color the color to be removed
	 */
	public void removeColor(String color){
		ArrayList<String> newColors = new ArrayList<String>();
		for (String current : colorOptions)
			if (!current.equals(color)) 
				newColors.add(current);
		this.colorOptions = new String[newColors.size()];
		for (int i = 0; i < newColors.size(); i++)
			this.colorOptions[i] = newColors.get(i);
		colorBox.setModel(new DefaultComboBoxModel<String>(colorOptions));
		
	}
	
	public String getPlayerName(){
		return textField.getText();
	}
	
	public String getPlayerRace(){
		return raceBox.getSelectedItem().toString();
	}
	
	public String getPlayerColor(){
		return colorBox.getSelectedItem().toString();
	}
	
	public String getName(){
		return "";
	}
}