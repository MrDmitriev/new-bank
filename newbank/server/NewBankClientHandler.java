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
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			userAuthorisation = new Authorisation(userName, password);
			UserID customer = userAuthorisation.getUserID();
			// if the user is authenticated then get requests from the user and process them 
			if(userAuthorisation.userAuthorised() == true && customer != null) {
				out.println("Log In Successful. \nPlease enter a command from the following list (leave spaces indicated by '+':\n" +
						"1)SHOWMYACCOUNTS\n" +
						"2)TOPUPACCOUNT + 'Account Name' + 'Top up amount'\n" +
						"3)NEWACCOUNT + 'New account name' + 'Opening balance'\n" +
						"4)MOVE + 'Name of withdrawal account' + 'Name of deposit account' + 'Amount'\n"+
						"5)PAY + 'Name of User' + 'Amount\n" +
						"6)LOGOUT");
				while(true) {
					String request = in.readLine();
					System.out.println("Request from " + customer.getKey());
					String responce = bank.processRequest(customer, request);
					out.println(responce);
				}
			}
			else {
				out.println("Log In Failed");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
