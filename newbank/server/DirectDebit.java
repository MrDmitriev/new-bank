package newbank.server;

import java.util.Date;

public class DirectDebit implements Transaction {
    private double amount;
    private String status;
    private Date transactionDate;


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


}
