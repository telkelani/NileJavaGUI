package org.com1028.part2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;



import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Panel;

public class MainProgram extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JPanel LoginPanel;
	private JPanel BuyerControlPanel;
	private Buyer existing_buyer;
	private JTable market_table;
	private static SQLDatabase data = SQLDatabase.getInstance();
	private JScrollPane scrollPane;
	private Product productToAdd;
	private JPanel CardPanel;
	private JPanel SellerControlPanel;
	private Seller existing_seller;
	private JTextField txtPrice;
	private JTextArea txtDescription;
	private JPanel CreateProductPanel;
	private JTextField txtProductName;
	private JTextField txtQuantity;
	private JPanel ViewProductsPanel;
	private JScrollPane scrollPane_1;
	private JTable table;
	private JTable AdminProductTable;
	private JTable AdminSellerTable;
	private JPanel AdminPanel;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_2;
	private JTable table_1;
	private JTable SellerProductTable;
	private JTable table_2;
	private JTable table_3;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram frame = new MainProgram();
					data.getConnection().close();
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
	public MainProgram() throws SQLException {
		/*data.populateExisiting_Accs();
		data.ReadFromMarketplaceTable();*/
		
		setTitle("Nile Marketplace");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Allows only this frame to close when close button is clicked
		setBounds(100, 100, 608, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		CardPanel = new JPanel();
		contentPane.add(CardPanel);
		CardPanel.setLayout(new CardLayout(0, 0));
		
		LoginPanel = new JPanel();
		CardPanel.add(LoginPanel, "name_220787083979100");
		LoginPanel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Rockwell Condensed", Font.BOLD, 16));
		lblUsername.setBounds(69, 87, 67, 20);
		LoginPanel.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(135, 84, 146, 26);
		LoginPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Rockwell Condensed", Font.BOLD, 16));
		lblPassword.setBounds(68, 114, 67, 20);
		LoginPanel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(135, 111, 146, 26);
		LoginPanel.add(passwordField);
		
		JLabel lblBuyerLogin = new JLabel("Login");
		lblBuyerLogin.setBounds(135, 51, 115, 20);
		LoginPanel.add(lblBuyerLogin);
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuScreen.main(null);
					dispose();
					setTitle("Main Menu");
			}
		});
		btnBackToMain.setBounds(0, 0, 175, 29);
		LoginPanel.add(btnBackToMain);
		/**
		 * Logging in: retrieves username and password from text boxes and compares the result
		 *  with any of the existing sellers in the object list
		 *  
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setBounds(135, 143, 80, 29);
		LoginPanel.add(btnLogin);

		
		BuyerControlPanel = new JPanel();
		CardPanel.add(BuyerControlPanel, "name_234259429562700");
		BuyerControlPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 139, 31);
		BuyerControlPanel.add(menuBar);
		
		JMenuItem mntmViewAccount = new JMenuItem("View Account");
		mntmViewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewAccount();	
			}
		});
		menuBar.add(mntmViewAccount);
		
		JMenuItem mntmCart = new JMenuItem("Cart");
		mntmCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//0 quantity by default
				viewCart();
			
			}
		});
		menuBar.add(mntmCart);
		
		JLabel lblNewLabel = new JLabel("Products Available");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 20));
		lblNewLabel.setBounds(166, 50, 166, 47);
		BuyerControlPanel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 99, 576, 340);
		BuyerControlPanel.add(scrollPane);
		

		market_table = new JTable();
		JButton btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					addtocart();
			}
		});


		

		btnAddToCart.setBounds(0, 455, 139, 29);
		BuyerControlPanel.add(btnAddToCart);
		
		JButton btnBack_2 = new JButton("Back ");
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MainMenuScreen.main(null);
			}
		});
		btnBack_2.setBounds(461, 2, 115, 29);
		BuyerControlPanel.add(btnBack_2);
		
		JButton btnDisplayTable = new JButton("Display Table/Refresh");
		btnDisplayTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayMarket();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDisplayTable.setBounds(347, 60, 197, 31);
		BuyerControlPanel.add(btnDisplayTable);
		
		JLabel lblWhenAddingTo = new JLabel("When adding to cart must press refresh after");
		lblWhenAddingTo.setBounds(10, 34, 316, 20);
		BuyerControlPanel.add(lblWhenAddingTo);
		
		SellerControlPanel = new JPanel();
		SellerControlPanel.setLayout(null);
		CardPanel.add(SellerControlPanel, "name_244246512914100");
		
		JLabel lblSellerView = new JLabel("Seller View");
		lblSellerView.setBounds(153, 27, 94, 20);
		SellerControlPanel.add(lblSellerView);
		
		JButton btnCreateProduct = new JButton("Create Product");
		btnCreateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePanel(CreateProductPanel);
			}
		});
		btnCreateProduct.setBounds(15, 52, 154, 29);
		SellerControlPanel.add(btnCreateProduct);
		
		JButton btnViewproducts = new JButton("View Products");
		btnViewproducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					viewProducts();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnViewproducts.setBounds(209, 52, 147, 29);
		SellerControlPanel.add(btnViewproducts);
		
		JButton btnBackToMenu = new JButton("Back To Main Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MainMenuScreen.main(null);
			}
		});
		btnBackToMenu.setBounds(0, 0, 115, 29);
		SellerControlPanel.add(btnBackToMenu);
		
		JButton btnViewaccountpage = new JButton("ViewAccountPage");
		btnViewaccountpage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ViewAccountPage viewaccount = new ViewAccountPage(existing_seller);
					viewaccount.main(null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnViewaccountpage.setBounds(155, 0, 188, 29);
		SellerControlPanel.add(btnViewaccountpage);
		
		CreateProductPanel = new JPanel();
		CreateProductPanel.setLayout(null);
		CardPanel.add(CreateProductPanel, "name_245808952739400");
		
		JLabel label = new JLabel("Create Product Panel");
		label.setBounds(113, 16, 179, 20);
		CreateProductPanel.add(label);
		
		JLabel label_1 = new JLabel("Product Name");
		label_1.setBounds(15, 66, 113, 20);
		CreateProductPanel.add(label_1);
		
		txtProductName = new JTextField();
		txtProductName.setColumns(10);
		txtProductName.setBounds(123, 66, 146, 26);
		CreateProductPanel.add(txtProductName);
		
		JLabel label_2 = new JLabel("Description");
		label_2.setBounds(15, 102, 101, 20);
		CreateProductPanel.add(label_2);
		
		txtDescription = new JTextArea();
		txtDescription.setBounds(123, 102, 146, 57);
		CreateProductPanel.add(txtDescription);
		
		JLabel label_3 = new JLabel("Price");
		label_3.setBounds(15, 176, 69, 20);
		CreateProductPanel.add(label_3);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(123, 173, 146, 26);
		CreateProductPanel.add(txtPrice);
		
		JLabel label_4 = new JLabel("\u00A3");
		label_4.setBounds(107, 176, 69, 20);
		CreateProductPanel.add(label_4);
		
		JLabel label_5 = new JLabel("Quantity");
		label_5.setBounds(15, 212, 69, 20);
		CreateProductPanel.add(label_5);
		
		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(123, 209, 36, 26);
		CreateProductPanel.add(txtQuantity);
		
		JButton btnCreateProduct_1 = new JButton("Create Product");
		btnCreateProduct_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createProduct();
			}
		});
		btnCreateProduct_1.setBounds(247, 208, 166, 29);
		CreateProductPanel.add(btnCreateProduct_1);
		
		JButton button_1 = new JButton("Back");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//This is needed because the dtm is not defined yet for the 
				try {
					dtm.setRowCount(0);
					changePanel(SellerControlPanel);
				}
				catch (NullPointerException e) {
					changePanel(SellerControlPanel);
				}
				
				
			}
		});
		button_1.setBounds(1, 0, 83, 29);
		CreateProductPanel.add(button_1);
		
		ViewProductsPanel = new JPanel();
		ViewProductsPanel.setLayout(null);
		CardPanel.add(ViewProductsPanel, "name_246080346472500");
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 46, 428, 145);
		ViewProductsPanel.add(scrollPane_1);
		
	
		
	
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePanel(SellerControlPanel);
			}
		});
		
		SellerProductTable = new JTable();
		SellerProductTable.setBounds(0, 0, 1, 1);
		ViewProductsPanel.add(SellerProductTable);
		btnBack.setBounds(0, 16, 115, 29);
		ViewProductsPanel.add(btnBack);
		
		JButton btnPlaceProduct = new JButton("Place product on market");
		btnPlaceProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				placeproductonmarket();
			}
		});
		btnPlaceProduct.setBounds(202, 215, 226, 29);
		ViewProductsPanel.add(btnPlaceProduct);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					addStock();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(0, 215, 115, 29);
		ViewProductsPanel.add(btnAdd);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					viewProducts();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnRefresh.setBounds(320, 16, 115, 29);
		ViewProductsPanel.add(btnRefresh);
		
		AdminPanel = new JPanel();
		AdminPanel.setLayout(null);
		CardPanel.add(AdminPanel, "name_248934997129000");
		
		JLabel label_6 = new JLabel("Admin View");
		label_6.setBounds(258, 16, 101, 29);
		AdminPanel.add(label_6);
		
		JLabel label_7 = new JLabel("All Products");
		label_7.setBounds(15, 79, 101, 29);
		AdminPanel.add(label_7);
		
		JLabel label_8 = new JLabel("All Sellers");
		label_8.setBounds(455, 81, 88, 25);
		AdminPanel.add(label_8);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 124, 284, 253);
		AdminPanel.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		
		
		
		
		JButton btnBanProduct = new JButton("Ban Product");
		btnBanProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				banProduct();
				
			}
		});
		btnBanProduct.setBounds(124, 79, 140, 29);
		AdminPanel.add(btnBanProduct);
		
		JButton btnBanSeller = new JButton("Ban Seller");
		btnBanSeller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BanSeller();
			}
		});
		btnBanSeller.setBounds(307, 79, 115, 29);
		AdminPanel.add(btnBanSeller);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(284, 124, 292, 253);
		AdminPanel.add(scrollPane_3);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MainMenuScreen.main(null);
			}
		});
		btnBack_1.setBounds(1, 0, 115, 29);
		AdminPanel.add(btnBack_1);
		
		
		
	
	}
		


		
		
	
		
private void setBuyer(Buyer b) {
	existing_buyer = b;
}
/**
 * When the add to cart button is clicked, this function will retrieve the selected value and update the cart accordingly
 * 
 */
private void addtocart() {
	//Every time the database is used, it must be opened and closed otherwise the database locks and program crashes
	data.open();
	/**Retrieving the name of the product and the user it is sold by of the selected row**/
	String seller_username = (String) table_3.getValueAt(table_3.getSelectedRow(), 5);
	String product_name = (String) table_3.getValueAt(table_3.getSelectedRow(), 0);
	
	/**The reason why the iterator has to be created as a local variable and not a field 
	 * is because if not the buyer and seller iterator will be running on the same thread 
	 * which will cause concurrentmodificationexception
	 */
	Iterator<Seller> slrs = data.getSellers();
	/**Iterating through the list of sellers and the list of products of each seller
	 * to locate the product object that matches the product that has just been selected
	 */
	while (slrs.hasNext()) {
		Seller current_seller = slrs.next();
		if (seller_username.equals(current_seller.getUsername())) {
			   Iterator<Product> it = current_seller.getProductsForSale();
			   while (it.hasNext()) {
				   Product p = it.next();
				   if (p.getName().equals(product_name)) {
		                productToAdd = p;
		            }
			   }

			    }
			}		
	//Retrieve the cart of the logged_in buyer
	Cart cart = existing_buyer.getCart();
	
	try {
	//This prevents the user from adding more products in the cart then there are products available
	if (productToAdd.getQuantityInCart()<productToAdd.getQuantity()) {
	cart.addToCart(productToAdd);
	}
	else {
		JOptionPane.showMessageDialog(null, "Cannot add more");
	}
	
	}
	catch (Exception h) {
		h.printStackTrace();
		JOptionPane.showMessageDialog(null, h.getMessage());
	}
	//Must close the database connection after the method is finished 
	finally {
		try {
			data.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/**
 * Displays the marketplace table in the user interface which allows buyer to select products to add to their cart
 * @throws SQLException
 */
private void displayMarket() throws SQLException {
	data.open();
	table_3 = new JTable();
	dtm = (DefaultTableModel) table_3.getModel();
	scrollPane.setViewportView(table_3);
	dtm.setRowCount(0); //Every time this function is run the table is reset so the database doesn't add duplicate data to table
	
	
	/**
	 * Deletes any product that has a quantity of 0.
	 */
	PreparedStatement delete = data.getConnection().prepareStatement("delete from product where quantity = 0");
	PreparedStatement deletefrommarket = data.getConnection().prepareStatement("delete from marketplace where quantity = 0");
	delete.execute();
	deletefrommarket.execute();
	
	
	String[] columnNames = {"Name","Description","Price","Quantity","InCart","Sold By"};
	for (String column: columnNames) {
		dtm.addColumn(column.toString());
	}
		
	Iterator<Seller> sellers = data.getSellers();
	while (sellers.hasNext()) {
		Seller s = sellers.next();
		
		Iterator<Product> forSale = s.getProductsForSale();
		int incart = 0;
		System.out.println(incart);
		Object[] row =  new Object[6];
		while (forSale.hasNext()) {
			Product p = forSale.next();
			incart = p.getQuantityInCart();
			/** To get one product select * where the name is the name of the product**/
			Statement querymarket = data.getConnection().createStatement();
			String sql = String.format("select * from marketplace where name='%s' and quantity > 0",p.getName());
			ResultSet whatsinmarket = querymarket.executeQuery(sql);
			/**This way the resultset will only have one row and that row can be added onto the table
			 * therefore the incart value will correspond to the desired value
			 */
			
			while (whatsinmarket.next()) {
				//System.out.println("supposed to work");
				/**insert the in cart value as an attribute of product so the number of products in cart resets
				 * every time the program is restarted 
				 */
				
				row[0] = whatsinmarket.getString("Name");
				row[1] = whatsinmarket.getString("Description");
				row[2] = whatsinmarket.getDouble("Price");
				row[3] = whatsinmarket.getInt("Quantity");
				row[4] = incart;
				row[5] = whatsinmarket.getString("sold_by");
				dtm.addRow(row);
				
				
			}
				
			
			whatsinmarket.close();
			querymarket.close();
		}

		}
	
	data.getConnection().close();
}



private void viewCart() {
	CartScreen cart = new CartScreen(existing_buyer);
	cart.setVisible(true);
}
/**
 * Opens a new window displaying account details of the buyer
 */
private void viewAccount() {
	ViewAccountPage viewaccount;
	try {
		viewaccount = new ViewAccountPage(existing_buyer);
		viewaccount.setVisible(true);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * This allows admin, seller and buyer to login. 
 * 
 */
private void login() {
	try {
		Connection con = data.getConnection();
		String username = txtUsername.getText();
		String password = new String(passwordField.getPassword());
		// TODO Auto-generated method stub
		/**To check if the account exists, must retrieve the buyers and sellers from database*
		 * Iterates through buyer list first to see if the username is equal to the username of any existing buyer
		 * Retrieves both lists from an iterator created by SQLDatabase
		 * */
		Iterator<Buyer> bIterator = data.getBuyers();
		Iterator<Seller> sIterator = data.getSellers();
		//Admin is not part of database so just needs this if statement to login as there is only one admin account
		boolean adminloggedin = false;
		
		if (username.equals("Admin") && password.equals("cairo")) {
			adminloggedin = true;
		}
	
		/**Iterates through buyers list to see if the buyers in the list are equivalent to the username inputted*
		 * If it is, the existing_buyer field is updated. This is how the program accesses functionality to the
		 * Buyer class*/
		boolean buyerloggedin = false;
		while (bIterator.hasNext()) {
			
			Buyer currentBuyer = bIterator.next();
				if (currentBuyer.getUsername().equals(username) && currentBuyer.getPassword().equals(password)) {	
						existing_buyer = currentBuyer;
						System.out.println(existing_buyer);
						buyerloggedin = true;
					
				}
					
				}

		System.out.println("existing buyer in login button:+ "+ existing_buyer);
		/**Iterates through sellers list to see if the sellers in the list are equivalent to the username inputted
		 * If it is, the existing_seller field is updated. This is how the program accesses functionality to the
		 * Seller class*/
		boolean sellerloggedin = false;
		while (sIterator.hasNext()) {
		Seller currentSeller = sIterator.next();
		if (currentSeller.getUsername().equals(username) && currentSeller.getPassword().equals(password)) {
			existing_seller = currentSeller;
			sellerloggedin = true;
		}
		}
		
		//Change JPanel respectively based on account
		if (adminloggedin) {
			changePanel(AdminPanel); 
			displayTables();
		}
		if (buyerloggedin) {
			changePanel(BuyerControlPanel);
		}
		if (sellerloggedin) {
			changePanel(SellerControlPanel);
		}
		if (adminloggedin==false&&buyerloggedin==false&&sellerloggedin==false) {
			JOptionPane.showMessageDialog(null, "Couldn't log in");
		}
		con.close();
			
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
}
/**
 * Changes the panel of the frame. Instead of disposing the frame every time and having multiple frame classes,
 * panels are switched in the same frame using CardLayout. This makes most of the main parts of the program function in just one frame
 * which is faster than disposing frames everytime the user clicks a button
 * @param panel
 * The panel for the frame to display
 */
private void changePanel(JPanel panel) {
	CardPanel.removeAll();
	CardPanel.add(panel);
	CardPanel.repaint();
	CardPanel.revalidate();
	
}
/**
 * Executes this method when the seller clicks createProduct method
 * 
 */
private void createProduct() {
	try {
		data.open();
	/**Retrieving values from user input**/
	String name = txtProductName.getText();
	String desc = txtDescription.getText();
	double price = Double.parseDouble(txtPrice.getText());
	int quantity = Integer.parseInt(txtQuantity.getText());
	/**Validating if the input was not empty**/
	if (!name.equals("") || !desc.equals("")) {
	//Calls createProduct method of seller class so seller object has list of products
	existing_seller.createProduct(name,desc,price,quantity);
	/**Whenever account object is updated, the database must also be updated
	 * as database saves the attributes of the object 
	 * so when the program is run again, the seller can log in to their account and have all their products 
	 */
	//Whenever some sql code is about to be executed, a Statement object must be instantiated
	Statement insertproduct = data.getConnection().createStatement();
	insertproduct.executeUpdate(String.format("INSERT INTO `product`(`Name`, `Description`, `Price`, `Quantity`,`sold_by`) VALUES ('%s','%s',%f,%d,'%s')",name,desc,price,quantity,existing_seller.getUsername()));
	JOptionPane.showMessageDialog(null, "Product has been created");
	data.getConnection().close();
	}
	else {
		throw new NumberFormatException();
	}
	}
	catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Product already exists");
	}
	 catch (NumberFormatException h) {
		 JOptionPane.showMessageDialog(null, "Must input something");
	 }
	

		
	}
	
/**
 * When seller clicks place product on market this method is executed
 */
private void placeproductonmarket() {
	System.out.println("works");
	data.open();
	Iterator<Product> placeproductiterator = existing_seller.getInventory();
	while (placeproductiterator.hasNext()){
		Product p = placeproductiterator.next();
		//Add product to arraylist and also to database
		if (p.getName().equals(SellerProductTable.getValueAt(SellerProductTable.getSelectedRow(),0))) {
			existing_seller.placeProductOnSale(p);
			System.out.println("products fro sale "+existing_seller.getProductsForSale());
			Statement insertProductsIntoMarket;
			try {
				System.out.println("products");
				insertProductsIntoMarket = data.getConnection().createStatement();
				String InsertProductInMarketTable = String.format("INSERT INTO `marketplace` (`Name`, `Description`, `Price`, `Quantity`,`InCart`, `sold_by`) VALUES ('%s','%s',%f,%d,0,'%s')",p.getName(),p.getDescription(),p.getPrice(),p.getQuantity(),p.getSoldBy().getUsername());
				insertProductsIntoMarket.execute(InsertProductInMarketTable);
				data.getConnection().close();
				JOptionPane.showMessageDialog(null, "Product placed in market");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
				JOptionPane.showMessageDialog(null, "product already exists in market");
			}
		
			
		
		}
	
}
	;
}
/**
 * Retrieves data from product table about the products of the logged in seller
 * Places in JTable for seller to see their products as if they had access to the database
 * @throws SQLException
 */
private void viewProducts() throws SQLException {
	changePanel(ViewProductsPanel);
	data.open();
	try {
		//PreparedStatement is just a different way of initializing some sql code
		PreparedStatement queryproduct = data.getConnection().prepareStatement(String.format("select * from product where sold_by='%s'",existing_seller.getUsername()));
		//The results of an sql query is always stored in a ResultSet object
		ResultSet rs = queryproduct.executeQuery();
		DefaultTableModel dtm = new DefaultTableModel();
		SellerProductTable.setModel(dtm);
		
		String[] columnNames = {
	            "Name",
	            "Description",
	            "Price",
	            "Quantity",
	            "Sold By"};
		for (String column: columnNames) {
			dtm.addColumn(column.toString());
		}
		while (rs.next()) {
			System.out.println("getName "+rs.getString("Name"));
			Object[] row = {rs.getString("Name"),rs.getString("Description"),rs.getDouble("Price"),rs.getInt("Quantity"),rs.getString("sold_by")};
			System.out.println(row);
			dtm.addRow(row);
			}
	data.getConnection().close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	scrollPane_1.setViewportView(SellerProductTable);


}

private void banProduct() {
	

	
	try {
		DefaultTableModel dtm = (DefaultTableModel) table_2.getModel();
		String selected_name = (String) table_2.getValueAt(table_2.getSelectedRow(), 0);
		String selected_seller = (String) table_2.getValueAt(table_2.getSelectedRow(), 4);
		int rowcount = dtm.getRowCount();
		System.out.println(rowcount);
		
		/*
		 * Delete from database
		 */
		try {
			data.open();
			Statement deleteproduct = data.getConnection().createStatement();
			deleteproduct.execute(String.format("DELETE FROM PRODUCT WHERE NAME='%s'",selected_name));
			Statement searchmarket = data.getConnection().createStatement();
			ResultSet rs = searchmarket.executeQuery(String.format("select name from marketplace where name='%s'",selected_name));
			
			
			while (rs.next()) {
				deleteproduct.execute(String.format("delete from marketplace where name='%s'",selected_name));
			}
			
			System.out.println("rowcount now "+rowcount);
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * Delete from inventory of seller
		 */
		Iterator<Seller> it = data.getSellers();
		while (it.hasNext()) {
			Seller s = it.next();
			if (s.getUsername().equals(selected_seller)) {
				Iterator<Product> s_products = s.getInventory();
				while (s_products.hasNext()) {
					Product p = s_products.next();
					if ((p.getName().equals(selected_name))){
						s_products.remove();
						Statement sta = data.getConnection().createStatement();
						sta.execute(String.format("delete from product where name='%s'",selected_name));
						
					}
					
				}
			}
		}
		dtm.setRowCount(rowcount-1);
		data.getConnection().close();
		}
	
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No products left");
		}
	
}
/**
 * Admin method to remove the seller from the database 
 * Retrieves the selected row of the JTable, extracting the value of the first column (which is the name)
 * The name is retrieved as this is the primary key for this table 
 * 
 */
private void BanSeller() {
	try {
		data.open();
	DefaultTableModel dtm_seller = (DefaultTableModel) AdminSellerTable.getModel();
	DefaultTableModel dtm_product = (DefaultTableModel) table_2.getModel();
	Iterator<Seller> it = data.getSellers();
	System.out.println(it.hasNext());
	int row_count = dtm_seller.getRowCount();
	//This line retrieves the selected row but the second argument specifies the value of the column it wants
	//The column wanted is 0 which is the name of the product (the primary key of database)
	String selected = (String) AdminSellerTable.getValueAt(AdminSellerTable.getSelectedRow(), 0);
	while (it.hasNext()) {
		Seller s = it.next();
		
		if (s.getUsername().equals(selected)) {
			
			try {
				
				//Deletes from database so when user runs the program again, the user is gone for good and doesn't reappear
				Statement delete = data.getConnection().createStatement();
				delete.execute(String.format("DELETE FROM account WHERE username='%s'",s.getUsername()));
				Iterator<Product> productstodelete =  s.getInventory();
				delete.close();
				Statement deleteproducts = data.getConnection().createStatement();
				Statement deletefrommarket = data.getConnection().createStatement();
				while (productstodelete.hasNext()) {
					System.out.println(productstodelete.next().getName());
					/**When the seller is removed, the seller will immediately leave view 
					 * however products will stay until admin closes window and reopens
					 */
					deletefrommarket.execute(String.format("DELETE FROM marketplace WHERE sold_by='%s'",s.getUsername()));
					deleteproducts.execute(String.format("DELETE FROM product WHERE sold_by='%s'",s.getUsername()));
					deleteproducts.close();
					
					
				}
				
				it.remove(); //The remove method from Iterator class removes it.next() (which is the product)
				
				dtm_seller.removeRow(AdminSellerTable.getSelectedRow());
				//Find out which products belong to the banned seller so that those products can be banned
				ArrayList<Integer> sellers_product_row_nos = new ArrayList<Integer>();
				//Adds all the rows that are sold by the selected person to ban
				for (int i = 0; i < dtm_product.getRowCount();i++) {
					if (table_2.getValueAt(i, 4).equals(selected)) {
						sellers_product_row_nos.add(i);
					}
				}
				//Remove all the rows whose indexes are stored in this array list
				for (int num: sellers_product_row_nos) {
					dtm_product.removeRow(num);
				}
				
				
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
					
					
				}
				
			}
		
	
		}
	data.getConnection().close();
	}
	catch (Exception e) {
		//An array out of bounds exception would be thrown if there are no rows in the table to select
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "No sellers Left");
	}
	
	
}
/**
 * Displays all products and all sellers for admin panel
 * All data is saved in the database which is accessed by the SQLDatbase class
 * Uses DefaultTableModel class as a container to place the data in the JTable
 * Updates this container with column names and rows
 * Column names are fixed so it is a String[] array
 * Add rows with the data that is returned from a query of all products and sellers
 * 
 * @throws SQLException
 * Using SQL to retrieve data from database so this exception is required
 */
private void displayTables() throws SQLException {
	data.open();
	AdminSellerTable = new JTable();
	scrollPane_3.setViewportView(AdminSellerTable);

	DefaultTableModel dtm_seller = (DefaultTableModel) AdminSellerTable.getModel();
	String[] SellercolumnNames = {"Username","Password","Balance"};
	for (String column: SellercolumnNames) {
		dtm_seller.addColumn(column.toString());
	}
	
	Statement retrievesellers = data.getConnection().createStatement();
	ResultSet SellerSet = retrievesellers.executeQuery("select username,password,balance from account where type='Seller'");
	while (SellerSet.next()) {
		Object[] row = {SellerSet.getString("username"),SellerSet.getString("password"),SellerSet.getDouble("balance")};
		dtm_seller.addRow(row);
	}
	
	

	DefaultTableModel dtm = (DefaultTableModel) table_2.getModel();
	String[] columnNames = {"Name","Description","Price","Quantity","Sold By"};
	for (String column: columnNames) {
		dtm.addColumn(column.toString());
	}
	
	Statement retrieveproducts = data.getConnection().createStatement();
	ResultSet rs = retrieveproducts.executeQuery("select * from product");
	while (rs.next()) {
		Object[] row = {rs.getString("Name"),rs.getString("Description"),rs.getDouble("Price"),rs.getInt("Quantity"),rs.getString("sold_by")};
		dtm.addRow(row);
	}
	data.getConnection().close();
}
/**
 * This method adds a product in the sellers inventory one at a time
 * 
 * @throws SQLException
 */
private void addStock() throws SQLException {
	//the following string is the string returned of the first column of the row that the user selected
	String selected_product = (String) SellerProductTable.getValueAt(SellerProductTable.getSelectedRow(), 0);
	//Iterator is used to iterate through inventory so cannot retrieve certain index of elements in inventory
	Iterator<Product> it = existing_seller.getInventory();
	while (it.hasNext()) {
		Product p = it.next();
		//If the string of the selected product is the same as the name of the current product add 1 to the quantity of product
		if (selected_product.equals(p.getName())) {
			p.addToQuantity(1);
		}
	}
}
	}



