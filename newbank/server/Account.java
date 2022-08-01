package newbank.server;
//test comment to commit
import java.util.ArrayList;
import java.util.Date;

public class Account {

	private String accountName;
	private double openingBalance;
	private double currentBalance;
	private ArrayList<Transaction> transactions;

	//Create local variables for account name and opening balance within account
	//also creates an array of transactions associated with the account where TopUps, Transfers etc. are stored
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
		this.transactions = new ArrayList<Transaction>();
	}

	//return account name
	public String getName() {
		return accountName;
	}

	public void topUpAccount(double amount) {
		TopUp topUp = new TopUp(amount, new Date().toString());
		transactions.add(topUp);
	}

	//return account balance
	public double getCurrentBalance(){
		return currentBalance;
	}

	//add money to the account
	public void addBalance(double amount){
		this.currentBalance += amount;
		Date now = new Date(System.currentTimeMillis());

		// create a transfer object associated with the account to reflect the move into the account
		Transfer transfer = new Transfer(amount, now);
		transactions.add(transfer);
	}

	//remove money from the account
	public void removeBalance(double amount){
		this.currentBalance -= amount;
		Date now = new Date(System.currentTimeMillis());

		// create a transfer object associated with the account to reflect the move out of the account
		Transfer transfer = new Transfer(-amount, now);
		transactions.add(transfer);
	}

	//return account name and opening balance
	public String toString() {
		return (accountName + ": " + currentBalance);
	}

}
