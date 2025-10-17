import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerPersonalButton;
    @FXML private Button registerCompanyButton;
    @FXML private Label messageLabel;

    private HybridBankingSystem mainApp;

    public void setMainApp(HybridBankingSystem mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        registerPersonalButton.setOnAction(e -> mainApp.showPersonalRegistration());
        registerCompanyButton.setOnAction(e -> mainApp.showCompanyRegistration());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            mainApp.showMainMenu();
        } else {
            messageLabel.setText("Please enter both username and password");
        }
    }
}