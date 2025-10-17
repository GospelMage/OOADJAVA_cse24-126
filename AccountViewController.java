import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountViewController {

    @FXML private TableView<String> accountsTable;
    @FXML private TableView<String> transactionsTable;
    @FXML private Button backButton;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;
    @FXML private Button payInterestButton;
    @FXML private Button calculateInterestButton;
    @FXML private TextField amountField;
    @FXML private Label balanceLabel;
    @FXML private Label interestRateLabel;

    private HybridBankingSystem mainApp;

    public void setMainApp(HybridBankingSystem mainApp) {
        this.mainApp = mainApp;
        updateDisplay();
    }

    @FXML
    private void initialize() {
        backButton.setOnAction(e -> mainApp.showMainMenu());
        depositButton.setOnAction(e -> handleDeposit());
        withdrawButton.setOnAction(e -> handleWithdraw());
        payInterestButton.setOnAction(e -> handlePayInterest());
        calculateInterestButton.setOnAction(e -> handleCalculateInterest());
    }

    private void updateDisplay() {
        if (mainApp != null && mainApp.getCurrentCustomer() != null) {
            // Update accounts table with real data
            accountsTable.setItems(mainApp.getAccountsData());
            
            // Update transactions table with real data
            transactionsTable.setItems(mainApp.getTransactionsData());
            
            // Update balance label
            double totalBalance = mainApp.getTotalBalance();
            balanceLabel.setText(String.format("Current Balance: P%.2f", totalBalance));
            
            // Update interest rate label
            double avgInterestRate = mainApp.getAverageInterestRate();
            interestRateLabel.setText(String.format("Average Interest Rate: %.1f%%", avgInterestRate));
        }
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                // Get selected account from table
                String selectedAccount = accountsTable.getSelectionModel().getSelectedItem();
                if (selectedAccount != null) {
                    // Extract account number from the table string
                    String accountNumber = extractAccountNumber(selectedAccount);
                    mainApp.handleDepositFromController(amount, accountNumber);
                    updateDisplay(); // Refresh the display
                    amountField.clear();
                } else {
                    showAlert("Error", "Please select an account first.");
                }
            } else {
                showAlert("Error", "Please enter a positive amount");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount");
        }
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                // Get selected account from table
                String selectedAccount = accountsTable.getSelectionModel().getSelectedItem();
                if (selectedAccount != null) {
                    // Extract account number from the table string
                    String accountNumber = extractAccountNumber(selectedAccount);
                    mainApp.handleWithdrawFromController(amount, accountNumber);
                    updateDisplay(); // Refresh the display
                    amountField.clear();
                } else {
                    showAlert("Error", "Please select an account first.");
                }
            } else {
                showAlert("Error", "Please enter a positive amount");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount");
        }
    }

    private void handlePayInterest() {
        if (mainApp.getCurrentCustomer() != null) {
            // Pay interest to all interest-bearing accounts
            for (Account account : mainApp.getCurrentCustomer().getAccounts()) {
                if (account instanceof InterestInterface) {
                    InterestInterface interestAccount = (InterestInterface) account;
                    double balanceBefore = account.viewBalance();
                    interestAccount.payInterest();
                    double balanceAfter = account.viewBalance();
                    double interestPaid = balanceAfter - balanceBefore;
                    
                    if (interestPaid > 0) {
                        showAlert("Interest Paid", 
                            String.format("💰 INTEREST PAID\n\nAccount: %s\nInterest: P%.2f", 
                                account.getAccountNumber(), interestPaid));
                    }
                }
            }
            updateDisplay();
        }
    }

    private void handleCalculateInterest() {
        if (mainApp.getCurrentCustomer() != null) {
            StringBuilder interestInfo = new StringBuilder("💰 PROJECTED INTEREST\n\n");
            
            for (Account account : mainApp.getCurrentCustomer().getAccounts()) {
                if (account instanceof InterestInterface) {
                    InterestInterface interestAccount = (InterestInterface) account;
                    double currentBalance = account.viewBalance();
                    double interestRate = interestAccount.getInterestRate();
                    double projectedInterest = currentBalance * interestRate / 100;
                    
                    interestInfo.append(String.format("%s: P%.2f at %.1f%% = P%.2f\n",
                        account.getAccountNumber(),
                        currentBalance,
                        interestRate,
                        projectedInterest));
                }
            }
            
            showAlert("Interest Calculation", interestInfo.toString());
        }
    }

    // Helper method to extract account number from table string
    private String extractAccountNumber(String accountString) {
        // Assuming format: "💰 Savings Account - 1234567890 - Balance: P500.00 - Interest: 2.5%"
        String[] parts = accountString.split(" - ");
        if (parts.length >= 2) {
            return parts[1].trim();
        }
        return "";
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}