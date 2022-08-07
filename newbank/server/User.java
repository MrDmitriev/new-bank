package newbank.server;

import java.util.ArrayList;

public interface User {
    public UserType getUserType();
    public Account getAccount(String account);

    public ArrayList<Account> getAccounts();
}
