import java.util.ArrayList;
class Admin {
    private String username;
    private String password;

    public enum Ad {
        // username,password,custID,custName
        A_username(0), A_password(1);

        public final int value;

        private Ad(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin(String[] parts) {
        this(parts[Ad.A_username.value],
                parts[Ad.A_password.value]);
    }

    public void viewAllCustomers() {
        Utilities.printArrayList(loadCustomers());
    }

    @Override
    public String toString() {
        return "Admin [username=" + username + ", password=" + password + "]";
    }

    public ArrayList<Customer> loadCustomers() {
        ArrayList<String[]> loaded = Utilities.loader(Authentication.getUserFile(this));
        ArrayList<Customer> allCustomers = new ArrayList<>();
        for (String[] x : loaded) {
            allCustomers.add(Customer.createCustomer(x));
        }
        return allCustomers;
    }

}
