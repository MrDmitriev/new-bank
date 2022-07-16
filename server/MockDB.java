/**
 * MockDB.java
 * Week 3 Sprint
 * git branch feature/NB-17-Database-basic-implementation
 */

/**
 * variable and method list:?
 * constructor MockDB(). This creates a new MockDB object and populates it with a list of 10 customers.
 * getCustomerDetails() returns the full customer database as an ArrayList<String[]> object. In other words ArrayList < {customer, userName, password) > object.
 * getCustomerNameFromUserName(String user) returns the customerName when the userName is provided to search the ArrayList.
 * getUserNameFromCustomerName(String customer) returns the userName when the customerName is provided to search the ArrayList.
 * getPasswordFromUserName(String user) returns the password when the userName is provided.
 * getPasswordFromCustomerName(String customer) returns the password when the customerName is provided.
 * createNewCustomer (String customerName, String userName, String password) setter to create a new customer in the following format { String customerName, String userName, String password }
 * removeCustomerRecord (String user, String password) removes a single customer record completely when the username and password is provided for a particular customer.
 * changePassword (String user, String oldPassword, String newPassword) changes a user’s password when a userName, oldPassword and newPassword are provided.
 * public void changeUserName (String user, String password, String newUsername) changes a userName when a userName and password are successfully provided.
 * listCustomerRecords() provides a list of neatly formatted customer records in the terminal.
 */
package newbank.server;
import java.util.ArrayList;

/** provides a DB simulation with 3 fields: customer as String, userName as String and password as String */
class MockDB {

    private ArrayList<String[]> customerDetails;
    private CustomerData data;

    /** Create a new mock database and populate it with records */
    public MockDB(){
        data = new CustomerData();
        customerDetails = new ArrayList<String[]>();
        customerDetails = data.getStoredCustomerData();
    }

    /** getter method returning the custmerDetails ArrayList */
    public ArrayList<String[]> getCustomerDetails() {
        return customerDetails;
    }

    /** getter method sourcing customerName using userName */
    public String getCustomerNameFromUserName(String userName) {
        String result = new String();
        for (String[] record: customerDetails) {
            if (record[1] == userName) {
                result = record[0];
            }
        }
        return result;
    }

    /** getter method sourcing userName using customerName */
    public String getUserNameFromCustomerName(String customer) {
        String result = new String();
        for (String[] record: customerDetails) {
            if (record[0] == customer) {
                result = record[1];
            }
        }
        return result;
    }

    /** getter method sourcing password using userName */
    public String getPasswordFromUserName(String userName) {
        String result = new String();
        for (String[] record: customerDetails) {
            if (record[1] == userName) {
                result = record[2];
            }
        }
        return result;
    }

    /** getter method sourcing passord using customerName */
    public String getPasswordFromCustomerName(String customer) {
        String result = new String();
        for (String[] record: customerDetails) {
            if (record[0] == customer) {
                result = record[2];
            }
        }
        return result;
    }

    /** setter method that creates a new customer */
    public void createNewCustomer (String customerName, String userName, String  password) {
        for (String[] record: customerDetails) {
            if (record[0] == customerName || record[1] == userName || record[2] == password) {
                break;
            }
        }
        String[] customer = {customerName, userName, password};
        customerDetails.add(customer);
    }

    /** method that removes a customer record if userName and password supplied */
    public void removeCustomerRecord (String userName, String password) {
        for (String[] record: customerDetails) {
            if (record[1] == userName && record[2] == password) {
                customerDetails.remove(record);
                System.out.println();
                System.out.println("User " + record[1] + " has been permanently removed.");
            }
        }
    }

    /** method that allows a user to change his/her password */
    public void changePassword (String userName, String oldPassword, String newPassword) {
        for (String[] record : customerDetails) {
            if (record[2] == newPassword) {
                System.out.println("Proposed password invalid, select a new password");
                break;
            }
        }
        for (String[] record: customerDetails) {
            if (record[1] == userName && record[2] == oldPassword) {
                record[2] = newPassword;
                System.out.println();
                System.out.println("User " + record[1] + " password has been updated.");
            }
        }
    }

    /** method that allows a user to change his/her userName */
    public void changeUserName (String userName, String password, String newUsername) {
        for (String[] record : customerDetails) {
            if (record[1] == newUsername) {
                System.out.println("Proposed user name already exists");
                break;
            }
        }
        for (String[] record: customerDetails) {
            if (record[1] == userName && record[2] == password) {
                record[1] = newUsername;
                System.out.println();
                System.out.println("Customer " + record[0] + "'s username has been updated.");
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

}
