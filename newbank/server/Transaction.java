package newbank.server;

public class Transaction {

    private CustomerID customerID;
    private Account account;
    private TransactionAction action;
    private double value;

    public Transaction(CustomerID customerID, Account account, TransactionAction action, double value) {
        this.customerID = customerID;
        this.account = account;
        this.action = action;
        this.value = value;
    }

    public CustomerID getCustomerID() {
        return customerID;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionAction getAction() {
        return action;
    }

    public double getValue() {
        return value;
    }
}
