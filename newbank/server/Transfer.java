package newbank.server;

import java.time.LocalDate;

/* Transfer class is used when customers are moving from one account to another */
public class Transfer implements Transaction{
    private double amount;
    private String status;
    private LocalDate transactionDate;
    private TransactionAction action;
    private String account;

    public Transfer(double amount, String account){
        this.amount = amount;
        this.transactionDate = LocalDate.now();
        this.action = TransactionAction.TRANSFER;
        this.account = account;
    }

    // All transactions have a value, this gets the value of the transaction
    public double getAmount(){
        return amount;
    };

    public Object getStatus(){
        return status;
    };

    public Object getDate(){
        return transactionDate;
    };

    public String toString(){
        return (account + " " + action.toString() +" £" + amount + " on " + transactionDate.toString());
    }

    public TransactionAction getAction() {
        return action;
    }

    public String getAccount() {
        return account;
    }
};
