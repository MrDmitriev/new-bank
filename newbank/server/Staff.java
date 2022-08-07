package newbank.server;

import java.util.ArrayList;

public class Staff implements User {

	private String staffName;
	private Account accountOfStaff;
	private ArrayList<Account> accounts;

	public UserType getUserType() {
		return UserType.STAFF;
	}

	public Staff(String staffName) {
		this.staffName = staffName;
	}

	public String staffName() {
		return staffName;
	}

	public Account getAccount(String account) {
		return accountOfStaff;
	}

}
