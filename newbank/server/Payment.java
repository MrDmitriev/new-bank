package newbank.server;

import java.time.LocalDate;

public class Payment implements Transaction{
    private double amount;
    private String status;
    private LocalDate transactionDate;
    private TransactionAction action;
    private String account;
    private String toFromCustomerName;

    public Payment(double amount, String account, String toFromCustomerName){
        this.amount = amount;
        this.transactionDate = LocalDate.now();
        this.action = TransactionAction.PAYMENT;
        this.account = account;
        this.toFromCustomerName = toFromCustomerName;
    }

    // All transactions have a value, this gets the value of the transaction
    public double getAmount(){return amount;};

    public Object getStatus(){return status;};

    public Object getDate(){return transactionDate;};

    public String toString(){
        return (account + " " + action.toString() + " £" + amount + " on " + transactionDate.toString() + " to/from " + toFromCustomerName);
    };

    public TransactionAction getAction(){return action;};

    public String getAccount(){return account;};

}
