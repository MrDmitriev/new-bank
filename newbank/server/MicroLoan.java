package newbank.server;

import java.time.LocalDate;

public class MicroLoan implements Payable {
    private static int nextID = 1;
    private String ID;
    private double regularPaymentAmount;
    private MicroLoanStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Customer toCustomer;
    private Account toAccount;
    private int paymentDayOfMonth;

    /* Microloans are at a fixed term of one year */
    public MicroLoan(Customer toCustomer, Account toAccount, double amount, double interestRate) {
        this.ID = "ML_" + Integer.toString(nextID);
        nextID++;
        this.regularPaymentAmount = calculateRegularPaymentAmount(amount, interestRate);
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusYears(1);    //hard coded 1 year as that's the only time period the calculation will work for
        this.status = MicroLoanStatus.ACTIVE;
        this.toAccount = toAccount;
        this.toCustomer = toCustomer;
        this.paymentDayOfMonth = LocalDate.now().getDayOfMonth();
    }

    private double calculateRegularPaymentAmount(double amount, double interestRate){
        double repayment;
        repayment = (amount + ((interestRate/100)*amount))/12;
        return repayment;
    }

    public String getID(){return ID;};
    public double getRegularPaymentAmount(){return regularPaymentAmount;}
    public MicroLoanStatus getStatus(){return status;};
    public LocalDate getStartDate(){return startDate;};
    public int getPaymentDayOfMonth() {return paymentDayOfMonth;};
    public LocalDate getEndDate() {return endDate;};
    public Account getToAccount(){return toAccount;}
    public Customer getToCustomer(){return toCustomer;}
    public String toString(){
        return (ID + ": £" + regularPaymentAmount + " to " + toCustomer.getCustomerName() +  " on " + paymentDayOfMonth +"th day of each month between " + startDate.toString() + " and " + endDate.toString());
    };


}
