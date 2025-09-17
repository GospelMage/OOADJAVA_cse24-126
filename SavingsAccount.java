public class SavingsAccount extends Account implements InterestBearing {
    private double interestRate; // e.g., 0.03 for 3%

    public SavingsAccount(double initialDeposit, String branch, Customer owner, double interestRate) {
        super(initialDeposit, branch, owner);
        this.interestRate = interestRate;
    }

    public double calculateInterest(int months) {
        // simple interest for demonstration
        return balance * interestRate * (months / 12.0);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (interest: %.2f%%)", interestRate * 100);
    }
}
