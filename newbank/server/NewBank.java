package newbank.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String, User> users;
	private static final int MINIMUM_PARAMETERS_NUMBER = 3;
	private final MockDB db = MockDB.getMockDB();
	private ArrayList<String[]> customerData =  new ArrayList<String[]>();

	private NewBank() {
		users = new HashMap<>();
		addTestData();

	}

	private void addTestData() {
		customerData =  db.getCustomerDetails();

		// for each customer in the MockDB, create a customer object and a main
		// account object, putting 1000 in each account
		for(String[] record: customerData){
			UserType userType = UserType.valueOf(record[3]);
			if (userType == UserType.STAFF) {
				Staff staff = new Staff(record[1]);
				users.put(record[1], staff);
			} else {
				Customer customer = new Customer(record[1], userType);
				Account mainAccount = new Account("Main", 1000);
				customer.addAccount(mainAccount);
				users.put(record[1], customer);
			}
		}
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized UserID checkLogInDetails(String userName, String password) {
		if (users.containsKey(userName)) {
			return new UserID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(UserID customer, String request) {

		if (users.containsKey(customer.getKey())) {
			User user = users.get(customer.getKey());
			String command = request.split(" ")[0];
			if (user.getUserType() == UserType.STAFF) {
				switch (command) {
					case "SHOWAPPROVETRANSACTIONS":
						return transactionsPending();
					case "APPROVETOPUP":
						return approveTopUp(request);
					case "LISTUSERS":
						return listUsers();
					case "CREATEUSER":
						return createUser(request);
					case "DELETEUSER":
						return deleteUser(request);
					case "EDITUSERPASSWORD":
						return editUserPassword(request);
					case "EDITUSERNAME":
						return editUserName(request);
					default:
						return "FAIL";
				}
			} else {
				// command parsing is now based on first word of request
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
	}
	return "FAIL";
}

private String approveTopUp(String request) {
	System.out.println(request);
	Customer customer = (Customer) users.get(request.split(" ")[1]);
	if (customer == null) {
		return "FAIL";
	}
	for (Account a : customer.getAccounts()) {
		for (TopUp t : a.pendingTopUps()) {
			a.addBalance(t.getAmount());
			a.addtoTransactions(t);
			t.setStatus(TopUpStatus.SUCCESS);
		}
		a.clearPendingTopUps();
	}
	return "SUCCESS";
}

	private String transactionsPending() {
		String pendingTopUps = "";
		for (User user : users.values()) {
			if (user.getUserType() != UserType.STAFF) {
				Customer customer = (Customer) user;
				for (Account account : customer.getAccounts()) {
					if (account.pendingTopUps().size() > 0) {
						for (TopUp topUp : account.pendingTopUps()) {
							pendingTopUps += customer.getCustomerName() + " " + account.getName() + " "
									+ topUp.getAmount()
									+ "\n";
						}
					}
				}
			}
		}
		return pendingTopUps;
	}

	private String showMyAccounts(UserID customer) {
		return ((Customer) users.get(customer.getKey())).accountsToString();
	}

	private String topUpAccount(UserID customerID, String request) {
		Customer customer = (Customer) users.get(customerID.getKey());
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
	private String createNewAccount(UserID customerID, String request) {
		// request is in the form of "NEWACCOUNT ACCOUNTNAME OPENINGBALANCE"
		String[] tokens = request.split(" ");

		if (tokens.length < MINIMUM_PARAMETERS_NUMBER) {
			return "FAIL - Account name or initial balance is missing";
		}
		if (tokens.length == MINIMUM_PARAMETERS_NUMBER) {
			Customer customer = (Customer) users.get(customerID.getKey());
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
	private String moveBetweenAccounts(UserID customerID, String request) {
		// request is in the form of "MOVE <value> <from account name> <to account name>"
		String[] tokens = request.split(" ");
		if (tokens.length == 4){
			Customer customer = (Customer) users.get(customerID.getKey());
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
	private String makePayment(UserID senderID, String request) {
		// request is in the form of "PAY <Person/Company> <amount>"
		String[] tokens = request.split(" ");

		// input must have the correct number of arguments to try to proceed
		if (tokens.length == 3){
			Customer sender = (Customer) users.get(senderID.getKey());
			UserID receiverID = new UserID(tokens[1]);
			double payValue = Double.parseDouble(tokens[2]);
			Account senderAccount = sender.getAccount("Main");

			// if the account to make payment to can be found
			// (for the customer/company not making the payment)
			if (users.containsKey(receiverID.getKey())) {
				Customer receiver = (Customer) users.get(receiverID.getKey());
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
						return "Pending approval from admin of the bank";
					}
				}
			}
		}
		return "FAIL";
	}

	/**
	 * List all users contained in MockDB database.
	 * Produces a list as customer, username, password, usertype
	 */
	private String listUsers() {
		System.out.println();
		db.listCustomerRecords();
		/** uncomment to test that customerData arraylist updates after a change to customer data
		 * for (String[] record: customerData) {
		 * System.out.println("Cutomer: " + record[0] + " User Name: " + record[1] + " Password: " + record[2] );
		 * }
		 */
		System.out.println();
		return "SUCCESS";
	}

	/**
	 * Method that allows STAFF user types to create a new customer
	 * CREATEUSER newCustomerName, newUserName, newPassword, newUserType
	 */
	private String createUser(String request) {
		System.out.println(request);
		ArrayList<String> newUser = new ArrayList<>(Arrays.asList(request.split(" ")).subList(0, 5));
		for (int i = 0; i < 5; i++) {
			if (newUser.get(i) == null) {
				return "FAIL";
			}
		}
		for (UserType uType: UserType.values()) {
			if (newUser.get(4).equals(uType.toString())) {
				System.out.println(uType.toString());
				String[] record = {newUser.get(1), newUser.get(2), newUser.get(3), newUser.get(4)}; // IS THIS 4TH PARAMETER NEEDED?
				db.createNewCustomer(newUser.get(1), newUser.get(2), newUser.get(3), newUser.get(4));
				return "SUCCESS";
				}
			}
		return "FAIL";
	}

	/**
	 * Method that allows STAFF user types to delete an existing customer
	 * DELETEUSER customerName, userName, password deletes the entire record
	 * for customerName
	 */
	private String deleteUser(String request) {
		System.out.println(request);

		ArrayList<String> customerDetails = new ArrayList<>(Arrays.asList(request.split(" ")).subList(0, 4));

		for (int i = 0; i < 4; i++) {
			if (customerDetails.get(i) == null) {
				return "FAIL";
			}
		}

		for (String[] record : db.getCustomerDetails()) {
			if (customerDetails.get(1).equals(record[0])) {
				db.removeCustomerRecord(customerDetails.get(2), customerDetails.get(3));
				//db.getCustomerDetails().remove(record);
				return "SUCCESS";
			}
		}

		return "FAIL";
	}

	/**
	 * Method that allows STAFF user to edit an existing customer's password.
	 * EDITUSERPASSWORD customerName, userName, existingPassword, newPassword
	 */

	private String editUserPassword(String request) {
		System.out.println(request);

		ArrayList<String> requestDetails = new ArrayList<>(Arrays.asList(request.split(" ")).subList(0, 5));

		for (int i = 0; i < 5; i++) {
			if (requestDetails.get(i) == null) {
				return "FAIL";
			}
		}
		// capture new password selection in variable newPassword
		String newPassword = requestDetails.get(4);
		for (String[] record : db.getCustomerDetails()) {
			if (requestDetails.get(1).equals(record[0])) {
				(db.getCustomerDetails().get(db.getCustomerDetails().indexOf(record)))[2] = newPassword;
				return "SUCCESS";
			}
		}
		return "FAIL";
	}

	/**
	 * Method that allows STAFF user to edit an existing customer's userName.
	 * EDITUSERNAME customerName, existingUserName, password, newUserName
	 */
	private String editUserName(String request) {
		System.out.println(request);

		ArrayList<String> requestDetails = new ArrayList<>(Arrays.asList(request.split(" ")).subList(0, 5));

		for (int i = 0; i < 5; i++) {
			if (requestDetails.get(i) == null) {
				return "FAIL";
			}
		}
		for (String[] record : db.getCustomerDetails()) {
			if (requestDetails.get(1).equals(record[0])) {
				(db.getCustomerDetails().get(db.getCustomerDetails().indexOf(record)))[1] = requestDetails.get(4);
				return "SUCCESS";
			}
		}
		return "FAIL";
	}

}
