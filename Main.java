import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HybridBankingSystem bankingApp = new HybridBankingSystem(primaryStage);
        bankingApp.showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}