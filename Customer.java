import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Authentication {
    private final String username; // unique for simple demo
    private String password;
    private String firstname;
    private String surname;
    private String address;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String username, String password, String firstname, String surname, String address) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
    }

    public String getUsername() { return username; }
    public String getFirstname() { return firstname; }
    public String getSurname() { return surname; }
    public List<Account> getAccounts() { return accounts; }

    public void addAccount(Account a) {
        if (a == null) throw new IllegalArgumentException("Account cannot be null");
        accounts.add(a);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        // no state maintained here besides the credential pair
    }

    @Override
    public String toString() {
        return String.format("%s %s (user: %s) - %d account(s)", firstname, surname, username, accounts.size());
    }
}
