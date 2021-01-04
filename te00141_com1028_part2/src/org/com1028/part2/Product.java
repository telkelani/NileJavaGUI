package org.com1028.part2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

public class Product {
		private String name;
		private String description;
		private double price;
		private int quantity = 0;
		private Seller SoldBy = null;
		//quantityInCart is an attribute which is used to keep track of the amount of this product added to the cart
		private int quantityInCart = 0;
		private SQLDatabase database = SQLDatabase.getInstance();
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public double getPrice() {
			return price;
		}
		public int getQuantity() {
			return quantity;
		}
		public Seller getSoldBy() {
			return SoldBy;
		}
		public int getQuantityInCart() {
			return quantityInCart;
		}
		

	public Product(String name, String description, double price, int quantity) {
			this.name = name;
			this.description = description;
			this.price =  price;
			this.quantity = quantity;
		}
	public void setSoldBy(Seller soldBy) {
		SoldBy = soldBy;
	}
	
	public void addToCartQuantity() {
		this.quantityInCart++;
	}
	public void removeFromCartQuantity() {
		this.quantityInCart--;
	}
	/**
	 * Removes product quantity 
	 * @param quantity
	 * quantity to remove from product
	 * @throws SQLException
	 */
	public void removeFromQuantity(int quantity) throws SQLException {
		database.open();
		//updates product table so the display functions can display the right information in the table
		
		this.quantity-=quantity;
		System.out.println(quantity);
		String sql = String.format("update product set quantity=%d where name='%s'",this.quantity,this.name);
		PreparedStatement remove = database.getConnection().prepareStatement(sql);
		remove.execute();
		database.getConnection().close();
	}
	/**
	 * Adds to product quantity
	 * @param quantity
	 * @see removeFromQuantity
	 * does the same thing but adds to quantity
	 * the quantity to add
	 * @throws SQLException
	 */
	public void addToQuantity(int quantity) throws SQLException {
		database.open();
		String sql = String.format("update product set quantity=%d where name='%s'",this.quantity+1,this.name);
		PreparedStatement add = database.getConnection().prepareStatement(sql);
		add.execute();
		this.quantity+=quantity;
		database.getConnection().close();
	}

	
	}

