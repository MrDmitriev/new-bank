package newbank.server;
//test comment to commit
import java.util.ArrayList;
import java.util.Date;

public class Account {

	private String accountName;
	private double openingBalance;
	private ArrayList<TopUp> topUps;
//create local variables for account name and opening balance within account
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.topUps = new ArrayList<TopUp>();
	}

	//return account name
	public String getName() {
		return accountName;
	}

	public void topUpAccount(double amount) {
		TopUp topUp = new TopUp(amount, new Date().toString());
		topUps.add(topUp);
	}
//return account name and opening balance
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

}
