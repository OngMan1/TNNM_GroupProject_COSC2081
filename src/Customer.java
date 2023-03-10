/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author 1: Ong Gia Man (s3938231)
  Author 2: Nguyen Le Thu Nhan (s3932151)
  Author 3: Tran Minh Nhat (s3926629)
  Author 4: Nguyen Ngoc Minh Thu (s3941327)
  Date: 01/2023
  Acknowledgement: Acknowledge the resources that you use here.
*/
import java.util.ArrayList;

interface UserInfo {
    int USERNAME = 0;
    int PASSWORD = 1;
    int ID = 2;
    int NAME = 3;
}

interface Membership {
    double NORMAL_RATE = 0;
    double SILVER_RATE = 0.05;
    double GOLD_RATE = 0.1;
    double PLATINUM_RATE = 0.15;

    double SILVER_THRES = 5_000_000;
    double GOLD_THRES = 10_000_000;
    double PLATINUM_THRES = 25_000_000;

    String NORMAL = "NORMAL";
    String SILVER = "SILVER";
    String GOLD = "GOLD";
    String PLATINUM = "PLATINUM";
}

class Customer extends User implements LoginInfo, UserInfo, OrderStatus {
    private String customerID;
    private String customerName;
    private ArrayList<Order> customerOrder = new ArrayList<>();
    private String customerMembership;

    private Customer(String username, String password, String customerID, String customerName) {
        super(username, password);
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerOrder.addAll(Searcher.searchOrderByUsername(username));
        this.customerMembership = calculateMembership();
    }

    public Customer(String[] parts) {
        this(parts[UserInfo.USERNAME],
                parts[UserInfo.PASSWORD],
                parts[UserInfo.ID],
                parts[UserInfo.NAME]);
    }

    public String toString() {
        return String.format(
                "%s %s == [%.2f] {{%s}}",
                getCustomerInfo(), getCustomerAccount(),
                getCustomerSpending(), getCustomerMembership());
    }

    public String getCustomerInfo() {
        return String.format("(%s) %s", getCustomerID(), getCustomerName());
    }

    public String getCustomerAccount() {
        return String.format("[%s:%s]", getUsername(), getPassword());
    }

    public String[] CustomerFormat() {
        String[] formatted = {
                getUsername(),
                getPassword(),
                getCustomerID(),
                getCustomerName(),
        };
        return formatted;
    }

    public ArrayList<Order> getOrders() {
        return this.customerOrder;
    }

    public double getCustomerSpending() {
        double totalSpending = 0;
        ArrayList<Order> thisOrders = getOrders();
        ArrayList<String> orderIDs = Utilities.getAllOrderIDs(thisOrders);
        ArrayList<OrderTotals> allTotals = Loader.loadOrderTotals();
        for (OrderTotals x : allTotals) {
            if (orderIDs.contains(x.getOrderID())) {
                totalSpending += x.getOrderTotals();
            }
        }
        return totalSpending;
    }

    public String calculateMembership() {
        double totalSpending = getCustomerSpending();
        if (totalSpending >= Membership.PLATINUM_THRES) {
            return Membership.PLATINUM;
        }
        if (totalSpending >= Membership.GOLD_THRES) {
            return Membership.GOLD;
        }
        if (totalSpending >= Membership.SILVER_THRES) {
            return Membership.SILVER;
        }
        return Membership.NORMAL;
    }

    public String getCustomerMembership() {
        return this.customerMembership;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean setCustomerUsername(String newUsername) {
        if (Loader.rawSearcher(SensitiveData.CUSTOMER_DETAILS, new String[] { newUsername }, true) != null) {
            System.out.println("Username already exist!");
            return false;
        }
        this.setUsername(newUsername);
        return true;
    }

    public void updateInfo(String newUsername, String newPassword, String newName) {
        String oldUsername = getUsername(), oldPassword = getPassword();
        if (newUsername != null) {
            if (!setCustomerUsername(newUsername)) {
                System.out.println("Username did not change");
            }
        }
        if (newPassword != null) {
            this.setPassword(newPassword);
        }
        if (newName != null) {
            this.setCustomerName(newName);
        }

        Writer.replaceLine(SensitiveData.CUSTOMER_DETAILS,
                String.format("%s%s%s", oldUsername, Delimiter.TEXT_DELIMITER, oldPassword),
                Writer.writeParser(this.CustomerFormat()));
    }

    public void updateInfo() {
        System.out.println("(Leave empty [Enter] to skip any field)");
        System.out.println("Enter username: ");
        String newUsername = UserInput.getInput();
        System.out.println("Enter password: ");
        String newPassword = UserInput.getInput();
        System.out.println("Enter name: ");
        String newName = UserInput.getInput();
        updateInfo(newUsername, newPassword, newName);
        System.out.println(this);
    }

    public void newOrder() {
        ArrayList<Product> cart = Product.addToCart();
        if (cart.size() == 0) {
            System.out.println("Cart is empty. Cancel.");
            return;
        }
        Order newOrder = newOrder(cart);
        System.out.println("Order successfully placed!");
        this.addOrder(newOrder);
        System.out.println(newOrder);
        System.out.println("View all orders? (Y/N)");
        if (UserInput.getConfirmation("Y", "N")) {
            Utilities.printArrayList(getOrders());
        }
    }

    public Order newOrder(ArrayList<Product> cart) {
        int totalOrders = Loader.loadOrder().size();
        Order order = new Order(getUsername(), Utilities.IDFormatter(totalOrders), cart, PLACED);
        Writer.appendFile(OrderInfo.ORDER_DETAILS, Writer.writeParser(
                order.getWriteFormat()));
        for (String[] x : order.getOrderProductWriteFormat()) {
            Writer.appendFile(OrderProduct.ORDER_PRODUCT, Writer.writeParser(x));
            Writer.appendFile(OrderProduct.ORDER_PRODUCT, "\n");
        }
        return order;
    }

    public void addOrder(Order order) {
        this.customerOrder.add(order);
    }

}