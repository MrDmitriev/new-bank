package newbank.server;

public class TopUp {
    private double amount;
    private String date;
    private TopUpStatus status;

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
