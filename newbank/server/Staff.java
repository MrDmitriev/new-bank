package newbank.server;

public class Staff implements User {

	public UserType getUserType() {
		return UserType.STAFF;
	}

	private String staffName;

	public Staff(String staffName) {
		this.staffName = staffName;
	}

	public String staffName() {
		return staffName;
	}

}
