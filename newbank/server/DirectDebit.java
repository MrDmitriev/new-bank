package newbank.server;

import java.util.Date;

public class DirectDebit implements Transaction {
    private double amount;
    private String status;
    private Date transactionDate;
    private TransactionAction action;
    private String account;


    @Override
    public double getAmount(){
        return amount;
    }

    @Override
    public Object getStatus(){
        return status;
    };

    public Object getDate(){
        return transactionDate;
    };

    public String toString(){
        return ("£" + amount + " on " + transactionDate.toString());
    };

    public TransactionAction getAction() {
        return action;
    }

    public String getAccount() {
        return account;
    }


}
