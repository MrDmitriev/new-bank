package newbank.server;

public interface Transaction {

    // All transactions have a value, this gets the value of the transaction
    public double getAmount();

    public Object getStatus();

    public Object getDate();

    public String toString();

    public TransactionAction getAction();

    public String getAccount();

}
