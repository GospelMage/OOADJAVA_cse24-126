public class InvestmentAccount extends Account implements InterestBearing {
    private double interestRate;
    private double minInitialDeposit;

    public InvestmentAccount(double initialDeposit, String branch, Customer owner, double interestRate, double minInitialDeposit) {
        super(initialDeposit, branch, owner);
        if (initialDeposit < minInitialDeposit) throw new IllegalArgumentException("Initial deposit too low.");
        this.interestRate = interestRate;
        this.minInitialDeposit = minInitialDeposit;
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        // potential extra rules here
    }

    @Override
    public void withdraw(double amount) {
        // might have penalties, but for demo we reuse base
        super.withdraw(amount);
    }

    public double calculateInterest(int months) {
        return balance * interestRate * (months / 12.0);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (inv-interest: %.2f%%)", interestRate * 100);
    }
}
