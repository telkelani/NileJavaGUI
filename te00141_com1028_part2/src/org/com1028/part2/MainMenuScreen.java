package org.com1028.part2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class MainMenuScreen {

	private JFrame frmMainMenu;
	private JPanel MainMenuPanel;
	private SQLDatabase database = SQLDatabase.getInstance();
	/*This boolean variable is very important as this only makes the mainmenuscreen read from the database once
	 * So when the main menu is called again from another frame, the database doesn't populate more than once
	 * which causes duplicates
	 * 
	 */
	private static boolean ran = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuScreen window = new MainMenuScreen();
					window.frmMainMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */

	public MainMenuScreen() throws SQLException {
		initialize();
		
		//This needs to be here because in the constructor of seller it connects the database, also in constructor of database it uses the seller constructor
		if(this.ran==false) {
		database.populateExisting_Accs();
		database.ReadFromMarketplaceTable();
		ran=true;
		}
		

	}



		


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainMenu = new JFrame();
		frmMainMenu.setTitle("Main Menu");
		frmMainMenu.setBounds(100, 100, 709, 421);
		frmMainMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMainMenu.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel CardPanel = new JPanel();
		frmMainMenu.getContentPane().add(CardPanel, "name_125450076377400");
		CardPanel.setLayout(new CardLayout(0, 0));
		
		MainMenuPanel = new JPanel();
		CardPanel.add(MainMenuPanel, "name_125472739937900");
		MainMenuPanel.setLayout(null);
		
		JLabel lblWelcomeToNile = new JLabel("Welcome To Nile Marketplace");
		lblWelcomeToNile.setBounds(90, 16, 245, 40);
		MainMenuPanel.add(lblWelcomeToNile);
		lblWelcomeToNile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(0, 72, 672, 117);
		MainMenuPanel.add(btnCreateAccount);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(0, 205, 672, 144);
		MainMenuPanel.add(btnLogin);
		
		
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainMenu.dispose();
				CreateAccount.CreateAccountMain();
			
				
				
				
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainMenu.dispose();
				database.open();
				MainProgram.main(null);
				
				
				
			}
		});
	
	}
}
