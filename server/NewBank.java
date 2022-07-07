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
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		/** put username, Customer and password into the HashMap for each customer */
		customers.put("Bhagy", new HashMap(){{put(bhagy, "12345");}});

		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		/** put username, Customer and password into the HashMap for each customer */
		customers.put("Christina", new HashMap(){{put(christina, "12345");}});

		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
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
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				default : return "FAIL";
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

}
