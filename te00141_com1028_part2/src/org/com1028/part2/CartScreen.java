package org.com1028.part2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ListIterator;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class CartScreen extends JFrame  {
	private JTable table;
	private static Buyer buyer;
	private JLabel lblTotalCost;
	private SQLDatabase database = SQLDatabase.getInstance();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartScreen frame = new CartScreen(buyer);
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
	public CartScreen(Buyer b) {
		buyer = b;
		setTitle("Cart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblCart = new JLabel("Cart");
		lblCart.setBounds(187, 16, 39, 20);
		getContentPane().add(lblCart);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 77, 428, 117);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		displayCart();
		
		
		JButton btnRemove = new JButton("Delete");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteItem();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		

		
		btnRemove.setBounds(0, 215, 104, 29);
		getContentPane().add(btnRemove);
		
		JLabel lblCartTotal = new JLabel("Cart Total ");
		lblCartTotal.setBounds(252, 219, 82, 20);
		getContentPane().add(lblCartTotal);
		
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
			try {
				checkout();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		});
		btnCheckout.setBounds(286, 32, 115, 29);
		getContentPane().add(btnCheckout);
		
		JButton btnEmpty = new JButton("Empty");
		btnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					emptyCart();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnEmpty.setBounds(103, 215, 115, 29);
		getContentPane().add(btnEmpty);
		
		JLabel lblInOrderTo = new JLabel("In Order to see changes");
		lblInOrderTo.setBounds(0, 36, 199, 20);
		getContentPane().add(lblInOrderTo);
		
		JLabel lblMustCloseAnd = new JLabel("Must close and reopen cart");
		lblMustCloseAnd.setBounds(0, 59, 218, 20);
		getContentPane().add(lblMustCloseAnd);
	}
/**
 * Deletes item from cart and removes itemfrom cart display
 * @throws SQLException
 */
private void deleteItem() throws SQLException {
	Cart c = buyer.getCart();
	//Uses DefaultTableModel as a placeholder for table contents
	DefaultTableModel model = (DefaultTableModel) table.getModel();
	table.setModel(model);

	Iterator<Product> cart_remove = c.create_iterator();
	/**Identifies product to remove by iterating through cart iterator 
	 * and checking if product name is equal to the name selected
	 */
	while (cart_remove.hasNext()) {
		Product p = cart_remove.next();
		String selected_name = (String) table.getValueAt(table.getSelectedRow(), 0);
		System.out.println("selected_name "+p.getQuantityInCart());
		if (selected_name.equals(p.getName())) {
			//If  product is found the product is removed from the cart quantity
			p.removeFromCartQuantity();
			//This iterator method removes the product from the iterator and subsequently the list of products in cart
			cart_remove.remove();
			//Removes the selected row from the table model so it doesn't appear to user
			model.removeRow(table.getSelectedRow());
			
		}
	}
	
	
	
	lblTotalCost.setText(String.valueOf(c.getTotalPrice()));
}
/**
 * This is where the transaction happens, both buyer and seller accounts are updated 
 * Also the database is updated with this information, including the quantities of the product available
 * @throws SQLException
 */
private void checkout() throws SQLException {
	
	Cart c = buyer.getCart();
	Iterator<Product> productsInCart = c.create_iterator();
	while (productsInCart.hasNext()) {
	Product p = productsInCart.next();
	System.out.println(p);
	//If the buyer wasn't able to buy the products in the cart
	if (!buyer.makePurchase()) {
		JOptionPane.showMessageDialog(null, "YA BROKE");
		break;
			
	}
		
	
		
	}
	if (buyer.makePurchase()) {
	buyer.updateBalance(c);
	Iterator<Seller> sellers = database.getSellers();
	while (sellers.hasNext()) {
		Seller s = sellers.next();
		s.updateBalance(c);
	
	}
	}

/**After the button is pressed, the table should reset
 * which means clearing the table from view and also emptying the cart attribute of the buyer
 */
emptyCart();
}
/**
 * Displays contents of cart 
 */
private void displayCart() {
	DefaultTableModel dtm = new DefaultTableModel();
	table.setModel(dtm);
	String[] columnNames = {"Name","Description","Price","Sold By"};
	for (String column: columnNames) {
		dtm.addColumn(column.toString());
	}
	Cart c = buyer.getCart();
	/*Every time I want to iterate through the products in the cart I create a new iterator
	 * One iterator is not sufficient because then there would be a concurrent modification exception
	 * if i try to modify the iterator in two loops
	 */
	
	Iterator<Product> cart_items = c.create_iterator();
	//Set the current index to 0 everytime as next() returns last element iterator stopped on
	int rowNo = 0;
	while (cart_items.hasNext()) {
		Product p = cart_items.next();
		Object[] row = {p.getName(),p.getDescription(),p.getPrice(),p.getSoldBy().getUsername()};
		dtm.addRow(row);
		System.out.println("cart items "+p.getName());
		
	}
	scrollPane.setViewportView(table);
	
	lblTotalCost = new JLabel(String.valueOf(c.getTotalPrice()));
	lblTotalCost.setBounds(332, 219, 69, 20);
	getContentPane().add(lblTotalCost);
}
/**
 * Empties content of cart from user view.
 * @throws SQLException
 */
private void emptyCart() throws SQLException {
	Cart c = buyer.getCart();
	DefaultTableModel model = (DefaultTableModel) table.getModel();
	model.setRowCount(0);
	c.emptyCart();
	lblTotalCost.setText(String.valueOf(c.getTotalPrice()));
}
}


