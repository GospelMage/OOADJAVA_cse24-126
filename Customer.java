import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String firstName;
    protected String surname;
    protected String address;
    protected int customerId;
    protected List<Account> accounts;

    public Customer(String firstName, String surname, String address, int customerId) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.customerId = customerId;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public int getCustomerId() { return customerId; }
    public List<Account> getAccounts() { return accounts; }
}