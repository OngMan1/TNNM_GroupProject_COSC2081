import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;

class Admin extends User implements SensitiveData, LoginInfo, ProductDetail, CategoryDetails, IDFormat, Order_Totals {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(String[] parts) {
        this(parts[USERNAME],
                parts[PASSWORD]);
    }

    public void viewAllCustomers(boolean withAccount) {
        ArrayList<Customer> curr = Loader.loadCustomers();

        if (!withAccount) {
            for (Customer x : curr) {
                System.out.println(String.format("%s == [%.2f] {{%s}}",
                        x.getCustomerInfo(), x.getCustomerSpending(), x.getCustomerMembership()));
            }
        } else {
            Utilities.printArrayList(curr);

        }
    }

    @Override
    public String toString() {
        return "Admin [username=" + super.getUsername() + ", password=" + super.getPassword() + "]";
    }

    public Order changeOrderStatus(String orderID, String orderStatus) {
        ArrayList<Order> orders = Searcher.searchOrderByID(orderID);
        if (orders.isEmpty()) {
            System.out.println("Didn't find Order with ID " + orderID);
            return null;
        }
        if (orders.size() != 1) {
            System.out.println("Err: Multiple orders with same ID");
            return null;
        }
        Order order = orders.get(0);
        if (!order.setOrderStatus(orderStatus)) {
            System.out.println("Order status did not change");
            return order;
        }
        Writer.replaceLine(OrderInfo.ORDER_DETAILS,
                String.format("%s%s%s", order.getOrderUsername(), Delimiter.TEXT_DELIMITER, order.getOrderID()),
                Writer.writeParser(order.getWriteFormat()));
        if (Loader.rawSearcher(ORDER_TOTALS, new String[] { order.getOrderID() }) != null) {
            System.out.println("Err: Order Total already exists");
            return null;
        }
        Writer.appendFile(ORDER_TOTALS, Writer
                .writeParser((new OrderTotals(order.getOrderID(), order.getOrderDiscountTotal())).getWriteFormat()));

        return order;
    }

    public void changeOrderStatus() {
        String orderID;
        Order oldOrder;
        while (true) {
            System.out.println("Enter Order ID");
            orderID = UserInput.getInput();
            ArrayList<Order> orders = Searcher.searchOrderByID(orderID);
            if (orders.size() == 1) {
                oldOrder = orders.get(0);
                break;
            }
            System.out.println("Couldn't find order with ID " + orderID + ". Try again? (Y/N): ");
            if (!UserInput.getConfirmation("Y", "N")) {
                return;
            }
        }
        System.out.println(oldOrder);
        System.out.println("Change to " + OrderStatus.DELIVERED + "? (Y/N): ");
        if (UserInput.getConfirmation("Y", "N") && oldOrder.getOrderStatus().equals(OrderStatus.PLACED)) {
            System.out.println("New order: ");
            System.out.println(oldOrder);
            System.out.println("Confirm changes? (Y/N):");
            if (UserInput.getConfirmation("Y", "N")) {
                Order newOrder = new Order(new String[] {
                        oldOrder.getOrderUsername(),
                        oldOrder.getOrderID(),
                        OrderStatus.DELIVERED,
                        oldOrder.getOrderDate().format(DateFormat.dtf)
                });
                Writer.replaceLine(OrderInfo.ORDER_DETAILS,
                        String.format("%s%s%s", oldOrder.getOrderUsername(), Delimiter.TEXT_DELIMITER,
                                oldOrder.getOrderID()),
                        Writer.writeParser(newOrder.getWriteFormat()));

                Writer.appendFile(ORDER_TOTALS, Writer
                        .writeParser(
                                (new OrderTotals(newOrder.getOrderID(), newOrder.getOrderDiscountTotal()))
                                        .getWriteFormat()));
                System.out.println("Order status changed!");
                return;
            }

        }
        System.out.println("Order status did not change");
    }

    public void viewAllOrders(boolean withProductsInfo) {
        ArrayList<Order> curr = Loader.loadOrder();

        if (!withProductsInfo) {
            for (Order x : curr) {
                System.out.println(x.getOrderInfo());
            }
        } else {
            Utilities.printArrayList(curr);

        }
    }

    public Product addProduct(String[] productInfo) {
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
    }

    public void addProduct() {
        System.out.println("Adding new product");
        String productName;
        while (true) {
            System.out.println("Enter Product Name: ");
            productName = UserInput.getInput();
            if (Loader.rawSearcher(PRODUCT_DETAILS, new String[] { null, productName }, false) == null) {
                break;
            }
            System.out.println("Product already exists. Try again? (Y/N): ");
            if (!UserInput.getConfirmation("Y", "N")) {
                return;
            }
        }

        Double productPrice;
        while (true) {
            System.out.println("Enter Product Price: ");
            productPrice = UserInput.getDoubleFromInput();
            if (productPrice != null) {
                break;
            }
            System.out.println("Invalid Price. Try again? (Y/N): ");
            if (!UserInput.getConfirmation("Y", "N")) {
                return;
            }
        }

        System.out.println("Enter Product Category ID or Name: ");
        String productCategory = UserInput.getInput();
        Category category = getCategory(productCategory);

        while (category == null) {
            System.out.println("Category not found. Add new category? (Y/N)");
            if (UserInput.getConfirmation("Y", "N")) {
                category = addCategory();
            } else {
                System.out.println("Product not created (No category)");
                return;
            }
        }

        Product newProduct = addProduct(new String[] {
                null,
                productName,
                String.valueOf(productPrice),
                category.getCategoryID()
        });

        if (newProduct != null) {
            System.out.println("Product added successfully");
            System.out.println(newProduct);
        } else {
            System.out.println("Product already exists");
        }
    }

    private Category getCategory(String productCategory) {
        Category category = null;

        if (Utilities.containsPattern(IDFormat.numbersRegex, productCategory)) {
            category = Searcher.searchCategory(productCategory, null);
        } else {
            category = Searcher.searchCategory(null, productCategory);
        }
        return category;
    }

    public Category addCategory(String categoryName) {
        ArrayList<String[]> allCategory = Loader.rawLoader(CATEGORY_DETAIL);
        int totalCategoriesCount = allCategory.size();
        String[] newCategoryInfo = {
                Utilities.IDFormatter(totalCategoriesCount),
                categoryName
        };
        Writer.appendFile(CATEGORY_DETAIL, Writer.writeParser(newCategoryInfo));
        Category newCategory = new Category(newCategoryInfo);
        return newCategory;
    }

    public Category addCategory() {
        System.out.println("Adding new category");
        System.out.println("Enter Category Name: ");
        String categoryName = UserInput.getInput();

        Category newCategory = addCategory(categoryName);
        if (newCategory != null) {
            System.out.println("Category added sucessfully");
            System.out.println(newCategory);
            return newCategory;
        } else {
            System.out.println("Category already exist");
            return null;
        }
    }

    public Product editProductInfo(Product product, Double productPrice, String CategoryID) {
        Double newPrice = productPrice;
        String newCategoryID = CategoryID;
        if (newPrice == null) {
            newPrice = product.getProductPrice();
        }
        if (newCategoryID == null) {
            newCategoryID = product.getProductCategoryID();
        }
        Product tmp = new Product(product.getProductID(), product.getProductName(), newPrice, newCategoryID);
        return tmp;
    }

    public void editProductInfo() {
        Product productEdit;
        while (true) {
            System.out.println("Enter Product ID or Name:");
            String productID = UserInput.getInput();
            ArrayList<Product> tmp = Searcher.searchProductByNameOrID(productID);
            if (tmp.size() == 1) {
                productEdit = tmp.get(0);
                break;
            }
            System.out.println("Product does not exists. Try again? (Y/N): ");
            if (!UserInput.getConfirmation("Y", "N")) {
                return;
            }
        }
        Double productPrice;
        while (true) {
            System.out.println("Enter Product Price: ");
            productPrice = UserInput.getDoubleFromInput();
            if (productPrice != null) {
                break;
            }
            System.out.println("Invalid Price. Try again? (Y/N): ");
            if (!UserInput.getConfirmation("Y", "N")) {
                return;
            }
        }
        Product productNew = editProductInfo(productEdit, productPrice, productEdit.getProductCategoryID());
        System.out.println("Old Product info: ");
        System.out.println(productEdit);
        System.out.println("New Product info: ");
        System.out.println(productNew);
        System.out.println("Confirm changes? (Y/N):");
        if (UserInput.getConfirmation("Y", "N")) {
            Writer.replaceLine(PRODUCT_DETAILS, productNew.getProductID(),
                    Writer.writeParser(productNew.getWriteFormat()));
            return;
        }
        System.out.println("Product info not changed");

    }

    public Double getTotalRevenue() {
        ArrayList<OrderTotals> allOrderTotals = Loader.loadOrderTotals();
        Double totalRevenue = 0.0;
        for (OrderTotals x : allOrderTotals) {
            totalRevenue += x.getOrderTotals();
        }
        return totalRevenue;
    }

    public double getRevenueOnDay(String date) {
        ArrayList<Order> allOrderOnDate = Searcher.searchOrderByDate(date);
        double totalRevenue = 0;
        for (Order x : allOrderOnDate) {
            OrderTotals tmp = Searcher.searchOrderTotalFromOrderID(x.getOrderID());
            if (tmp != null) {
                totalRevenue += tmp.getOrderTotals();
            }
        }
        return totalRevenue;
    }

    private class ProductSalePair {
        Product product;
        int sale;

        public ProductSalePair(Product product, int sale) {
            this.product = product;
            this.sale = sale;
        }

        public String toString() {
            return String.format("(%s) %s:\t%s", product.getProductID(), product.getProductName(), sale);
        }

        static Comparator<ProductSalePair> bySales() {
            return new Comparator<ProductSalePair>() {
                @Override
                public int compare(ProductSalePair p1, ProductSalePair p2) {
                    return p1.sale - p2.sale;
                }
            };
        }

        static Comparator<ProductSalePair> bySalesDescending() {
            return new Comparator<ProductSalePair>() {
                @Override
                public int compare(ProductSalePair p1, ProductSalePair p2) {
                    return p2.sale - p1.sale;
                }
            };
        }

    }

    public void sortProductBySales(boolean isDescending) {
        ArrayList<Product> allProducts = Loader.loadProduct();
        ArrayList<Product> allProductFromOrder = new ArrayList<>();
        ArrayList<Order> allOrders = Loader.loadOrder();
        for (Order x : allOrders) {
            if (x.getOrderStatus().equals(OrderStatus.DELIVERED)) {
                for (Product y : x.getOrderProducts()) {
                    allProductFromOrder.add(y);
                }
            }
        }
        ArrayList<ProductSalePair> productSales = new ArrayList<>();
        for (Product x : allProducts) {
            productSales.add(new ProductSalePair(x, Utilities.countOccurence(allProductFromOrder, x)));
        }
        if (isDescending) {
            productSales.sort(ProductSalePair.bySalesDescending());
        } else {
            productSales.sort(ProductSalePair.bySales());
        }
        System.out.println("ID    Name\tSales");
        for (ProductSalePair x : productSales) {
            System.out.println(x);
        }

    }

    public void getOrdersByDate() {
        ArrayList<Order> tmp;
        System.out.println("Enter date (" + DateFormat.dateFormat + ")");
        String date = UserInput.getInput();
        tmp = Searcher.searchOrderByDate(date);
        for (Order x : tmp) {
            System.out.println(x);
        }
    }

    public void numberOfMembershipType() {
        Field[] membershipTypes = Membership.class.getDeclaredFields();
        ArrayList<String> allMembershipTypes = new ArrayList<>();
        for (Field x : membershipTypes) {
            if (x.getType() == String.class) {
                allMembershipTypes.add(x.getName());
            }
        }
        Utilities.printArrayBullet(allMembershipTypes);
        System.out.println("There are " + allMembershipTypes.size() + " types of membership");
    }

    public void getOrdersByCustomer() {
        ArrayList<Customer> tmp;
        System.out.println("Search by Username or Name? (USER/NAME): ");
        if (UserInput.getConfirmation("USER", "NAME")) {
            System.out.println("Enter Username: ");
            String username = UserInput.getInput();
            tmp = Searcher.searchCustomerByUsername(username);
        } else {
            System.out.println("Enter Name: ");
            String name = UserInput.getInput();
            tmp = Searcher.searchCustomerByName(name);
        }
        for (Customer x : tmp) {
            System.out.println(x.getCustomerInfo());
            for (Order y : x.getOrders()) {
                System.out.println(y);
            }
        }
    }

    private class CustomerSpending {
        Customer customer;
        Double spending;

        public CustomerSpending(Customer customer, double spending) {
            this.customer = customer;
            this.spending = spending;
        }

        public String toString() {
            return String.format("(%s) %-15s\t%-10.2f", customer.getCustomerID(), customer.getCustomerName(), spending,
                    customer.getCustomerMembership());
        }

        static Comparator<CustomerSpending> bySpending() {
            return new Comparator<CustomerSpending>() {
                @Override
                public int compare(CustomerSpending p1, CustomerSpending p2) {
                    return p1.spending.compareTo(p2.spending);
                }
            };
        }

        static Comparator<CustomerSpending> bySpendingDescending() {
            return new Comparator<CustomerSpending>() {
                @Override
                public int compare(CustomerSpending p1, CustomerSpending p2) {
                    return p2.spending.compareTo(p1.spending);
                }
            };
        }

    }

    public void sortCustomerBySpending(boolean isDescending) {
        ArrayList<Customer> allCustomers = Loader.loadCustomers();
        ArrayList<CustomerSpending> allSpending = new ArrayList<>();
        for (Customer x : allCustomers) {
            allSpending.add(new CustomerSpending(x, x.getCustomerSpending()));
        }

        if (isDescending) {
            allSpending.sort(CustomerSpending.bySpendingDescending());
        } else {
            allSpending.sort(CustomerSpending.bySpending());
        }
        System.out.printf("ID    Name%-14sSpending\n", " ");
        for (CustomerSpending x : allSpending) {
            System.out.println(x);
        }

    }

}