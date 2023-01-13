interface AdminIndex {
    String VIEW_ALL_PRODUCTS = "0";
    String VIEW_ALL_ORDERS = "1";
    String VIEW_ALL_CUSTOMERS = "2";
    String ADD_PRODUCT = "3";
    String ADD_CATEGORY = "4";
    String EDIT_PRODUCT_PRICE = "5";
    String EDIT_ORDER_STATUS = "6";
    String VIEW_ORDERS_BY_CUSTOMER = "7";
    String VIEW_ORDERS_BY_DATE = "8";
    String STATISTIC = "9";
}

public class AdminCLI implements CLI, AdminIndex {
    public boolean run() {
        boolean state = true;
        Authentication session = new Authentication();
        System.out.println("ADMIN LOGIN: ");
        Admin currAdmin = session.Admin_Login();
        while (state) {
            UserInput.clearConsole();
            Utilities.printStringBullet(getOptions());
            System.out.println("Enter an option: ");
            String command = UserInput.getInput();
            state = executeCommand(command, currAdmin);
            System.out.println("Continue? (Y/N): ");
            if (UserInput.getConfirmation("Y", "N")) {
                state = true;
            }
        }
        return state;

    }

    public boolean executeCommand(String command, Admin admin) {
        if (admin != null) {
            switch (command) {
                case VIEW_ALL_PRODUCTS:
                    Product.browseProducts();
                    break;
                case VIEW_ALL_ORDERS:
                    System.out.println("Include products? (Y/N): ");
                    boolean viewProduct = UserInput.getConfirmation("Y", "N");
                    admin.viewAllOrders(viewProduct);
                    break;
                case VIEW_ALL_CUSTOMERS:
                    System.out.println("Include user accounts? (Y/N): ");
                    boolean viewAccount = UserInput.getConfirmation("Y", "N");
                    admin.viewAllCustomers(viewAccount);
                    break;
                case ADD_PRODUCT:
                    admin.addProduct();
                    break;
                case ADD_CATEGORY:
                    admin.addCategory();
                    break;
                case EDIT_PRODUCT_PRICE:
                    admin.editProductInfo();
                    break;
                case EDIT_ORDER_STATUS:
                    admin.changeOrderStatus();
                    break;
                case VIEW_ORDERS_BY_CUSTOMER:
                    admin.getOrdersByCustomer();
                    break;
                case VIEW_ORDERS_BY_DATE:
                    admin.getOrdersByDate();
                    break;
                case STATISTIC:

                default:
                    System.out.println("Invalid option, Please enter a valid option");
                    break;
            }
        }
        return false;

    }

    @Override
    public String[] getOptions() {
        String[] options = {
                "View all Products",
                "View all Orders",
                "View all Customers",
                "Add a Product",
                "Add a Category",
                "Edit a Product's Price",
                "Edit an Order's Status",
                "View Orders by Customer",
                "View Orders by Date",
                "Statistic functions"
        };
        return options;
    }
}