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
        String[] customer0001 = {"christina","Christina","rwT56", "standard"};
        String[] customer0002 = {"john","John","uI8t5", "standard"};
        String[] customer0003 = {"mary","Mary","yh*I", "standard"};
        String[] customer0004 = {"bhagy","Bhagy","Lki87", "standard"};
        String[] customer0005 = {"theobald","Theobald","Kj8yt", "standard"};
        String[] customer0006 = {"frankie","Frankie","HmF55", "standard"};
        String[] customer0007 = {"robert","Robert","yU872", "standard"};
        String[] customer0008 = {"adrian","Adrian","Nbd43", "standard"};
        String[] customer0009 = {"luca","Luca","Ot6r5", "standard"};
        String[] customer0010 = {"indigo","Indigo","nT6n*", "standard"};
        String[] customer0011 = {"hsbc","HSBC","r$tGH", "corporate"};

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
    }

    public ArrayList<String[]> getStoredCustomerData() {
        return storedCustomerData;
    }
}