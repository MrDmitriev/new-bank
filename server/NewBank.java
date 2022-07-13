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

}
