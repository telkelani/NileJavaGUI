/**
 * 
 */
package org.com1028.part2;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author tarek
 *
 */
public abstract class Account {
	private String username = null;
	private String password = null;
	private String CreditCardNumber; 
	private String ExpiryDate = null;
	private String CVV = "000";
	protected double Balance = 0;
	
	private boolean validateCCN(String CCN) {
		/**
		 * Luhn algorithm to check valid credit card numbers
		 */
	
		boolean outcome = false;
		//This if statement is necessary because if this is left out, the fixed array will just fill up with 0s and will trick algorithm into thinking it is a valid number
		if (CCN.length()!=16) {
			
		}
		else {
		String[] CCN_iterable = new String[16];
		CCN_iterable = CCN.split("");
		
		
		int[] digit_weight_array = new int[16];
		/**Weights**/
		for (int i = 0; i < CCN_iterable.length;i++) {
			
			int int_N = Integer.parseInt(CCN_iterable[i]);
			if (i%2 == 0) {
				digit_weight_array[i] = 2;
			}
			else {
				digit_weight_array[i] = 1;
			}
		}
		/**Taking care of 2 digit numbers**/
		int[] products = new int[16];
		for (int j = 0; j < digit_weight_array.length;j++) {
			int CCN_int = Integer.parseInt(CCN_iterable[j]);
			int result = CCN_int*digit_weight_array[j];
			
			if (result > 9) {
				String result_str = String.valueOf(result);
				result = Character.getNumericValue(result_str.charAt(0))+Character.getNumericValue(result_str.charAt(1));
				//Adding the two digits together
			}
			products[j] = result;
		}
		/**Checking if sum is mod 10**/
		int total_sum = 0;
		for (int num: products) {
			total_sum+=num;
		}
		if (total_sum%10 == 0) {
			outcome = true;
		}
		}
		return outcome;
	}


/**c
 * Checks if the password is valid. 
 * @param password
 * @return
 */
private boolean validateCredentials(String password) {
	boolean validPassword = false;
	boolean numberCheck = false;
	boolean capitalCheck = false;
	boolean symbolCheck = false;
	
	for (int i = 0; i< password.length();i++) {
		char c = password.charAt(i);
		int asciival = (int) c;
		if ((asciival>=33 && asciival<=47) || (asciival>=58 && asciival<=64) || (asciival>=91 && asciival<=96) || (asciival>=123 && asciival<=126)) {
			symbolCheck = true;
		}
		if (asciival>=65 && asciival<=90) {
			capitalCheck = true;
		}
		if (asciival>=48 && asciival<=57) {
			numberCheck = true;
		}
	}
	if (numberCheck&&capitalCheck&&symbolCheck&&password.length()>=8) {
		validPassword = true;
	}
	
	return validPassword;
	
}
/**
 * Checks if the credit card has expired
 * @param date
 * The date the user inputted
 * @return
 * whether the card has expired or not
 */
private boolean checkifCardExpired(String date) {
	boolean expired = false;
	LocalDateTime now = LocalDateTime.now(); //gets the time right noq
	String[] exp_date = date.split("/"); //The format of credit card dates are MM/YY so the / will split the numbers
	int exp_year = Integer.parseInt(exp_date[1]); //The year is the second index of the split list and the month is the first
	int exp_month = Integer.parseInt(exp_date[0]);
	int current_month = now.getMonthValue(); //Gets the current month in real time
	int current_year = now.getYear()-2000; //Gets the current year in real time (returns 2019 but only want the 19 part so subtract 2000)
	if (exp_year < current_year) { //If the expiry year is less than the current year than it has expired
		expired = true;
	}
	//On the same year if the expiry month is later than the current month then it is expired
	//Without this, if the date is 05/19 then 06/19 will not be an expired credit card
	if (exp_month > current_month && exp_year == current_year) { 
		expired = true;
	}
	return expired;
	
	
	
}

public void setBalance(double balance) {
	Balance = balance;
}





public Account(String username, String password, String ccn, String exp_date, String CVV) {
	super();
	/**
	 * Validation of account including credit card details is carried out in constructor
	 */
	if (username==null||password==null||ccn==null||exp_date==null || CVV== null) {
		throw new IllegalArgumentException("INPUT something");
	}
		
	
	
	if (!this.validateCredentials(password)) {
		//System.out.println();
		throw new IllegalArgumentException("PASSWORD NOT VALID");
	}
	if (!this.validateCCN(ccn)) {
		//System.out.println(); 
		throw new IllegalArgumentException("CCN NOT VALID");
	}
	if (this.checkifCardExpired(exp_date)) {
		//System.out.println();
		System.out.println(exp_date);
		throw new IllegalArgumentException("EXP NOT VALID");
	}
	
	if (CVV.length() != 3) {
		System.out.println(CVV.length());
		throw new IllegalArgumentException("CVV NOT VALID");
	}
	else {
		this.username = username;
		this.password = password;
		this.CreditCardNumber = ccn;
		this.ExpiryDate = exp_date;
		this.CVV = CVV;
	}

	

}

public String getCCN() {
	return this.CreditCardNumber;
}


public String getUsername() {
	return username;
}



public String getPassword() {
	return password;
}



public String getCreditCardNumber() {
	return CreditCardNumber;
}



public String getExpiryDate() {
	return ExpiryDate;
}



public String getCVV() {
	return CVV;
}



public double getBalance() {
	return Balance;
}

public abstract void updateBalance(Cart Cart);



}

