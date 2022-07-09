package newbank.server;
//test comment to commit
import java.util.ArrayList;
import java.util.Date;

public class Account {

	private String accountName;
	private double openingBalance;
	private ArrayList<TopUp> topUps;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.topUps = new ArrayList<TopUp>();
	}

	public String getName() {
		return accountName;
	}

	public void topUpAccount(double amount) {
		TopUp topUp = new TopUp(amount, new Date().toString());
		topUps.add(topUp);
	}

	public String toString() {
		return (accountName + ": " + openingBalance);
	}

}
