package newbank.server;
//test comment to commit
import java.time.LocalDate;
import java.util.ArrayList;

public class Account {

	private String accountName;
	private double openingBalance;
	private double currentBalance;
	private ArrayList<Transaction> transactions;
	private ArrayList<TopUp> pendingTopUps;
	private ArrayList<Payable> payables;
	private ArrayList<DirectDebit> directdebits;

	//Create local variables for account name and opening balance within account
	//also creates an array of transactions associated with the account where TopUps, Transfers etc. are stored
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
		transactions = new ArrayList<Transaction>();
		pendingTopUps = new ArrayList<TopUp>();
		payables = new ArrayList<Payable>();
		directdebits = new ArrayList<DirectDebit>();
	}

	//return account name
	public String getName() {
		return accountName;
	}

	public void topUpAccount(double amount) {
		TopUp topUp = new TopUp(amount, LocalDate.now().toString());
		pendingTopUps.add(topUp);
	}

	public ArrayList<TopUp> pendingTopUps() {
		return pendingTopUps;
	}

	//return account balance
	public double getCurrentBalance(){
		return currentBalance;
	}

	//add or remove money to/from the account
	//positive input adds money, negative input removes it
	public void addRemoveBalance(double amount){
		this.currentBalance += amount;

		// create a transfer object associated with the account to reflect the move into the account
		Transfer transfer = new Transfer(amount, getName());
		transactions.add(transfer);
	}

	public void makeReceivePayment(double amount, Customer toFromCustomer){
		this.currentBalance += amount;
		toFromCustomer.getCustomerName();

		// create a payment object associated with the account to reflect the payment sent/received
		Payment payment = new Payment(amount, getName(), toFromCustomer.getCustomerName());
		transactions.add(payment);
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
	
	public void changeName(String newAccountName){
		accountName = newAccountName;
	}

	public ArrayList<Transaction> getTransactions() {return transactions;}

	public ArrayList<DirectDebit> getDirectDebits(){return directdebits;}

	public void createDirectDebit(Customer toCustomer, Account toAccount, double amount, int paymentDayOfMonth, LocalDate endDate){
		DirectDebit directDebit = new DirectDebit(toCustomer, toAccount, amount, paymentDayOfMonth, endDate);
		directdebits.add(directDebit);
	}

	public boolean cancelDirectDebit(String ID){
		boolean removed = false;
		DirectDebit ddToRemove = null;
		for (DirectDebit dd:  getDirectDebits()){
			if(dd.getID().equals(ID)){
				ddToRemove = dd;
			}
		}
		if(ddToRemove!=null){
			getDirectDebits().remove(ddToRemove);
			removed = true;
		}
		return removed;
	}

}
