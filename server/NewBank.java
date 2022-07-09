package newbank.server;

import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String, Customer> customers;

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
		Customer customer = customers.get(customerID.getKey());
		// request is in the form of "NEWACCOUNT ACCOUNTNAME"
		//
		String[] tokens = request.split(" ");
		if (tokens.length >= 2) {
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
}
