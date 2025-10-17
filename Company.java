public class Company extends Customer {
    private String companyName;
    private int contactNumber;
    private String businessType;
    private int taxNumber;
    private String licenseNo;

    public Company(String companyName, String address, int customerId, 
                  int contactNumber, String businessType, int taxNumber, String licenseNo) {
        super(companyName, "", address, customerId);
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.businessType = businessType;
        this.taxNumber = taxNumber;
        this.licenseNo = licenseNo;
    }

    public void supportedEmployeeSalaryPayment(double amount) {
        System.out.println("Processing salary payment: " + amount);
    }

    public void generateInvoice(String clientName, double amount) {
        System.out.println("Invoice for " + clientName + ": " + amount);
    }

    public boolean verifyBusinessRegistration() {
        return taxNumber > 0 && licenseNo != null && !licenseNo.isEmpty();
    }

    public String getCompanyName() { return companyName; }
    public int getContactNumber() { return contactNumber; }
    public String getBusinessType() { return businessType; }
    public int getTaxNumber() { return taxNumber; }
    public String getLicenseNo() { return licenseNo; }
}