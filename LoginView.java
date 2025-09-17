import java.util.Scanner;

public class LoginView {
    private final Scanner scanner = new Scanner(System.in);
    private final LoginController loginController;

    public LoginView(LoginController lc) {
        this.loginController = lc;
    }

    public void displayLogin() {
        System.out.println("=== Welcome to Demo Bank ===");
    }

    public Customer showLoginMenu() {
        while (true) {
            System.out.println("\n1) Login\n2) Register\n3) Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Username: ");
                    String u = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String p = scanner.nextLine().trim();
                    Customer c = loginController.authenticateUser(u, p);
                    if (c != null) {
                        System.out.println("Login successful. Hello " + c.getFirstname());
                        return c;
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case "2":
                    System.out.print("Choose username: ");
                    String un = scanner.nextLine().trim();
                    System.out.print("Choose password: ");
                    String pwd = scanner.nextLine().trim();
                    System.out.print("First name: ");
                    String fn = scanner.nextLine().trim();
                    System.out.print("Surname: ");
                    String sn = scanner.nextLine().trim();
                    System.out.print("Address: ");
                    String addr = scanner.nextLine().trim();
                    boolean ok = loginController.register(un, pwd, fn, sn, addr);
                    if (ok) System.out.println("Registered. Please login.");
                    else System.out.println("Username already exists.");
                    break;
                case "3":
                    return null;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
