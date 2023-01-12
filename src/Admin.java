import java.util.ArrayList;

class Admin extends User implements SensitiveData, LoginInfo {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(String[] parts) {
        this(parts[USERNAME],
                parts[PASSWORD]);
    }

    public void viewAllCustomers() {
        Utilities.printArrayList(loadCustomers());
    }

    @Override
    public String toString() {
        return "Admin [username=" + super.getUsername() + ", password=" + this.getPassword() + "]";
    }

    public ArrayList<Customer> loadCustomers() {
        ArrayList<String[]> loaded = Utilities.loader(SensitiveData.CUSTOMER_DETAILS);
        ArrayList<Customer> allCustomers = new ArrayList<>();
        for (String[] x : loaded) {
            allCustomers.add(new Customer(x));
        }
        return allCustomers;
    }
    public void setOrderStatus(Order order, String status) {
        order.setOrderStatus(this, status);
    }
    public void viewAllOrders() {
        Utilities.printArrayList(Order.loadOrder());
    }

    public void setCustomerID(Customer customer, String newID) {
        customer.setID(newID);
    }

    // public void setProductCategory(Product product, String newCategory) {
    // product.setCategory(newCategory);
    // }

}