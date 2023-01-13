import java.util.ArrayList;

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
        return "Admin [username=" + super.getUsername() + ", " +
                "password=" + super.getPassword() + "]";
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

    //public void setCustomerID(Customer customer, String newID) {
        //customer.setCustomerID(newID);
    //}

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
        double productPrice = Double.parseDouble(UserInput.getInput());

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
        if (IDFormat.numbersRegex.matches(productCategory)) {
            category = Searcher.searchCategory(productCategory, null);
        } else {
            category = Searcher.searchCategory(null, productCategory);
        }
        return category;
    }

    public Category addCategory(String categoryName) {
        if (Loader.rawSearcher(CATEGORY_DETAIL, new String[] { null, categoryName }) == null) {
            ArrayList<String[]> allCategory = Loader.rawLoader(CATEGORY_DETAIL);
            int totalCategoriesCount = allCategory.size();
            String[] newCategoryInfo = {
                    Utilities.IDFormatter(totalCategoriesCount),
                    categoryName
            };
            Writer.appendFile(CATEGORY_DETAIL, Writer.writeParser(newCategoryInfo));
            Category newCategory = new Category(newCategoryInfo);
            return newCategory;
        } else {
            return null;
        }
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
}