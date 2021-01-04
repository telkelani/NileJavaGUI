package te00141_com1028_part2;

import static org.junit.Assert.*;

import org.com1028.part2.*;
import org.junit.Test;

public class AccountTest {

	@Test
	public void createValidAccount() {
		//Cannot directly instantiate account so this instantiates subclass
		//If the buyer works then the Seller account will work because they are both subclasses of Account
		Buyer b = new Buyer("Tarek","Computer1!","4336737888880352", "04/21","264");
	}
	@Test(expected=IllegalArgumentException.class)
	public void invalidPassword() {
		Buyer b = new Buyer("Tarek","uwu","4009858985342785", "04/21","264");
	}
	@Test(expected=IllegalArgumentException.class)
	public void invalidCCN() {
		Buyer b = new Buyer("Tarek","Computer1!","1111111111111111", "04/21","264");
	
	}
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpDate() {
		Buyer b = new Buyer("Tarek","Computer1!","4009858985342785", "11/15","264");
		Buyer c = new Buyer("Tarek","Computer1!","4009858985342785", "03/19","11");
	}
	@Test(expected=IllegalArgumentException.class)
	public void invalidCVV() {
		Buyer b = new Buyer("Tarek","Computer1!","4009858985342785", "04/15","11");
	}
	@Test(expected=IllegalArgumentException.class)
	public void nullEntry() {
		Buyer b = new Buyer(null,"Computer1!","4009858985342785", "04/15","11");
		Buyer c = new Buyer("Tarek",null,"4009858985342785", "04/15","11");
		Buyer d = new Buyer("Tarek","Computer1!",null, "04/15","11");
		Buyer e = new Buyer("Tarek","Computer1!","4009858985342785", null,"11");
		Buyer f = new Buyer("Tarek","Computer1!","4009858985342785", "04/15",null);
	}
	
}
