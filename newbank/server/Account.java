package newbank.server;
//test comment to commit
import java.util.ArrayList;
import java.util.Date;

public class Account {

	private String accountName;
	private double openingBalance;
	private double currentBalance;
	private ArrayList<Transaction> transactions;
	private ArrayList<TopUp> pendingTopUps;

	//Create local variables for account name and opening balance within account
	//also creates an array of transactions associated with the account where TopUps, Transfers etc. are stored
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
		transactions = new ArrayList<Transaction>();
		pendingTopUps = new ArrayList<TopUp>();
	}

	//return account name
	public String getName() {
		return accountName;
	}

	public void topUpAccount(double amount) {
		TopUp topUp = new TopUp(amount, new Date().toString());
		pendingTopUps.add(topUp);
	}

	public ArrayList<TopUp> pendingTopUps() {
		return pendingTopUps;
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
		Transfer transfer = new Transfer(amount, now, getName());
		transactions.add(transfer);
	}

	//remove money from the account
	public void removeBalance(double amount){
		this.currentBalance -= amount;
		Date now = new Date(System.currentTimeMillis());

		// create a transfer object associated with the account to reflect the move out of the account
		Transfer transfer = new Transfer(-amount, now, getName());
		transactions.add(transfer);
	}

	//return account name and opening balance
	public String toString() {
		return (accountName + ": " + currentBalance);
	}

	public void removePendingTopUp(Transaction t) {
		pendingTopUps.remove(t);
	}

	public void addtoTransactions(Transaction t) {
		transactions.add(t);
	}

	public void clearPendingTopUps() {
		pendingTopUps.clear();
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
}
