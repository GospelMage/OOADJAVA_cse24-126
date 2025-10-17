public class Personal extends Customer {
    private int nationalID;
    private int phoneNumber;
    private String email;

    public Personal(String firstName, String surname, String address, int customerId, 
                   int nationalID, int phoneNumber, String email) {
        super(firstName, surname, address, customerId);
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void updateContactInfo(String phone, String email) {
        this.phoneNumber = Integer.parseInt(phone);
        this.email = email;
    }

    public boolean verifyIdentity() {
        return nationalID > 0 && phoneNumber > 0 && email != null && !email.isEmpty();
    }

    public int getNationalID() { return nationalID; }
    public int getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
}