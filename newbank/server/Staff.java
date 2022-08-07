package newbank.server;

public class Staff implements User {

	private String staffName;
	private Account accountOfStaff;

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
