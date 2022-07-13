package newbank.server;

import java.util.HashMap;

public class NewBank {
	private static final NewBank bank = new NewBank();
	/**
	 * Created nested HashMap to hold the Customer, username, and password
	 * relationships:
	 * HashMap<userName, HashMap <Customer, String>> which maps to
	 *  <String < Customer, password>>
	 **/
	private HashMap< String, HashMap <Customer, String> > customers;

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
    /** put username, Customer and password into the HashMap for each customer */
		customers.put("Bhagy", new HashMap(){{put(bhagy, "12345");}});

//create customer for christina
		Customer christina = new Customer();
//add account to list of type savings with 1500
		christina.addAccount(new Account("Savings", 1500.0));


//add christina to customers
    /** put username, Customer and password into the HashMap for each customer */
		customers.put("Christina", new HashMap(){{put(christina, "12345");}});

//create customer object for john
		Customer john = new Customer();
//add account for john of type checking with 250
		john.addAccount(new Account("Checking", 250.0));
    

//add john to customers
    /** put username, Customer and password into the HashMap for each customer */
		customers.put("John", new HashMap(){{put(john, "12345");}});

	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {

		/**
		 * Access nested HashMap to authenticate both the customer userName and password
		 * from user input.  If this fails, program returns Null. If it passes it allows
		 * the user to process requests.
		 */

		if ((customers.containsKey(userName) && (customers.get(userName).containsValue(password)))) {
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
				default:
					return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		/**
		 * Access Customer.accountsToString() method using .keySet().iterator().next()
		 * to source Customer key
		 */
		return customers.get(customer.getKey()).keySet().iterator().next().accountsToString();

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
		if (tokens.length >= 2) {
			Customer customer = customers.get(customerID.getKey());
			String accountName = tokens[1];
			if (customer.getAccount(accountName)!=null){
				return "FAIL - Account already exists";
			} else {
				customer.addAccount(new Account(accountName,0));
				return "Account " +accountName+ " created, opening balance "+0;
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
					return "SUCCESS";
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
						return "SUCCESS";
					}
				}
			}
		}
		return "FAIL";
	}
}
