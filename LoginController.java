public class LoginController {
    private final Repository repository;

    public LoginController(Repository repo) {
        this.repository = repo;
    }

    // register new customer
    public boolean register(String username, String password, String firstname, String surname, String address) {
        if (repository.findByUsername(username).isPresent()) {
            return false; // already exists
        }
        Customer c = new Customer(username, password, firstname, surname, address);
        repository.saveCustomer(c);
        return true;
    }

    // authenticate and return the Customer if successful
    public Customer authenticateUser(String username, String password) {
        return repository.findByUsername(username)
                .filter(c -> c.login(username, password))
                .orElse(null);
    }
}
