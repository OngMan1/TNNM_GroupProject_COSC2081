import java.util.ArrayList;

class Admin extends User implements SensitiveData, LoginInfo {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(String[] parts) {
        this(parts[USERNAME],
                parts[PASSWORD]);
    }

    public void viewAllCustomers(boolean isShort) {
        ArrayList<Customer> curr = loadCustomers();

        if (isShort) {
            for (Customer x : curr) {
                System.out.println(String.format("%s == [%.2f] {{%s}}",
                        x.getCustomerInfo(), x.getCustomerSpending(), x.getMembership()));
            }
        } else {
            Utilities.printArrayList(curr);

        }
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

    public void viewAllOrders(boolean isShort) {
        ArrayList<Order> curr = Order.loadOrder();

        if (isShort) {
            for (Order x : curr) {
                System.out.println(x.getShortOrderInfo());
            }
        } else {
            Utilities.printArrayList(curr);

        }
    }

    public ArrayList<Customer> searchCustomerByUsername(String customerUsername) {
        return searchCustomer(customerUsername, null, null, null, null);
    }

    public ArrayList<Customer> searchCustomerByID(String customerID) {
        return searchCustomer(null, customerID, null, null, null);
    }

    public ArrayList<Customer> searchCustomerByName(String customerName) {
        return searchCustomer(null, null, customerName, null, null);
    }

    public ArrayList<Customer> searchCustomerBySpendingRange(double min, double max) {
        if (min >= 0 && max >= min) {
            double[] range = { min, max };
            searchCustomerBySpendingRange(range);
        }
        return null;
    }

    public ArrayList<Customer> searchCustomerByMembership(String customerMembership) {
        return searchCustomer(null, null, null, null, customerMembership);
    }

    private ArrayList<Customer> searchCustomerBySpendingRange(double[] customerSpendingRange) {
        return searchCustomer(null, null, null, customerSpendingRange, null);
    }

    private ArrayList<Customer> searchCustomer(String customerUsername, String customerID, String customerName,
                                               double[] customerSpendingRange,
                                               String customerMembership) {
        ArrayList<Customer> curr = loadCustomers();
        ArrayList<Customer> res = new ArrayList<>();
        for (Customer x : curr) {
            if ((customerUsername != null && x.getUsername().equals(customerUsername)) ||
                    (customerID != null && x.getID().equals(customerID)) ||
                    (customerName != null && x.getName().equals(customerName)) ||
                    (customerSpendingRange != null
                            && Utilities.isInRange(x.getCustomerSpending(), customerSpendingRange))
                    ||
                    (customerMembership != null && x.getMembership().equals(customerMembership))) {
                res.add(x);
            }
        }
        return res;

    }

    public void setCustomerID(Customer customer, String newID) {
        customer.setID(newID);
    }

    // public void setProductCategory(Product product, String newCategory) {
    // product.setCategory(newCategory);
    // }

}