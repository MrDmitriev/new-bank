package newbank.server;

public class TopUp  implements Transaction{
    private double amount;
    private String date;
    private TopUpStatus status;
    /*
     * Idea here is that each time a customer top ups an account, a new TopUp object
     * is created.
     * The TopUp object is then added to the ArrayList of TopUp objects in the
     * Account object.
     * The reasoning behind this is so that the customer can see the history of all
     * the top ups they have made.
     * As well as the status and date of each top up.
     */

    public TopUp(double amount, String date) {
        this.amount = amount;
        this.date = date;
        this.status = TopUpStatus.PENDING;
    }

    public TopUpStatus getStatus() {
        return status;
    }

    public void setStatus(TopUpStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return ("£" + amount + " on " + date);
    }
}
