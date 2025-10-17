public class SavingAccount extends Account implements InterestInterface {
    private double interestRate;

    public SavingAccount(String accountNumber, Customer customer, String branch, double interestRate) {
        super(accountNumber, customer, branch);
        this.interestRate = interestRate;
    }

    @Override
    public void calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.println("Calculated interest: P" + interest + " for account " + accountNumber);
    }

    @Override
    public void payInterest() {
        double interest = balance * interestRate / 100;
        if (interest > 0) {
            deposit(interest);
            transactions.add(new Transaction(
                transactions.size() + 1, 
                interest, 
                "Interest Payment", 
                balance
            ));
            System.out.println("Paid interest: P" + interest + " to account " + accountNumber);
        }
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction(transactions.size() + 1, amount, "Withdrawal", balance));
        }
    }
}