/**
 * I've created a simple test to show you some of the MockDB functionality
 */

package newbank.server;

public class TestMockDB {

    public static void main(String[] args) {
        MockDB myDB = new MockDB();
        myDB.listCustomerRecords(); // list all customer records
        // attempt to create a new customer with an existing customer name
        myDB.createNewCustomer("christina", "Christina", "yhT78");
        // create a new customer
        myDB.createNewCustomer("daniel", "Daniel", "Tgh76");
        // change an existing user's userName
        myDB.changeUserName("John", "uI8t5", "Johnny");
        // attempt to change an existing user's password to a password already in existance.
        myDB.changePassword("bhagy", "Lki87", "yU872");
        myDB.listCustomerRecords(); // list all customer records
    }
}
