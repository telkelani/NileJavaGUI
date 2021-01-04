package te00141_com1028_part2;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.com1028.part2.*;
import org.junit.Test;

public class GUIandClassTests {
	//Test for all user defined classes (not JFrame classes)
	@Test
	public void createBuyerObject() {
		Buyer b = new Buyer("Tarek","Saturn11!","4307413362827666", "04/20","930");
		assertEquals("Tarek",b.getUsername());
		assertEquals(100,b.getBalance(),0);
	}
	@Test
	public void createSellerObject() throws SQLException {
		Seller s = new Seller("Tarek1","Saturn11!","4363056236097627", "04/21","931");
		assertEquals("Tarek1",s.getUsername());
		assertEquals(0,s.getBalance(),0);
	}
	@Test
	public void createCartObject() {
		Cart c = new Cart();
	}
	@Test
	public void createProductObject() {
		Product p = new Product("Computer", "Intel i7 core",1200.50,2);
	}
	@Test
	public void testDatabaseConnection() throws SQLException {
		SQLDatabase data = SQLDatabase.getInstance();
		data.open();
		Statement s = data.getConnection().createStatement();
		ResultSet q = s.executeQuery("select * from account where type='Buyer'");
		String name = q.getString("username");
		assertEquals("PAMUK",name);
		data.getConnection().close();
	}
	@Test
	public void testCreateAccount() throws SQLException {
		SQLDatabase data = SQLDatabase.getInstance();
		data.open();
		Statement s = data.getConnection().createStatement();
		ResultSet q = s.executeQuery("select username from account where username='CreatedBuyerAccount'");
		String name = q.getString("username");
		assertEquals("CreatedBuyerAccount",name);
		Statement s1 = data.getConnection().createStatement();
		ResultSet q1 = s.executeQuery("select username from account where username='CreatedSellerAccount'");
		String sellername = q1.getString("username");
		assertEquals("CreatedSellerAccount",sellername);
		data.getConnection().close();
		
	}
	@Test
	public void testCreateProduct() throws SQLException {
		SQLDatabase data = SQLDatabase.getInstance();
		data.open();
		Statement s = data.getConnection().createStatement();
		ResultSet q = s.executeQuery("select name from product where name='TestProduct'");
		String name = q.getString("name");
		assertEquals("TestProduct",name);
		data.getConnection().close();
	}
	@Test 
	public void testAddProduct() throws SQLException {
		SQLDatabase data = SQLDatabase.getInstance();
		data.open();
		Statement s = data.getConnection().createStatement();
		ResultSet q = s.executeQuery("select quantity from product where name='TestProduct'");
		int quantity = q.getInt("quantity");
		assertEquals(3,quantity);
		data.getConnection().close();
	}
	@Test
	public void testPlaceOnMarket() throws SQLException {
		SQLDatabase data = SQLDatabase.getInstance();
		data.open();
		Statement s = data.getConnection().createStatement();
		ResultSet q = s.executeQuery("select name from marketplace where name='TestProduct'");
		String name = q.getString("name");
		assertEquals("TestProduct",name);
		data.getConnection().close();
	}
	
	
	

}
