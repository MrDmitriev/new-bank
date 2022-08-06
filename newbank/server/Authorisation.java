package newbank.server;

public class Authorisation {
    private UserID userID;
    private Boolean authorised;

    public UserID getUserID() {
        return userID;
    }

    public Boolean userAuthorised() {
        return authorised;
    }

    public Boolean checkAuthentication(String username, String password) {
        String originalPassword = MockDB.getPasswordFromUserName(username);
        if (originalPassword.length() == 0) {
            return false;
        }
        return originalPassword.equals(password);
    }
}
