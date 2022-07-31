package newbank.server;

import java.util.ArrayList;
import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String, Customer> customers;
	private static final int MINIMUM_PARAMETERS_NUMBER = 3;

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
//create customer object for bhagy
		Customer bhagy = new Customer();
//add account to list of type main with 1000
		bhagy.addAccount(new Account("Main", 1000.0));
//add bhagy to customers
		customers.put("Bhagy", bhagy);
//create customer for christina
		Customer christina = new Customer();
//add account to list of type savings with 1500
		christina.addAccount(new Account("Savings", 1500.0));
//add christina to customers
		customers.put("Christina", christina);
//create customer object for john
		Customer john = new Customer();
//add account for john of type checking with 250
		john.addAccount(new Account("Checking", 250.0));
//add john to customers
		customers.put("John", john);
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if (customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if (customers.containsKey(customer.getKey())) {
			// command parsing is now based on first word of request
			String command = request.split(" ")[0];
			switch (command) {
				case "SHOWMYACCOUNTS":
					return showMyAccounts(customer);
				case "TOPUPACCOUNT":
					return topUpAccount(customer, request);
				case "NEWACCOUNT":
					return createNewAccount(customer, request);
				case "MOVE":
					return moveBetweenAccounts(customer,request);
				case "PAY":
					return makePayment(customer, request);
				case "VIEWTRANSACTIONS":
					return viewTransactions(customer);
				default:
					return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String topUpAccount(CustomerID customerID, String request) {
		Customer customer = customers.get(customerID.getKey());
		// request is in the form of "TOPUPACCOUNT ACCOUNTNAME AMOUNT"
		// so we need to split the request into 3 parts
		String[] tokens = request.split(" ");
		if (tokens.length == 3) {
			Account account = customer.getAccount(tokens[1]);
			Double amount = Double.parseDouble(tokens[2]);
			if (account != null) {
				account.topUpAccount(amount);
				return "Pending approval from admin of the bank";
			}
		}
		return "FAIL";
	}

	// this function creates a new account
	private String createNewAccount(CustomerID customerID, String request) {
		// request is in the form of "NEWACCOUNT ACCOUNTNAME"
		String[] tokens = request.split(" ");

		if (tokens.length < MINIMUM_PARAMETERS_NUMBER) {
			return "FAIL - Account name or initial balance is missing";
		}
		if (tokens.length == MINIMUM_PARAMETERS_NUMBER) {
			Customer customer = customers.get(customerID.getKey());
			String accountName = tokens[1];
			Double initialBalance = Double.parseDouble(tokens[2]);

			if (customer.getAccount(accountName)!=null){
				return "FAIL - Account already exists";
			} else {
				customer.addAccount(new Account(accountName, initialBalance));
				return "Account " +accountName+ " created, opening balance "+initialBalance;
			}
		}
		return "FAIL";
	}

	// This function is for members to move money from one of their accounts to another
	// should only be allowed if:
	// 1. Both accounts exist and are owned by the requesting (logged in) member
	// 2. The account money is being withdrawn from has sufficient balance
	private String moveBetweenAccounts(CustomerID customerID, String request){
		// request is in the form of "MOVE <value> <from account name> <to account name>"
		String[] tokens = request.split(" ");
		if (tokens.length == 4){
			Customer customer = customers.get(customerID.getKey());
			double moveValue = Double.parseDouble(tokens[1]);
			Account fromAccount = customer.getAccount(tokens[2]);
			Account toAccount = customer.getAccount(tokens[3]);

			// Only attempt if both of the accounts are found to exist and be owned by the requesting customer
			if(fromAccount!=null && toAccount!=null){
				if (fromAccount.getCurrentBalance() < moveValue){
					return "FAIL - Insufficient balance in <from> account ";
				} else {
					//remove <moveValue> from <from> account
					fromAccount.removeBalance(moveValue);
					//add <moveValue> to <to> account
					toAccount.addBalance(moveValue);
					return "Pending approval from admin of the bank";
				}
			}
		}
		return "FAIL";
	}

	//allows logged in customer to make a payment to another customer
	// NOTE: currently assumes that all users have a 'Main' account, and assumes this is where
	//       all payments are made and received
	private String makePayment(CustomerID senderID, String request){
		// request is in the form of "PAY <Person/Company> <amount>"
		String[] tokens = request.split(" ");

		// input must have the correct number of arguments to try to proceed
		if (tokens.length == 3){
			Customer sender = customers.get(senderID.getKey());
			CustomerID receiverID = new CustomerID(tokens[1]);
			double payValue = Double.parseDouble(tokens[2]);
			Account senderAccount = sender.getAccount("Main");

			// if the account to make payment to can be found
			// (for the customer/company not making the payment)
			if(customers.containsKey(receiverID.getKey())){
				Customer receiver = customers.get(receiverID.getKey());
				Account receiverAccount = receiver.getAccount("Main");

				//only attempts to make payment if both sending and receiving accounts can be found
				if(senderAccount!=null && receiverAccount!=null){
					if (senderAccount.getCurrentBalance() < payValue){
						return "FAIL - Insufficient balance in <sender> account ";
					} else {
						//remove <moveValue> from <from> account
						senderAccount.removeBalance(payValue);
						//add <moveValue> to <to> account
						receiverAccount.addBalance(payValue);
						// Create a transaction to be recorded to a database
						Transaction transaction = new Transaction(senderID, senderAccount, TransactionAction.PAYMENT, payValue);
						return "Pending approval from admin of the bank";
					}
				}
			}
		}
		return "FAIL";
	}

	// Allows the user to view transactions
	private String viewTransactions(CustomerID customerID){

		ArrayList<Transaction> transactionsOfCustomer = MockDB.getTransactions(customerID);

		if (transactionsOfCustomer.size() == 0) {
			return "Transactions have not been recorded";
		}
		else {
			for(int i = 0; i < transactionsOfCustomer.size(); i++) {
				System.out.println(transactionsOfCustomer.get(i).getCustomerID() + " " + transactionsOfCustomer.get(i).getAccount().getName() + " " + transactionsOfCustomer.get(i).getAction() + " " + transactionsOfCustomer.get(i).getValue());
			}
			return "Transactions have been printed to the console";
		}
	}
}
