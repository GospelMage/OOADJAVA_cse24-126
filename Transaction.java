import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final LocalDateTime dateTime;
    private final String type; // Deposit, Withdraw, Transfer
    private final double amount;
    private final double balanceAfter;

    public Transaction(int transactionId, String type, double amount, double balanceAfter) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.dateTime = LocalDateTime.now();
    }

    public int getTransactionId() { return transactionId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %.2f | bal: %.2f",
                transactionId, dateTime.toString(), type, amount, balanceAfter);
    }
}
