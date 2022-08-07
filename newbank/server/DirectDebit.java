package newbank.server;

import java.time.LocalDate;

/*
This class supports the implementation of direct debit payments
A direct debit is associated with an account (the account from which the direct debit is being made)
To set up the direct debit the following information is needed:
--> The amount of money required to be paid per month on the direct debit
--> The day of the month which the direct debit is being paid
--> The account to which the direct debit is being paid (from account is the account the
    direct debit is associated with)
--> The end date of the direct debit (start date is the date when the direct debit is created)

 */

public class DirectDebit implements Payable {
    private static int nextID = 1;
    private String ID;
    private double regularPaymentAmount;
    private DirectDebitStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Customer toCustomer;
    private Account toAccount;
    private int paymentDayOfMonth;
    public DirectDebit(Customer toCustomer, Account toAccount, double amount, int paymentDayOfMonth, LocalDate endDate) {
        this.ID = "DD_" + Integer.toString(nextID);
        nextID++;
        this.regularPaymentAmount = amount;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
        this.status = DirectDebitStatus.ACTIVE;
        this.toAccount = toAccount;
        this.toCustomer = toCustomer;
        this.paymentDayOfMonth = paymentDayOfMonth;
    }

    public String getID(){return ID;};
    public double getRegularPaymentAmount(){return regularPaymentAmount;}
    public DirectDebitStatus getStatus(){return status;};
    public Object getStartDate(){return startDate;};
    public int getPaymentDayOfMonth() {return paymentDayOfMonth;};
    public Object getEndDate() {return endDate;};
    public Account getToAccount(){return toAccount;}
    public String toString(){
        return (ID + ": £" + regularPaymentAmount + " to " + toCustomer.getCustomerName() +  " on " + paymentDayOfMonth +"th day of each month between " + startDate.toString() + " and " + endDate.toString());
    };

}
