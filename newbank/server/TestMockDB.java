/**
 * I've created a simple test to show you some of the MockDB functionality
 */

package newbank.server;

public class TestMockDB {
    private static MockDB mockDB;

    public static void main(String[] args) {

        mockDB = MockDB.getMockDB();
        mockDB.listCustomerRecords(); // list all customer records
        // attempt to create a new customer with an existing customer name
        mockDB.createNewCustomer("christina", "Christina", "yhT78", "standard");
        // create a new customer
        mockDB.createNewCustomer("daniel", "Daniel", "Tgh76", "standard");
        String yourPassword = MockDB.getPasswordFromUserName("Robert"); // test static getPasswordFromUserName function
        System.out.println("\nYour password is: " + yourPassword);
        // change an existing user's userName
        mockDB.changeUserName("John", "uI8t5", "Johnny");
        // attempt to change an existing user's password to a password already in existance.
        mockDB.changePassword("bhagy", "Lki87", "yU872");
        mockDB.listCustomerRecords(); // list all customer records

        String uType1 = mockDB.getUserTypeFromCustomerName("hsbc");
        System.out.println(uType1);

        mockDB.changeUserType("HSBC","r$tGH","admin");
        uType1 = mockDB.getUserTypeFromCustomerName("hsbc");
        System.out.println(uType1);

        String uType2 = mockDB.getUserTypeFromUserName("Daniel");
        System.out.println(uType2);
    }
}
