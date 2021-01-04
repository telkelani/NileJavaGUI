package org.com1028.part2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Seller extends Account {
private ArrayList<Product> inventory = new ArrayList<Product>();;
private ArrayList<Product> ProductsForSale = new ArrayList<Product>();;
private SQLDatabase database = SQLDatabase.getInstance();



public Product createProduct(String name,String description, double price, int quantity) {
	
	Product newProduct = new Product(name,description, price, quantity);
	newProduct.setSoldBy(this);
	inventory.add(newProduct);
	
	return newProduct;
}

public void placeProductOnSale(Product product) {
	if (!ProductsForSale.contains(product)) {
	ProductsForSale.add(product);
	}
	
}

public void addToInventory(Product p) {
	inventory.add(p);
}

public void removeProductFromSale(Product product) {
	if (ProductsForSale.contains(product)) {
		ProductsForSale.remove(product);
	}
}

public Seller(String username,String password, String CCN, String Exp_Date, String CVV) throws SQLException {
	
	super(username,password,CCN,Exp_Date, CVV);
	/*Cannot put populateExistingAccs() and ReadFromMarketplace() method in SQLDatabase constructor
	 * because the connection wouldn't have been made so this would raise a null pointer exception
	 */
	database.open(); //Must open database before using getting connection.
	Statement query_products = database.getConnection().createStatement();
	ResultSet rs = query_products.executeQuery(String.format("select name,description,price,quantity from product where sold_by='%s'",username));
	while (rs.next()) {
		this.createProduct(rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity"));
	}
	
	//System.out.println("existing products in seller inventory"+this.inventory);
	
}




public Iterator<Product> getInventory(){
	return inventory.iterator();
}

public Iterator<Product> getProductsForSale(){
	return ProductsForSale.iterator();
}
/**
 * Updates balance of seller
 */
@Override
public void updateBalance(Cart cart) {
	// TODO Auto-generated method stub
	Iterator<Product> productsInCart = cart.create_iterator();
	/**Searches the contents of the cart and checks to see if any of the products belongs to the seller
	 * If it does the balance is incremented by the price of the product and the object's balance is update
	 * the database is also updated so when the seller views how much money they have it won't reset 
	 * (they don't lose their money)
	 */
	while (productsInCart.hasNext()) {
		Product p = productsInCart.next();
		Seller salesperson = p.getSoldBy();
		if (salesperson.equals(this)) {
		Statement updatesellerbalance;
		try {
				database.open();
				updatesellerbalance = database.getConnection().createStatement();
				double balance = salesperson.getBalance()+p.getPrice();
				salesperson.setBalance(balance);
				updatesellerbalance.execute(String.format("update account set balance=%f where username='%s'",salesperson.getBalance(),salesperson.getUsername()));
				Statement updateProductQuantity = database.getConnection().createStatement();
				updateProductQuantity.execute(String.format("update marketplace set quantity=%d where name='%s'", p.getQuantity()-p.getQuantityInCart(),p.getName()));
				updateProductQuantity.execute(String.format("update product set quantity=%d where name='%s'", p.getQuantity()-p.getQuantityInCart(),p.getName()));
				database.getConnection().close();
		} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
			
			

	}
	
}
	
}



