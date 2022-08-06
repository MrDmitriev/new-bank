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
	
				out.println("You have selected" + command);
			
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
			System.out.println("handleSuccessfulAuthentication");

			String request = in.readLine();
			System.out.println("Request from " + userId.getKey());
			String responce = bank.processRequest(userId, request);
			out.println(responce);
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
				out.println("Log In Successful. What do you want to do?");
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

}
