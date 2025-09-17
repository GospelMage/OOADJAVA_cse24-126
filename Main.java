public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();
        LoginController loginController = new LoginController(repo);
        AccountController accountController = new AccountController(repo);

        LoginView loginView = new LoginView(loginController);
        AccountView accountView = new AccountView(accountController);

        loginView.displayLogin();
        while (true) {
            Customer current = loginView.showLoginMenu();
            if (current == null) {
                System.out.println("Goodbye.");
                break;
            }
            // Enter account menu
            accountView.showAccountMenu(current);
        }
    }
}
