package newbank.server;
//test comment to commit
import java.util.ArrayList;
import java.util.Date;

public class Account {

	private String accountName;
	private double openingBalance;
	private double currentBalance;
	private ArrayList<TopUp> topUps;
//create local variables for account name and opening balance within account
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
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

	//return account balance
	public double getCurrentBalance(){
		return currentBalance;
	}

	//add money to the account
	public void addBalance(double amount){
		this.currentBalance += amount;
	}

	//remove money from the account
	public void removeBalance(double amount){
		this.currentBalance -= amount;
	}

//return account name and opening balance
	public String toString() {
		return (accountName + ": " + currentBalance);
	}

}
