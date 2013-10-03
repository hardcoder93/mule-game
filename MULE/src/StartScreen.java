import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class StartScreen extends JFrame {

	private JPanel contentPane;
	private JButton click;
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
		btnQuit.addActionListener(new addListener());

		ImageIcon image = new ImageIcon("StartImage.png");
		JLabel lblNewLabel = new JLabel(image);

		lblNewLabel.setBounds(6, 6, 900, 575);
		contentPane.add(lblNewLabel);
	}

	private class addListener implements ActionListener{
		/*
		 * @param e ActionEvent
		 */
		public void actionPerformed (ActionEvent e){
			click = (JButton)e.getSource();
			go(click.equals(btnQuit));				
		}
		
		private boolean go(boolean yes){
			System.out.println("ds");
			return yes;
	}
	
}



}
