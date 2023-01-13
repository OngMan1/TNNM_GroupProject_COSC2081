interface CustomerCLIIndex {
    String VIEW_CUSTOMER_INFO = "0";
    String UPDATE_CUSTOMER_INFORMATION = "1";
    String CURRENT_MEMBERSHIP_STATUS = "2";
    String VIEW_PRODUCTS = "3";
    String SEARCH_PRODUCTS = "4";
    String PLACE_ORDER = "5";
    String SEARCH_ORDERS = "6";
    String YOUR_ORDERS = "7";
}

public class CustomerCLI implements CLI, CustomerCLIIndex {

    public boolean run() {
        boolean state = true;
        Authentication session = new Authentication();
        System.out.println("CUSTOMER LOGIN: ");
        Customer currCustomer = session.Customer_Login();
        while (state) {
            UserInput.clearConsole();
            Utilities.printStringBullet(getOptions());
            System.out.println("Enter an option: ");
            String command = UserInput.getInput();
            state = executeCommand(command, currCustomer);
            System.out.println("Continue as Customer? (Y/N): ");
            if (UserInput.getConfirmation("Y", "N")) {
                state = true;
            } else {
                state = false;
            }
        }
        return state;
    }

    public boolean executeCommand(String command, Customer customer) {
        if (command == null) {
            return false;
        }
        if (customer != null) {
            switch (command) {
                case VIEW_CUSTOMER_INFO:
                    System.out.println(customer);
                case UPDATE_CUSTOMER_INFORMATION:
                    customer.updateInfo();
                    break;
                case CURRENT_MEMBERSHIP_STATUS:
                    System.out.println(String.format(
                            "Your membership: %s\nTotal Spending: %.2f",
                            customer.getCustomerMembership(), customer.getCustomerSpending()));
                    break;
                case VIEW_PRODUCTS:
                    Product.browseProducts();
                    break;
                case SEARCH_PRODUCTS:
                    CLI productSearch = new ProductSearchCLI();
                    productSearch.run();
                    break;
                case PLACE_ORDER:
                    customer.newOrder();
                    break;
                case SEARCH_ORDERS:
                    System.out.println("Enter Order ID: ");
                    String orderID = UserInput.getInput();
                    Utilities.printArrayList(Searcher.searchOrderByID(orderID));
                    break;
                case YOUR_ORDERS:
                    System.out.println("Your orders: ");
                    Utilities.printArrayList(customer.getOrders());
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        return false;

    }

    @Override
    public String[] getOptions() {
        String[] options = {
                "View Customer Information",
                "Update Customer Information",
                "Current Membership Status",
                "View all Products",
                "Search Products",
                "Place an Order",
                "Search Orders",
                "Your Orders"
        };
        return options;
    }

}