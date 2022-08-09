package newbank.server;

/* this Interface is to store the information about items that require a
regular payment to be applied from one account to another
* */

public interface Payable {

    public Object getStartDate();

    public Object getEndDate();

    public double getRegularPaymentAmount();

    public int getPaymentDayOfMonth();
    public Account getToAccount();

    public Object getStatus();

}
