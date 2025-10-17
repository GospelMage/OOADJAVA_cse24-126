public class ChequeAccount extends Account {
    private String employee;
    private String employeeAddress;

    public ChequeAccount(String accountNumber, Customer customer, String branch, 
                        String employee, String employeeAddress) {
        super(accountNumber, customer, branch);
        this.employee = employee;
        this.employeeAddress = employeeAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction(transactions.size() + 1, amount, "Withdrawal", balance));
        }
    }

    public String getEmployee() { return employee; }
    public String getEmployeeAddress() { return employeeAddress; }
}