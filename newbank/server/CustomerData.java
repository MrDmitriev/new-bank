/**
 * The CustomerData class contains a sample set of customer data
 * which can be accessed through the getStoredCustomerData method.
 * This data is accessed and automatically loaded through the
 * MockDB class.
 */

package newbank.server;

import java.util.ArrayList;

class CustomerData {

    private static ArrayList <String[]> storedCustomerData = new ArrayList <String[]>();

    public CustomerData () {
        loadCustomerData();
    }

    private void loadCustomerData() {

        // data = CustomerData.customerData();
        // customer: {users first name, username, password, user type}
        String[] customer0001 = { "christina", "Christina", "rwT56", "STANDARD" };
        String[] customer0002 = { "john", "John", "uI8t5", "STANDARD" };
        String[] customer0003 = { "mary", "Mary", "yh*I", "STANDARD" };
        String[] customer0004 = { "bhagy", "Bhagy", "qwerty", "STANDARD" };
        String[] customer0005 = { "theobald", "Theobald", "Kj8yt", "STANDARD" };
        String[] customer0006 = { "frankie", "Frankie", "HmF55", "STANDARD" };
        String[] customer0007 = { "robert", "Robert", "yU872", "STANDARD" };
        String[] customer0008 = { "adrian", "Adrian", "Nbd43", "STANDARD" };
        String[] customer0009 = { "luca", "Luca", "Ot6r5", "STANDARD" };
        String[] customer0010 = { "indigo", "Indigo", "nT6n*", "STANDARD" };
        String[] customer0011 = { "hsbc", "HSBC", "r$tGH", "CORPORATE" };
        String[] customer0012 = { "bill", "Bill", "clinton", "STAFF" };

        storedCustomerData.add(customer0001);
        storedCustomerData.add(customer0002);
        storedCustomerData.add(customer0003);
        storedCustomerData.add(customer0004);
        storedCustomerData.add(customer0005);
        storedCustomerData.add(customer0006);
        storedCustomerData.add(customer0007);
        storedCustomerData.add(customer0008);
        storedCustomerData.add(customer0009);
        storedCustomerData.add(customer0010);
        storedCustomerData.add(customer0011);
        storedCustomerData.add(customer0012);

    }

    public ArrayList<String[]> getStoredCustomerData() {
        return storedCustomerData;
    }
}