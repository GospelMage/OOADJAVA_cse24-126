import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimpleBankingApp extends Application {
    
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }
    
    private void showLoginScreen() {
        // Create login form
        Label titleLabel = new Label("Banking System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> showMainMenu());
        
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, userLabel, userField, passLabel, passField, loginBtn);
        layout.setPadding(new javafx.geometry.Insets(20));
        
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Banking System - Login");
        primaryStage.show();
    }
    
    private void showMainMenu() {
        Label titleLabel = new Label("Main Menu");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Button accountsBtn = new Button("View Accounts");
        accountsBtn.setOnAction(e -> showAccounts());
        
        Button investmentBtn = new Button("Open Investment Account (P50 min)");
        investmentBtn.setOnAction(e -> showInvestmentInfo());
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showLoginScreen());
        
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, accountsBtn, investmentBtn, logoutBtn);
        layout.setPadding(new javafx.geometry.Insets(20));
        
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Banking System - Main Menu");
    }
    
    private void showAccounts() {
        Label titleLabel = new Label("Account Overview");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label balanceLabel = new Label("Current Balance: P800.00");
        
        TextArea transactionsArea = new TextArea();
        transactionsArea.setText("Deposit - P1000.00 - Balance: P1000.00\nWithdrawal - P200.00 - Balance: P800.00");
        transactionsArea.setEditable(false);
        
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        
        HBox buttonsBox = new HBox(10);
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button interestBtn = new Button("Pay Interest");
        Button backBtn = new Button("Back to Main Menu");
        backBtn.setOnAction(e -> showMainMenu());
        
        buttonsBox.getChildren().addAll(depositBtn, withdrawBtn, interestBtn);
        
        VBox layout = new VBox(15);
        layout.getChildren().addAll(titleLabel, balanceLabel, transactionsArea, 
                                  amountLabel, amountField, buttonsBox, backBtn);
        layout.setPadding(new javafx.geometry.Insets(20));
        
        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Banking System - Accounts");
    }
    
    private void showInvestmentInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Investment Account");
        alert.setHeaderText("Investment Account Requirements");
        alert.setContentText("Minimum initial deposit: P50\nPlease visit our branch to open an investment account.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}