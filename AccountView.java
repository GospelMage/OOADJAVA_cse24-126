import java.util.List;
import java.util.Scanner;

public class AccountView {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountController accountController;

    public AccountView(AccountController ac) {
        this.accountController = ac;
    }

    public void showAccountMenu(Customer customer) {
        while (true) {
            System.out.println("\n=== Account Menu for " + customer.getFirstname() + " ===");
            System.out.println("1) Create Account");
            System.out.println("2) List Accounts");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw");
            System.out.println("5) View Transactions");
            System.out.println("6) Logout");
            System.out.print("Choose: ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    createAccountFlow(customer);
                    break;
                case "2":
                    listAccounts(customer);
                    break;
                case "3":
                    depositFlow(customer);
                    break;
                case "4":
                    withdrawFlow(customer);
                    break;
                case "5":
                    viewTransactions(customer);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createAccountFlow(Customer customer) {
        System.out.print("Account type (savings/investment/cheque): ");
        String type = scanner.nextLine().trim();
        System.out.print("Initial deposit: ");
        double init = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Branch: ");
        String branch = scanner.nextLine().trim();
        String extra = null;
        if ("savings".equalsIgnoreCase(type)) {
            System.out.print("Interest rate (e.g., 0.02): ");
            extra = scanner.nextLine().trim();
        } else if ("investment".equalsIgnoreCase(type)) {
            System.out.print("Interest rate (e.g., 0.05): ");
            extra = scanner.nextLine().trim();
        } else if ("cheque".equalsIgnoreCase(type)) {
            System.out.print("Employment details: ");
            extra = scanner.nextLine().trim();
        }
        try {
            Account acc = accountController.createAccount(customer, type, init, branch, extra);
            System.out.println("Account created: " + acc);
        } catch (Exception ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        }
    }

    private void listAccounts(Customer customer) {
        List<Account> accs = customer.getAccounts();
        if (accs.isEmpty()) {
            System.out.println("No accounts.");
            return;
        }
        for (int i = 0; i < accs.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, accs.get(i).toString());
        }
    }

    private Account selectAccount(Customer customer) {
        listAccounts(customer);
        System.out.print("Choose account number (index): ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= customer.getAccounts().size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return customer.getAccounts().get(idx);
    }

    private void depositFlow(Customer customer) {
        Account acc = selectAccount(customer);
        if (acc == null) return;
        System.out.print("Amount to deposit: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        try {
            accountController.deposit(acc, amt);
            System.out.printf("Deposited %.2f. New balance: %.2f\n", amt, acc.getBalance());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void withdrawFlow(Customer customer) {
        Account acc = selectAccount(customer);
        if (acc == null) return;
        System.out.print("Amount to withdraw: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        try {
            accountController.withdraw(acc, amt);
            System.out.printf("Withdrew %.2f. New balance: %.2f\n", amt, acc.getBalance());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void viewTransactions(Customer customer) {
        Account acc = selectAccount(customer);
        if (acc == null) return;
        if (acc.getTransactions().isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("Transactions for account " + acc.getAccountNumber());
        for (Transaction tx : acc.getTransactions()) {
            System.out.println(tx);
        }
    }
}
