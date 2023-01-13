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

class Customer extends User implements LoginInfo, UserInfo, Membership, OrderStatus {
    private String customerName;
    private String customerID;
    private ArrayList<Order> customerOrder = new ArrayList<>();
    private String customerMembership;

    private Customer(String username, String password, String customerID, String customerName) {
        super(username, password);
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerOrder.addAll(Searcher.searchOrderByUsername(username));
        this.customerMembership = calculateMembership();
    }

    public Customer(String[] parts) {    // take input as a split string
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
        for (Order x : getOrders()) {
            if (x.getOrderStatus().equals(DELIVERED))
                totalSpending += x.calculateTotal() - x.getOrderDiscount();
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

    public void setCustomerID(String newID) {
        this.customerID = newID;
    }
}