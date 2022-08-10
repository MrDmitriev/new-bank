package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private Authorisation userAuthorisation;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		userAuthorisation = new Authorisation();
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			while (true) {
				out.println("Welcome to the NewBank");
				out.println("Select any command from main menu:");
				out.println("1. LOGIN");
				out.println("2. EXIT");

				String command = in.readLine();

			switch (command) {
				case "LOGIN":
				case "1":
					handleLogin(in);
				case "EXIT":
				case "2":
					handleExit();
			}
		}


		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

	private void handleUnsuccessfulAuthentication() throws IOException {
		out.println("Some of your credentials are incorrect");
		out.println("Would you like to try again? ");
		out.println("Enter: `yes` to try again");

		Boolean tryAgain = in.readLine().equals("yes");

		if (tryAgain) {
			handleLogin(in);
		}
	}

	private void handleSuccessfulAuthentication(UserID userId) throws IOException {
		while(true) {
			String userType = MockDB.getUserTypeFromUserName(userId.getKey());
			if (userType.equals("STAFF")){
				System.out.println(commandListStaff());
			} else {
				System.out.println(commandList());
			}

			String request = in.readLine();
			System.out.println("Request from " + userId.getKey());
			String responce = bank.processRequest(userId, request);
			out.println(responce);
			out.println("Press enter to continue...");
			in.readLine();

			if (responce.equals("LOGOUT")) {
				out.println("You have been logged out");
				return;
			}
		}
	}

	public void handleLogin(BufferedReader in) {
		try {
			// Try to authenticate the user. Take provided username and password and compare it with data in data base.
			out.println("Enter Username");
			String username = in.readLine();
			out.println("Enter Password");
			String password = in.readLine();

			Boolean isUserAuthenticated = userAuthorisation.checkAuthentication(username, password);

			// Handle successful authentication
			if (isUserAuthenticated) {
				out.println("Log In Successful, What would you like to do?");

				// generate user ID token from bank for use in subsequent requests
				UserID userId = new UserID(username);

				handleSuccessfulAuthentication(userId);
			} else {
				// Handle unsuccessful authentication
				handleUnsuccessfulAuthentication();
			}
		} catch (Exception e) {
			out.println("An error occured during login: " + e);
		}
	}

	public void handleExit() {
		out.println("Thank you for using NewBank, goodbye");
	}

	private String commandList(){
		return "\nPlease enter a command from the following list (leave spaces indicated by '+':\n" +
				"1)SHOWMYACCOUNTS\n" +
				"2)TOPUPACCOUNT + 'Account Name' + 'Top up amount'\n" +
				"3)NEWACCOUNT + 'New account name' + 'Opening balance'\n" +
				"4)MOVE + 'Name of withdrawal account' + 'Name of deposit account' + 'Amount'\n"+
				"5)PAY + 'Name of User' + 'Amount\n" +
				"6)VIEWTRANSACTIONS\n" +
				"7)CREATEDIRECTDEBIT + 'corporate user name' + 'Amount' + 'Payment day of month' + 'End date (format yyyy-mm-dd)' \n" +
				"8)VIEWDIRECTDEBITS\n" +
				"9)CANCELDIRECTDEBITS + 'Direct debit ID' \n" +
				"10)CHANGEACCOUNTNAME + 'Old Name' + 'New Name'\n" +
				"11)DELETEACCOUNT + 'Account Name'\n" +
				"12)CREATEMICROLOAN + 'standard user name' + 'Amount to borrow' \n" +
				"13)VIEWMICROLOANS\n" +
				"14)LOGOUT";
	}

	private String commandListStaff(){
		return "\nPlease enter a command from the following list (leave spaces indicated by '+':\n" +
				"1)SHOWAPPROVETRANSACTIONS\n" +
				"2)APPROVETOPUP + 'Username'\n" +
				"3)LISTUSERS \n" +
				"4)DELETEUSER + 'Customer name' + 'Username' + 'Password'\n"+
				"5)CREATEUSER 'New customer name' + 'New username' + 'New password' + 'User type'\n"+
				"6)EDITUSERPASSWORD 'Customer name' + 'Username' + 'Existing password' + 'New password'\n" +
				"7)EDITUSERNAME + 'Customer name' + 'Existing username' + 'Password' + 'New username' \n" +
				"8)LOGOUT";
	}


}
