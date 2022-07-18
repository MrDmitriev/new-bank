package newbank.server;

public class Authorisation {

    private CustomerID customerID;
    private Boolean authorised;

    // The following method creates an instance of Authorisation class for provided login details
    public Authorisation(String userName, String password) {
        if (MockDB.getPasswordFromUserName(userName).equals(password)) {
            this.customerID = new CustomerID(userName);
            this.authorised = true;
        }
        else {
            this.authorised = false;
        }
    }

    public CustomerID getCustomerID() {
        return customerID;
    }

    public Boolean userAuthorised() {
        return authorised;
    }
}
