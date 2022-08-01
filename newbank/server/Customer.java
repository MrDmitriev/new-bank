package newbank.server;

import java.util.ArrayList;

public class Customer implements User {
	private ArrayList<Account> accounts;
	private String customerName;
	private UserType userType;

	public Customer(String customerName, UserType userType) {
		this.customerName = customerName;
		this.userType = userType;
		accounts = new ArrayList<>();
	}

	public String getCustomerName() {
		return customerName;
	}

	//create string of all accounts
	public String accountsToString() {
		String s = "";
		for (Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	//get account details
	public Account getAccount(String accountName) {
		for (Account a : accounts) {
			if (a.getName().equals(accountName)) {
				return a;
			}
		}
		return null;
	}


	public void addAccount(Account account) {
		accounts.add(account);
	}

	public UserType getUserType() {
		return userType;
	}
}
