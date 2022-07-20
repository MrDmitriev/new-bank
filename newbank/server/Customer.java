package newbank.server;

import java.util.ArrayList;

public class Customer {

	private ArrayList<Account> accounts;

	public Customer() {
		accounts = new ArrayList<>();
	}

	//create string of all accounts
	public String accountsToString() {
		String s = "";
		for (Account a : accounts) {
			s += a.toString();
		}
		return s;
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
}
