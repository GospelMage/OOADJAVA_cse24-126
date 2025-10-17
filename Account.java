import java.util.ArrayList;
import java.util.List;

public abstract class Account implements AccountInterface {
    protected String accountNumber;
    protected double balance;
    protected Customer customer;
    protected String branch;
    protected List<Transaction> transactions;

    public Account(String accountNumber, Customer customer, String branch) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.branch = branch;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        this.balance += amount;
        transactions.add(new Transaction(transactions.size() + 1, amount, "Deposit", balance));
    }

    public double viewBalance() {
        return balance;
    }

    public List<Transaction> viewTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public String getBranch() { return branch; }
}