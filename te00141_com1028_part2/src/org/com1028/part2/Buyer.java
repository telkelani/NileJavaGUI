package org.com1028.part2;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Buyer extends Account {
	private Cart cart;
	private SQLDatabase database = SQLDatabase.getInstance();
	
	
	public Buyer(String username, String password, String ccn, String exp_date, String CVV) {
		super(username,password,ccn,exp_date, CVV);
		this.depositMoney(100);
		cart = new Cart();

		
		
		
	}
	
	public void depositMoney(double Amount) {
		if (Amount < 0) {
			throw new IllegalArgumentException();
		}
		this.Balance = Amount;
		
	}
	


	
	public boolean makePurchase() {
		boolean purchasemade = false;
		if (this.Balance > cart.getTotalPrice()) {
			purchasemade = true;
		}
		return purchasemade;
	}

	public Cart getCart() {
		// TODO Auto-generated method stub
		return cart;
	}




	/**
	 * updates balance of buyer based on the total amount they spent on their cart.
	 */
	public void updateBalance(Cart cart) {
		//This just subtracts the total price of cart from balance
		if (this.Balance > 0) {
		this.Balance-=cart.getTotalPrice();
		try {
		database.open();
		/**Updates the database with this information so the buyer does not always have initial amount of money 
		 * whenever they log in again
		 */
		Statement updatebalance = database.getConnection().createStatement();
		updatebalance.execute(String.format("UPDATE account  set balance=%f where username='%s'",Balance,this.getUsername()));
		updatebalance.close();
		database.getConnection().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
		
	}






	
	
	

}
