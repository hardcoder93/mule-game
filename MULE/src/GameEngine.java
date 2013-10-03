import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;


public class GameEngine extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEngine frame = new GameEngine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws HeadlessException 
	 */
	public GameEngine() throws HeadlessException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(185, 178, 153, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		btnNewButton.setBounds(950, 686, 117, 29);
		contentPane.add(btnNewButton);
				
				JLabel lblNameOfPlayer = new JLabel("Name of Player");
				lblNameOfPlayer.setFont(new Font("American Typewriter", Font.BOLD, 17));
				lblNameOfPlayer.setBounds(196, 150, 182, 29);
				contentPane.add(lblNameOfPlayer);
				
				JComboBox comboBox = new JComboBox();
				comboBox.setToolTipText("");
				comboBox.setFont(new Font("American Typewriter", Font.PLAIN, 13));
				comboBox.setBounds(196, 314, 124, 27);
				contentPane.add(comboBox);
				
				JLabel lblColor = new JLabel("Color");
				lblColor.setFont(new Font("American Typewriter", Font.BOLD, 17));
				lblColor.setBounds(225, 286, 153, 29);
				contentPane.add(lblColor);
				
				JComboBox comboBox_1 = new JComboBox();
				comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Flapper", "Human", "Others"}));
				comboBox_1.setFont(new Font("American Typewriter", Font.PLAIN, 13));
				comboBox_1.setBounds(208, 247, 104, 27);
				contentPane.add(comboBox_1);
				
				JLabel lblRace = new JLabel("Race\n");
				lblRace.setFont(new Font("American Typewriter", Font.BOLD, 17));
				lblRace.setBounds(229, 218, 53, 29);
				contentPane.add(lblRace);
				
				JLabel label = new JLabel("");
				label.setIcon(new ImageIcon("GameEngineImage.png"));
				label.setBounds(0, 0, 900, 575);
				contentPane.add(label);
		
	
		
	}
}
