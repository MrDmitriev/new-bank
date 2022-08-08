package newbank.server;

public interface User {
    public UserType getUserType();
    public Account getAccount(String account);
}
