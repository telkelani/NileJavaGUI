package org.com1028.part2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Cart implements IteratorInterface {
	private List<Product> productsInCart;
	private double totalPrice = 0;
	
public Cart() {
	productsInCart = new ArrayList<Product>();
	
	
}

public double getTotalPrice() {
	/**
	 * I need to set total price to 0 at first because if not it will keep adding the price on to the previous
	 * every time I open cart
	 */
	totalPrice = 0;
	for (Product p: productsInCart) {
		System.out.println("Product in cart "+p.getName());
		totalPrice+=p.getPrice();
	}
	return totalPrice;
}

public void addToCart(Product p)  {
	productsInCart.add(p);
	p.addToCartQuantity();

}

//Don't need a remove method because Iterator has the remove method built in

public void emptyCart() throws SQLException {
	for (Product p: productsInCart) {
		p.removeFromCartQuantity();
	}
	productsInCart.clear();
	totalPrice = 0;
}

@Override
public Iterator create_iterator() {
	// TODO Auto-generated method stub
	return productsInCart.iterator();
}







}
