public class ChequeAccount extends Account {
    private String employmentDetails;

    public ChequeAccount(double initialDeposit, String branch, Customer owner, String employmentDetails) {
        super(initialDeposit, branch, owner);
        this.employmentDetails = employmentDetails;
    }

    // May implement overdraft rules here in future

    @Override
    public String toString() {
        return super.toString() + String.format(" (emp: %s)", employmentDetails);
    }
}
