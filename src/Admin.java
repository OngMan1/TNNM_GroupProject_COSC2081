import java.util.ArrayList;

class Admin extends User implements SensitiveData, LoginInfo, ProductDetail {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(String[] parts) {
        this(parts[USERNAME],
                parts[PASSWORD]);
    }

    public void viewAllCustomers(boolean isShort) {
        ArrayList<Customer> curr = Loader.loadCustomers();

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
        return "Admin [username=" + super.getUsername() + ", " +
                "password=" + super.getPassword() + "]";
    }

    public void setOrderStatus(Order order, String status) {
        order.setOrderStatus(this, status);
    }

    public void viewAllOrders(boolean isShort) {
        ArrayList<Order> curr = Loader.loadOrder();

        if (isShort) {
            for (Order x : curr) {
                System.out.println(x.getShortOrderInfo());
            }
        } else {
            Utilities.printArrayList(curr);

        }
    }

    public ArrayList<Customer> searchCustomerByUsername(String customerUsername) {
        return Searcher.searchCustomer(customerUsername, null, null, null, null);
    }

    public ArrayList<Customer> searchCustomerByID(String customerID) {
        return Searcher.searchCustomer(null, customerID, null, null, null);
    }

    public ArrayList<Customer> searchCustomerByName(String customerName) {
        return Searcher.searchCustomer(null, null, customerName, null, null);
    }

    public ArrayList<Customer> searchCustomerBySpendingRange(double min, double max) {
        return Searcher.searchCustomer(null, null, null, new double[] { min, max }, null);
    }

    public ArrayList<Customer> searchCustomerByMembership(String customerMembership) {
        return Searcher.searchCustomer(null, null, null, null, customerMembership);
    }

    public Product addProduct(String[] productInfo) {
        if (Loader.rawSearcher(PRODUCT_DETAILS, new String[] { null, productInfo[ProductDetail.NAME] }) == null) {
            ArrayList<String[]> allProducts = Loader.rawLoader(PRODUCT_DETAILS);
            int totalProductCount = allProducts.size();
            String[] newProductInfo = {
                    Utilities.IDFormatter(totalProductCount),
                    productInfo[ProductDetail.NAME],
                    productInfo[ProductDetail.PRICE],
                    productInfo[ProductDetail.CATEGORY]
            };
            Writer.appendFile(PRODUCT_DETAILS, Writer.writeParser(newProductInfo));
            Product newProduct = new Product(newProductInfo);
            return newProduct;
        } else {
            return null;
        }
    }

    public void addProduct() {
        System.out.println("Adding new product");
        System.out.println("Enter Product Name: ");
        String productName = UserInput.getInput();
        System.out.println("Enter Product Price: ");
        Double productPrice = Double.parseDouble(UserInput.getInput());
        System.out.println("Enter Product Category: ");
        String productCategory = UserInput.getInput();

        Product newProduct = addProduct(new String[] {
                null,
                productName,
                String.valueOf(productPrice),
                productCategory,
        });
        if (newProduct != null) {
            System.out.println("Product added successfully");
        } else {
            System.out.println("Product already exist");
        }
    }
    public void setCustomerID(Customer customer, String newID) {
        customer.setID(newID);
    }


}