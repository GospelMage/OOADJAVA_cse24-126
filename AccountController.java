public class AccountController {
    private final Repository repository;

    public AccountController(Repository repo) {
        this.repository = repo;
    }

    /**
     * Create an account for a customer.
     *
     * @param customer the customer who will own account
     * @param type "savings", "investment", or "cheque"
     * @param initialDeposit initial deposit amount
     * @param branch branch name
     * @param extra optional param (interest rate or employment details)
     * @return created Account or null if invalid
     */
    public Account createAccount(Customer customer,
                                 String type,
                                 double initialDeposit,
                                 String branch,
                                 String extra) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");

        Account newAcc;
        switch (type.toLowerCase()) {
            case "savings":
                double rate = parseDoubleOrDefault(extra, 0.01);
                newAcc = new SavingsAccount(initialDeposit, branch, customer, rate);
                break;
            case "investment":
                double invRate = parseDoubleOrDefault(extra, 0.05);
                double min = 1000.0; // example rule
                if (initialDeposit < min) throw new IllegalArgumentException("Investment requires min deposit " + min);
                newAcc = new InvestmentAccount(initialDeposit, branch, customer, invRate, min);
                break;
            case "cheque":
                String employment = (extra == null) ? "" : extra;
                newAcc = new ChequeAccount(initialDeposit, branch, customer, employment);
                break;
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }

        customer.addAccount(newAcc);
        return newAcc;
    }

    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    private double parseDoubleOrDefault(String s, double def) {
        try {
            return (s == null || s.isBlank()) ? def : Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return def;
        }
    }
}
