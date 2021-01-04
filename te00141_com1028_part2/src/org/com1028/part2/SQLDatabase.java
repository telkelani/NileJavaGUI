package org.com1028.part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;

public class SQLDatabase {
	private Connection con;
	private ArrayList<Seller> sellerList = new ArrayList<Seller>();
	private ArrayList<Buyer> buyerList  = new ArrayList<Buyer>();
	private static final SQLDatabase instance = new SQLDatabase(); //This ensures only one object is created in singleton class because of the keyword final
	private  SQLDatabase() {
	}
	/**
	 * Connects the database to java 
	 */
	public void open() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.con = DriverManager.getConnection("jdbc:sqlite:C:\\Software Engineering CW part 2\\te00141_com1028_part2\\niledatabase.db");
			System.out.println("Connected");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Turn on web server and restart program");
		}
	}
	/**
	 * Whenever any other class wants to use the database, this method must be called
	 * @return
	 * instance of SQLDatabase
	 */
	public static SQLDatabase getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		return this.con;
	}
	/**
	 * Retrieves the accounts that are saved on the database and creates objects based on the account table
	 * 
	 * @throws SQLException
	 */
	public void populateExisting_Accs() throws SQLException {
		this.open();
		Statement existing_acc_statement = con.createStatement();
		//Queries database for existing accounts by selecting all entries from account table
		ResultSet Existing_Accs = existing_acc_statement.executeQuery("select * from account");
		//while the query is not null
		while (Existing_Accs.next()) {
			/** Retrieves data from query by datatype of column**/
			String username = Existing_Accs.getString(1);
			String password = Existing_Accs.getString(2);
			String account_type = Existing_Accs.getString(3);
			String ccn = Existing_Accs.getString(4);
			String exp_date = Existing_Accs.getString(5);
			String cvv = Existing_Accs.getString(6);
			
			/**Added a type column so that the program can differentiate when
			 * to create a buyer or a seller object
			 */
			if (account_type.equals("Buyer")) {
				Buyer b = new Buyer(username,password,ccn,exp_date,cvv);
				if (!this.buyerList.contains(b)) {
				this.buyerList.add(b);
				}
				System.out.println("existing buyers "+b);
			}
			if (account_type.equals("Seller")) {
				
			Seller s = new Seller(username,password,ccn,exp_date,cvv);
			if (!this.sellerList.contains(s)) {
			this.sellerList.add(s);
			}
			}
			
		 
	}
		con.close();

}
	/**
	 * This function reads in the sql table marketplace whenever the program is rebooted, so when the program
	 * is started again the marketplace doesn't reset
	 * 
	 * @throws SQLException
	 */
public void ReadFromMarketplaceTable() throws SQLException {
	this.open();
	Statement market_statement = this.getConnection().createStatement();
	ResultSet market_query = market_statement.executeQuery("select * from marketplace");
	
	while (market_query.next()) { 
		for (Seller s: this.sellerList) { 
			Iterator<Product> sellersproducts = s.getInventory();
			
			while (sellersproducts.hasNext()) {
			Product p = sellersproducts.next();
			if (market_query.getString("sold_by").equals(s.getUsername())) {
					if (p.getName().equals(market_query.getString("name"))){
						s.placeProductOnSale(p);

					
				}
			}
			}
		}
	}
	this.con.close();
	
}
	public void addAccountToList(Object o) {
		if (o instanceof Buyer) {
			this.buyerList.add((Buyer) o);
		}
		if (o instanceof Seller) {
			this.sellerList.add((Seller) o);
		}
	}

	
	public Iterator<Buyer> getBuyers(){
		return buyerList.iterator();
	}
	
	public Iterator<Seller> getSellers(){
		return sellerList.iterator();
	}
	






	

	
}
