import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HybridBankingSystem {
    private Stage primaryStage;
    private Map<String, Customer> customers = new HashMap<>();
    private Customer currentCustomer;
    private int accountCounter = 1001;
    private int customerIdCounter = 1001;

    public HybridBankingSystem(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Create sample personal customer
        Personal customer1 = new Personal("John", "Doe", "123 Main St", customerIdCounter++, 
                                         123456789, 5550101, "john@email.com");
        
        // Create sample company customer
        Company customer2 = new Company("ABC Corporation", "456 Business Ave", customerIdCounter++,
                                       5550102, "Technology", 987654321, "LIC-2024-001");
        
        customers.put("john", customer1);
        customers.put("abccorp", customer2);
        
        // Sample accounts
        SavingAccount savings = new SavingAccount("ACC1001", customer1, "Main Branch", 2.5);
        savings.deposit(1000.0);
        customer1.addAccount(savings);
        
        ChequeAccount cheque = new ChequeAccount("ACC1002", customer1, "Main Branch", "Mike Johnson", "789 Employee St");
        cheque.deposit(500.0);
        customer1.addAccount(cheque);
        
        InvestmentAccount investment = new InvestmentAccount("ACC1003", customer1, "Main Branch", 3.5, 1000.0);
        investment.deposit(2000.0);
        customer1.addAccount(investment);
    }

    // FXML Navigation Methods
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(root, 400, 400);
            primaryStage.setTitle("Banking System - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showLoginScreenProgrammatic();
        }
    }

    private void showLoginScreenProgrammatic() {
        // Fallback programmatic login screen
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        grid.add(btn, 1, 4);

        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            if (authenticateUser(username, password)) {
                showMainMenu();
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainMenu() {
        showMainDashboard();
    }

    public void showAccountView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountView.fxml"));
            Parent root = loader.load();
            
            AccountViewController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(root, 800, 700);
            primaryStage.setTitle("Account Management - " + getCustomerDisplayName());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAllAccountsScreen();
        }
    }

    // Methods called by LoginController
    public void showPersonalRegistration() {
        showPersonalRegistrationScreen();
    }

    public void showCompanyRegistration() {
        showCompanyRegistrationScreen();
    }

    // Methods called by AccountViewController
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public ObservableList<String> getAccountsData() {
        ObservableList<String> accounts = FXCollections.observableArrayList();
        
        if (currentCustomer == null) return accounts;
        
        for (Account account : currentCustomer.getAccounts()) {
            StringBuilder accountInfo = new StringBuilder();
            
            // Add emoji based on account type
            if (account instanceof SavingAccount) {
                accountInfo.append("💰 Savings Account - ");
            } else if (account instanceof ChequeAccount) {
                accountInfo.append("💳 Cheque Account - ");
            } else if (account instanceof InvestmentAccount) {
                accountInfo.append("📈 Investment Account - ");
            } else {
                accountInfo.append("🏦 Account - ");
            }
            
            accountInfo.append(account.getAccountNumber())
                      .append(" - Balance: P")
                      .append(String.format("%.2f", account.viewBalance()));
            
            // Add interest rate for interest-bearing accounts
            if (account instanceof InterestInterface) {
                InterestInterface interestAccount = (InterestInterface) account;
                accountInfo.append(" - Interest: ")
                          .append(interestAccount.getInterestRate())
                          .append("%");
            } else {
                accountInfo.append(" - Interest: 0%");
            }
            
            accounts.add(accountInfo.toString());
        }
        
        if (accounts.isEmpty()) {
            accounts.add("No accounts found. Create your first account!");
        }
        
        return accounts;
    }

    public ObservableList<String> getTransactionsData() {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        
        if (currentCustomer == null) return transactions;
        
        // Get transactions from all accounts
        for (Account account : currentCustomer.getAccounts()) {
            for (Transaction transaction : account.viewTransactions()) {
                String emoji = "✅ ";
                if (transaction.getType().toLowerCase().contains("withdrawal")) {
                    emoji = "❌ ";
                } else if (transaction.getType().toLowerCase().contains("interest")) {
                    emoji = "💰 ";
                } else if (transaction.getType().toLowerCase().contains("deposit")) {
                    emoji = "📥 ";
                }
                
                String transactionText = String.format("%s%s - P%.2f - Balance: P%.2f",
                    emoji,
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getBalanceAfter());
                
                transactions.add(transactionText);
            }
        }
        
        if (transactions.isEmpty()) {
            transactions.add("No transactions found.");
        }
        
        return transactions;
    }

    public double getTotalBalance() {
        if (currentCustomer == null) return 0.0;
        return currentCustomer.getAccounts().stream()
                .mapToDouble(Account::viewBalance)
                .sum();
    }

    public double getAverageInterestRate() {
        if (currentCustomer == null) return 0.0;
        
        long interestAccounts = currentCustomer.getAccounts().stream()
                .filter(account -> account instanceof InterestInterface)
                .count();
        
        if (interestAccounts == 0) return 0.0;
        
        double totalRate = currentCustomer.getAccounts().stream()
                .filter(account -> account instanceof InterestInterface)
                .mapToDouble(account -> ((InterestInterface) account).getInterestRate())
                .sum();
        
        return totalRate / interestAccounts;
    }

    public void handleDepositFromController(double amount, String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public void handleWithdrawFromController(double amount, String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null && amount <= account.viewBalance()) {
            account.withdraw(amount);
        }
    }

    public Account findAccountByNumber(String accountNumber) {
        if (currentCustomer == null) return null;
        return currentCustomer.getAccounts().stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public boolean authenticateUser(String username, String password) {
        // Simple authentication
        if (customers.containsKey(username) && password.equals("password123")) {
            currentCustomer = customers.get(username);
            return true;
        }
        return false;
    }

    public String getCustomerDisplayName() {
        if (currentCustomer instanceof Personal) {
            Personal personal = (Personal) currentCustomer;
            return personal.getFirstName() + " " + personal.getSurname();
        } else if (currentCustomer instanceof Company) {
            Company company = (Company) currentCustomer;
            return company.getCompanyName();
        }
        return "Customer";
    }

    // Existing methods from your previous implementation
    public void showMainDashboard() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text welcomeText = new Text("Welcome, " + getCustomerDisplayName() + "!");
        welcomeText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // FXML Navigation Button
        Button viewAccountsFXMLBtn = new Button("View Accounts (FXML)");
        viewAccountsFXMLBtn.setOnAction(e -> showAccountView());
        viewAccountsFXMLBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-pref-width: 200px;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showLoginScreen());
        logoutBtn.setStyle("-fx-pref-width: 200px;");

        vbox.getChildren().addAll(welcomeText, viewAccountsFXMLBtn, logoutBtn);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
    }

    private void showPersonalRegistrationScreen() {
        // Your existing personal registration implementation
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add registration form components...

        Button createBtn = new Button("Create Personal Customer");
        Button backBtn = new Button("Back to Login");

        createBtn.setOnAction(e -> {
            // Handle personal customer creation
            showLoginScreen();
        });

        backBtn.setOnAction(e -> showLoginScreen());

        grid.add(createBtn, 1, 7);
        grid.add(backBtn, 1, 8);

        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
    }

    private void showCompanyRegistrationScreen() {
        // Your existing company registration implementation
        // Similar structure to personal registration
        showLoginScreen(); // Temporary return to login
    }

    private void showAllAccountsScreen() {
        // Your existing programmatic accounts screen
        showMainDashboard(); // Temporary fallback
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}