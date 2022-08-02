package newbank.server;

public class Authorisation {

    private UserID userID;
    private Boolean authorised;

    // The following method creates an instance of Authorisation class for provided login details
    public Authorisation(String userName, String password) {
        if (MockDB.getPasswordFromUserName(userName).equals(password)) {
            this.userID = new UserID(userName);
            this.authorised = true;
        }
        else {
            this.authorised = false;
        }
    }

    public UserID getUserID() {
        return userID;
    }

    public Boolean userAuthorised() {
        return authorised;
    }
}
