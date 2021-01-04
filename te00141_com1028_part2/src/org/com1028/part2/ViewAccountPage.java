package org.com1028.part2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ViewAccountPage extends JFrame {

	private JPanel contentPane;
	private static Account account;
	
	private JTextField DepositAmount;
	SQLDatabase data = SQLDatabase.getInstance();
	protected static Object object;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAccountPage frame = new ViewAccountPage(account);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ViewAccountPage(Account acc) throws SQLException {
		account = acc;
		data.open();
		Statement query_balance = data.getConnection().createStatement();
	    ResultSet newbalance = query_balance.executeQuery(String.format("select balance from account where username='%s'",account.getUsername()));
	    if (newbalance.next()) {
	    	double balance = newbalance.getDouble("balance");
	    	account.Balance = balance;
	    }
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(account.getUsername()+"'s Account");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UsernameLbl = new JLabel("Username: "+account.getUsername());
		UsernameLbl.setBounds(0, 16, 165, 20);
		contentPane.add(UsernameLbl);
		
		JLabel lblPassword = new JLabel("Password: "+account.getPassword());
		lblPassword.setBounds(0, 38, 165, 20);
		contentPane.add(lblPassword);
		
		JLabel lblCreditCardNumber = new JLabel("Credit Card Number: "+account.getCCN());
		lblCreditCardNumber.setBounds(0, 58, 269, 20);
		contentPane.add(lblCreditCardNumber);
		
		JLabel lblExpiryDate = new JLabel("Expiry Date: "+account.getExpiryDate());
		lblExpiryDate.setBounds(0, 74, 190, 20);
		contentPane.add(lblExpiryDate);
		
		JLabel lblCvvCode = new JLabel("CVV Code: "+account.getCVV());
		lblCvvCode.setBounds(0, 94, 179, 20);
		contentPane.add(lblCvvCode);
		
		JLabel lblBalance = new JLabel("Balance: \u00A3"+account.getBalance());
		lblBalance.setBounds(0, 116, 165, 20);
		contentPane.add(lblBalance);
		
		JButton DepositButton = new JButton("Deposit Amount");
		DepositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depositAmount();
				
			}
		});
		DepositButton.setBounds(216, 150, 147, 38);
		contentPane.add(DepositButton);
		
		DepositAmount = new JTextField();
		DepositAmount.setBounds(45, 152, 171, 36);
		contentPane.add(DepositAmount);
		DepositAmount.setColumns(10);
		
		JLabel label = new JLabel("\u00A3");
		label.setBounds(32, 155, 51, 29);
		contentPane.add(label);
		data.getConnection().close();
	}
	/**
	 * Allows buyer to add money to their account in User interface
	 */
	private void depositAmount() {
		try {
		data.open();
		double depositedamount = Double.parseDouble(DepositAmount.getText());
		/**instanceof checks to see if the static account variable is a buyer 
		 * if it is then the balance is updated.
		 */
		if (depositedamount > 0 && account instanceof Buyer) {
		account.Balance+=depositedamount;
		Statement update_account_balance = data.getConnection().createStatement();
		update_account_balance.execute(String.format("update account set balance = %f where username='%s'",account.Balance,account.getUsername()));
		
		
		JOptionPane.showMessageDialog(null, "Money successfully added to account");
		data.getConnection().close();
		}
		//If the buyer wants to add a negative amount of money
		else {
			JOptionPane.showMessageDialog(null, "Why do you want to be more broke?");
		}
		/**If it is a seller, they cannot use this function as sellers cannot add money
		 * JOptionPane allows a popup to be shown.
		 */
		if (!(account instanceof Buyer)) {
			JOptionPane.showMessageDialog(null, "Seller cannot add money to account");
		}
	
	
		}
		catch (Exception g) {
			JOptionPane.showMessageDialog(null, "What are you doing?");
		}
		
	}
}
