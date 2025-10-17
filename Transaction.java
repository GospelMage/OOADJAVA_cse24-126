public class Transaction {
    private int transactionId;
    private double amount;
    private String transactionType;
    private double balanceAfter;

    public Transaction(int transactionId, double amount, String transactionType, double balanceAfter) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.balanceAfter = balanceAfter;
    }

    public int getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getTransactionType() { return transactionType; }
    public double getBalanceAfter() { return balanceAfter; }
}