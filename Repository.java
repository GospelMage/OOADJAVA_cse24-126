import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository {
    // simple in-memory store; replace with database/file later
    private final List<Customer> customers = new ArrayList<>();

    public void saveCustomer(Customer c) {
        customers.add(c);
    }

    public Optional<Customer> findByUsername(String username) {
        return customers.stream().filter(c -> c.getUsername().equals(username)).findFirst();
    }

    // helpful for listing/search in demo
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
}
