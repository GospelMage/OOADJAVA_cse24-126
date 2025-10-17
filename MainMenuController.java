import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML private Button viewAccountsButton;
    @FXML private Button openInvestmentButton;
    @FXML private Button personalRegButton;
    @FXML private Button companyRegButton;
    @FXML private Button logoutButton;

    private HybridBankingSystem mainApp;

    public void setMainApp(HybridBankingSystem mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        // Set button actions
        viewAccountsButton.setOnAction(e -> mainApp.showAccountsScreen());
        openInvestmentButton.setOnAction(e -> mainApp.showInvestmentInfo());
        personalRegButton.setOnAction(e -> mainApp.showPersonalRegistration());
        companyRegButton.setOnAction(e -> mainApp.showCompanyRegistration());
        logoutButton.setOnAction(e -> mainApp.showLoginScreen());
        
        // Style the buttons
        String buttonStyle = "-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white;";
        viewAccountsButton.setStyle(buttonStyle);
        openInvestmentButton.setStyle(buttonStyle);
        personalRegButton.setStyle(buttonStyle);
        companyRegButton.setStyle(buttonStyle);
        logoutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
    }
}