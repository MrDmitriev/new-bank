/**
 * MockDB.java
 * Week 3 Sprint
 * git branch feature/NB-17-Database-basic-implementation
 *
 * note - like NewBank, MockDB contains a self initialiser and offers two static methods
 * getPasswordFromUserName and getPasswordFromCustomerName
 *
 * variable and method list:
 * constructor MockDB(). This creates a new MockDB object and populates it with a list of 10 customers.
 * getCustomerDetails() returns the full customer database as an ArrayList<String[]> object. In other words ArrayList < {customer, userName, password) > object.
 * getCustomerNameFromUserName(String user) returns the customerName when the userName is provided to search the ArrayList.
 * getUserNameFromCustomerName(String customer) returns the userName when the customerName is provided to search the ArrayList.
 * static getPasswordFromUserName(String user) returns the password when the userName is provided.
 * static getPasswordFromCustomerName(String customer) returns the password when the customerName is provided.
 * createNewCustomer (String customerName, String userName, String password) setter to create a new customer in the following format { String customerName, String userName, String password }
 * removeCustomerRecord (String user, String password) removes a single customer record completely when the username and password is provided for a particular customer.
 * changePassword (String user, String oldPassword, String newPassword) changes a user’s password when a userName, oldPassword and newPassword are provided.
 * public void changeUserName (String user, String password, String newUsername) changes a userName when a userName and password are successfully provided.
 * listCustomerRecords() provides a list of neatly formatted customer records in the terminal.
 */
package newbank.server;
import java.util.ArrayList;

/** provides a DB simulation with 4 fields: customer as String, userName as String, password as String,
 * and user type as a String */
class MockDB {

    private static final MockDB db = new MockDB();
    private static ArrayList<String[]> customerDetails;
    private static ArrayList<Transaction> transactions;
   // private final CustomerData data;

    /** Create a new mock database and populate it with records */
    public MockDB(){
        CustomerData data = new CustomerData();
        customerDetails = new ArrayList<String[]>();
        customerDetails = data.getStoredCustomerData();
        transactions = new ArrayList<Transaction>();
    }

    /** getter method returning the custmerDetails ArrayList */
    public ArrayList<String[]> getCustomerDetails() {
        return customerDetails;
    }

    /** getter method sourcing customerName using userName */
    public String getCustomerNameFromUserName(String userName) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[1].equals(userName)) {
                result = record[0];
            }
        }
        return result;
    }

    /** getter method sourcing userName using customerName */
    public String getUserNameFromCustomerName(String customer) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[0].equals(customer)) {
                result = record[1];
            }
        }
        return result;
    }

    /** getter method sourcing password using userName */
    public static String getPasswordFromUserName(String userName) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[1].equals(userName)) {
                result = record[2];
            }
        }
        return result;
    }

    /** getter method sourcing password using customerName */
    public static String getPasswordFromCustomerName(String customer) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[0].equals(customer)) {
                result = record[2];
            }
        }
        return result;
    }

    /** getter method sourcing user type using customerName */
    public String getUserTypeFromCustomerName(String customer) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[0].equals(customer)) {
                result = record[3];
            }
        }
        return result;
    }

    /** getter method sourcing user type using userName */
    public String getUserTypeFromUserName(String userName) {
        String result = "";
        for (String[] record: customerDetails) {
            if (record[1].equals(userName)) {
                result = record[3];
            }
        }
        return result;
    }

    /** setter method that creates a new customer */
    public void createNewCustomer (String customerName, String userName, String  password, String userType) {
        for (String[] record: customerDetails) {
            if (record[0].equals(customerName) || record[1].equals(userName) || record[2].equals(password)) {
                break;
            }
        }
        String[] customer = {customerName, userName, password, userType};
        customerDetails.add(customer);
    }

    /** method that removes a customer record if userName and password supplied */
    public void removeCustomerRecord (String userName, String password) {
        for (String[] record: customerDetails) {
            if (record[1].equals(userName) && record[2].equals(password)) {
                customerDetails.remove(record);
                System.out.println();
                System.out.println("User " + record[1] + " has been permanently removed.");
            }
        }
    }

    /** method that allows a user to change his/her password */
    public void changePassword (String userName, String oldPassword, String newPassword) {
        for (String[] record : customerDetails) {
            if (record[2].equals(newPassword)) {
                System.out.println("Proposed password invalid, select a new password");
                break;
            }
        }
        for (String[] record: customerDetails) {
            if (record[1].equals(userName) && record[2].equals(oldPassword)) {
                record[2] = newPassword;
                System.out.println();
                System.out.println("User " + record[1] + " password has been updated.");
            }
        }
    }

    /** method that allows a user to change his/her userName */
    public void changeUserName (String userName, String password, String newUsername) {
        for (String[] record : customerDetails) {
            if (record[1].equals(newUsername)) {
                System.out.println("Proposed user name already exists");
                break;
            }
        }
        for (String[] record: customerDetails) {
            if (record[1].equals(userName) && record[2].equals(password)) {
                record[1] = newUsername;
                System.out.println();
                System.out.println("Customer " + record[0] + "'s username has been updated.");
            }
        }
    }

    /** method that allows a user to change his/her userName */
    public void changeUserType (String userName, String password, String newUserType) {
        for (String[] record: customerDetails) {
            if (record[1].equals(userName) && record[2].equals(password)) {
                record[3] = newUserType;
                System.out.println();
                System.out.println("Customer " + record[0] + "'s user type has been updated.");
            }
        }
    }

    /** print method that prints each record in the customerDetails ArrayList */
    public void listCustomerRecords() {
        for (String[] record: customerDetails) {
            System.out.println();
            System.out.println("Cutomer: " + record[0]);
            System.out.println("User Name: " + record[1]);
            System.out.println("Password: " + record[2]);
        }
    }

    public static MockDB getMockDB() {
        return db;
    }

    // Records a transaction to transactions in the database
    public static void recordTransaction(Transaction transaction) {

        if (transaction.getCustomerID() == null || transaction.getAccount() == null || transaction.getAction() == null || transaction.getValue() == 0) {
            System.out.println("Try to record a transaction again");
        }
        else {
            transactions.add(transaction);
        }
    }

    // Gets transactions of customer from transactions in the database
    public static ArrayList<Transaction> getTransactions(CustomerID customerID) {

        ArrayList<Transaction> transactionsOfCustomer = new ArrayList<Transaction>();

        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getCustomerID() == customerID) {
                transactionsOfCustomer.add(transactions.get(i));
            }
        }

        return transactionsOfCustomer;
    }
}
