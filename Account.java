import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Account  {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1000);

    protected final String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer owner;
    protected final List<Transaction> transactions = new ArrayList<>();

    public Account(double initialDeposit, String branch, Customer owner) {
        this.accountNumber = String.valueOf(ID_GEN.getAndIncrement());
        this.balance = initialDeposit;
        this.branch = branch;
        this.owner = owner;
        // initial deposit transaction if > 0
        if (initialDeposit > 0) {
            addTransaction("InitialDeposit", initialDeposit);
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public Customer getOwner() { return owner; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        balance += amount;
        addTransaction("Deposit", amount);
    }

    // Basic withdraw implementation; subclasses may override for extra rules
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        addTransaction("Withdraw", amount);
    }

    protected void addTransaction(String type, double amount) {
        int txId = TransactionIdGenerator.nextId();
        Transaction tx = new Transaction(txId, type, amount, balance);
        transactions.add(tx);
    }

    @Override
    public String toString() {
        return String.format("%s - Acc#: %s - Bal: %.2f - Branch: %s",
                this.getClass().getSimpleName(), accountNumber, balance, branch);
    }
}
