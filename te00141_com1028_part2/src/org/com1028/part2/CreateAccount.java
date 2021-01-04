package org.com1028.part2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class CreateAccount extends JFrame {

	private JPanel contentPane;
	private JPanel InitialPanel;
	private JTextField txtUsername;
	private JPasswordField SellerpasswordField;
	private JTextField CCNtxt;
	private JTextField ExpDatetxt;
	private JTextField BuyerUsernameTxt;
	private JPasswordField BuyerpasswordField;
	private JTextField BuyerCCNTxt;
	private JTextField BuyerExpDateTxt;
	private JPanel BuyerPanel;
	private JPanel SellerPanel;
	private JTextField BuyerCVVTxt;
	private JTextField SellerCVVTxt;
	SQLDatabase database = SQLDatabase.getInstance();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
	
	


	/**
	 * Launch the application.
	 */
	public static void CreateAccountMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount frame = new CreateAccount();
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
	public CreateAccount() {
		setTitle("Create Account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		InitialPanel = new JPanel();
		InitialPanel.setBounds(0, 0, 428, 244);
		contentPane.add(InitialPanel);
		InitialPanel.setLayout(new CardLayout(0, 0));
		
		SellerPanel = new JPanel();
		SellerPanel.setLayout(null);
		InitialPanel.add(SellerPanel, "name_237119627854300");
		
		JLabel label_5 = new JLabel("Username");
		label_5.setBounds(20, 81, 81, 20);
		SellerPanel.add(label_5);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(96, 78, 146, 26);
		SellerPanel.add(txtUsername);
		
		JLabel label_6 = new JLabel("Password");
		label_6.setBounds(20, 108, 69, 20);
		SellerPanel.add(label_6);
		
		SellerpasswordField = new JPasswordField();
		SellerpasswordField.setBounds(96, 105, 146, 26);
		SellerPanel.add(SellerpasswordField);
		
		CCNtxt = new JTextField();
		CCNtxt.setColumns(10);
		CCNtxt.setBounds(164, 133, 146, 26);
		SellerPanel.add(CCNtxt);
		
		JLabel label_7 = new JLabel("Credit Card Number");
		label_7.setBounds(15, 136, 173, 20);
		SellerPanel.add(label_7);
		
		JLabel label_8 = new JLabel("Expiry Date");
		label_8.setBounds(15, 194, 81, 20);
		SellerPanel.add(label_8);
		
		ExpDatetxt = new JTextField();
		ExpDatetxt.setColumns(10);
		ExpDatetxt.setBounds(96, 191, 69, 26);
		SellerPanel.add(ExpDatetxt);
		
		JLabel lblCvvCode = new JLabel("CVV Code");
		lblCvvCode.setBounds(20, 172, 81, 20);
		SellerPanel.add(lblCvvCode);
		
		SellerCVVTxt = new JTextField();
		SellerCVVTxt.setBounds(96, 169, 38, 26);
		SellerPanel.add(SellerCVVTxt);
		SellerCVVTxt.setColumns(10);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					createSellerAccount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCreateAccount.setBounds(250, 190, 163, 29);
		SellerPanel.add(btnCreateAccount);
		
		JLabel lblNewBuyerAccount = new JLabel("New Seller Account");
		lblNewBuyerAccount.setBounds(130, 53, 163, 20);
		SellerPanel.add(lblNewBuyerAccount);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
					MainMenuScreen.main(null);
				
				
				
				
			}
		});
		btnBack.setBounds(0, 0, 115, 29);
		SellerPanel.add(btnBack);
		
		JButton btnBuyer = new JButton("Buyer");
		btnBuyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitialPanel.removeAll();
				InitialPanel.add(BuyerPanel);
				InitialPanel.repaint();
				InitialPanel.revalidate();
			}
		});
		btnBuyer.setBounds(0, 36, 115, 29);
		SellerPanel.add(btnBuyer);
		
		JButton btnSeller = new JButton("Seller");
		btnSeller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitialPanel.removeAll();
				InitialPanel.add(SellerPanel);
				InitialPanel.repaint();
				InitialPanel.revalidate();
			}
		});
		btnSeller.setBounds(313, 36, 115, 29);
		SellerPanel.add(btnSeller);
		
		BuyerPanel = new JPanel();
		BuyerPanel.setLayout(null);
		InitialPanel.add(BuyerPanel, "name_237170132824500");
		
		JLabel label = new JLabel("Buyer Account");
		label.setBounds(146, 63, 102, 20);
		BuyerPanel.add(label);
		
		JLabel label_1 = new JLabel("Username");
		label_1.setBounds(20, 102, 81, 20);
		BuyerPanel.add(label_1);
		
		BuyerUsernameTxt = new JTextField();
		BuyerUsernameTxt.setColumns(10);
		BuyerUsernameTxt.setBounds(96, 99, 146, 26);
		BuyerPanel.add(BuyerUsernameTxt);
		
		JLabel label_2 = new JLabel("Password");
		label_2.setBounds(20, 129, 69, 20);
		BuyerPanel.add(label_2);
		
		BuyerpasswordField = new JPasswordField();
		BuyerpasswordField.setBounds(96, 126, 146, 26);
		BuyerPanel.add(BuyerpasswordField);
		
		BuyerCCNTxt = new JTextField();
		BuyerCCNTxt.setColumns(10);
		BuyerCCNTxt.setBounds(164, 154, 146, 26);
		BuyerPanel.add(BuyerCCNTxt);
		
		JLabel label_3 = new JLabel("Credit Card Number");
		label_3.setBounds(15, 157, 173, 20);
		BuyerPanel.add(label_3);
		
		JLabel label_9 = new JLabel("Expiry Date");
		label_9.setBounds(20, 205, 81, 20);
		BuyerPanel.add(label_9);
		
		BuyerExpDateTxt = new JTextField();
		BuyerExpDateTxt.setColumns(10);
		BuyerExpDateTxt.setBounds(106, 202, 69, 26);
		BuyerPanel.add(BuyerExpDateTxt);
		
		JButton CreateBuyerAccountButton = new JButton("Create Account");
		CreateBuyerAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBuyerAccount();
			}
		});
		CreateBuyerAccountButton.setBounds(242, 196, 171, 29);
		BuyerPanel.add(CreateBuyerAccountButton);
		
		JLabel lblCvv = new JLabel("CVV Code");
		lblCvv.setBounds(20, 181, 81, 20);
		BuyerPanel.add(lblCvv);
		
		BuyerCVVTxt = new JTextField();
		BuyerCVVTxt.setBounds(106, 178, 43, 26);
		BuyerPanel.add(BuyerCVVTxt);
		BuyerCVVTxt.setColumns(10);
		
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenuScreen.main(null);
			
			}
		});
		button.setBounds(0, 0, 115, 29);
		BuyerPanel.add(button);
		
		JButton button_1 = new JButton("Buyer");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(BuyerPanel);
			}
		});
		button_1.setBounds(0, 34, 115, 29);
		BuyerPanel.add(button_1);
		
		JButton button_2 = new JButton("Seller");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(SellerPanel);
			}
		});
		button_2.setBounds(313, 34, 115, 29);
		BuyerPanel.add(button_2);
		
		
		}
	public JPanel getAccountPanel() {
		return InitialPanel;
	}
	/**
	 * Creates a seller account, creates the seller object and creates a record in database in the account table
	 * @throws SQLException
	 */
private void createSellerAccount() throws SQLException {
	database.open();
	/**Extract user input**/
	String username = txtUsername.getText();
	String password = new String(SellerpasswordField.getPassword());
	String CCN = CCNtxt.getText();
	String Exp_Date = ExpDatetxt.getText();
	String CVV = SellerCVVTxt.getText();
	
	double balance = 0; //Seller starts with £0
	/**The object is in a try statement because the Account class constructor is validated*
	 * the constructor throws errors if the credit card details and password is not valid*/
	try {
	Seller s = new Seller(username,password,CCN,Exp_Date, CVV);
	//If seller can be created, the data is inserted into the account table which stores all the accounts
	//String.format() is a useful function which allows for placeholders for dynamic strings
	//For example, the username and password will change for every user so the placeholder %s is for a string data type which is then update by the variable username
	
	String insert_string = String.format("insert into account values('%s','%s','%s','%s','%s','%s','%f')",username,password,"Seller",CCN,Exp_Date,CVV,balance);
	Statement insertintoaccount = database.getConnection().createStatement();
	insertintoaccount.executeUpdate(insert_string);
	//This adds the account to the seller list in SQLDatabase class so that an iterator can be created from this
	database.addAccountToList(s);
	JOptionPane.showMessageDialog(null, "Successfully created account");
	}
	catch (Exception l) {
		l.printStackTrace();
		switch (l.getMessage()) {
		case "PASSWORD NOT VALID":
			JOptionPane.showMessageDialog(null, "Invalid Password, must have at least one capital letter, one number and one symbol");
			break;
		
		case "CCN NOT VALID":
			JOptionPane.showMessageDialog(null, "Invalid Credit Card Number");
			break;
		case "EXP NOT VALID":
			JOptionPane.showMessageDialog(null, "Credit Card is expired");
			break;
		case "CVV NOT VALID":
			JOptionPane.showMessageDialog(null, "CVV code is invalid, should only be 3 digits");
			break;
		}
	}
	database.getConnection().close();

	}
	
/**
 * 
 * @see createSellerAccount() same functionality but for buyer account
 * 
 */
private void createBuyerAccount()  {
		database.open();
		String username = BuyerUsernameTxt.getText();
		String password = new String(BuyerpasswordField.getPassword());
		String CCN = BuyerCCNTxt.getText();
		String Exp_Date = BuyerExpDateTxt.getText();
		String CVV = BuyerCVVTxt.getText();
		System.out.println("Hello Buyer");
		try {
		double balance = 100;
		
		Buyer b = new Buyer(username,password,CCN,Exp_Date, CVV);
		System.out.println("String "+CCN);
		String insert_string = String.format("insert into account values('%s','%s','%s','%s','%s','%s','%f')",username,password,"Buyer",CCN,Exp_Date,CVV,balance);
		Statement insertintoaccount = database.getConnection().createStatement();
		insertintoaccount.executeUpdate(insert_string);
		database.addAccountToList(b);
		b.setBalance(100); //All buyers start with £100
		System.out.println("HERE");
		JOptionPane.showMessageDialog(null, "Successfully created account");
		database.getConnection().close();
		}
	
		catch (Exception l) {
			l.printStackTrace();
			switch (l.getMessage()) {
			case "PASSWORD NOT VALID":
				JOptionPane.showMessageDialog(null, "Invalid Password, must have at least one capital letter, one number and one symbol");
				break;
			
			case "CCN NOT VALID":
				JOptionPane.showMessageDialog(null, "Invalid Credit Card Number");
				break;
			case "EXP NOT VALID":
				JOptionPane.showMessageDialog(null, "Credit Card is expired");
				break;
			case "CVV NOT VALID":
				JOptionPane.showMessageDialog(null, "CVV code is invalid, should only be 3 digits");
				break;
			}
		
	}
	
	try {
		database.getConnection().close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * This JFrame has multiple pages so it is necessary to changePanel when either the buyer or seller button is clicked
 * @param panel
 * the panel to switch to 
 */
private void changePanel(JPanel panel) {
	InitialPanel.removeAll();
	InitialPanel.add(panel);
	InitialPanel.repaint();
	InitialPanel.revalidate();
}
}

