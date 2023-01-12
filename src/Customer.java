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

class Customer extends User implements LoginInfo, UserInfo, Membership {
    private String customerName;
    private String customerID;
    private ArrayList<Order> customerOrder = new ArrayList<>();
    private String customerMembership;


    private Customer(String username, String password, String customerID, String customerName) {
        super(username, password);
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerOrder.addAll(Order.getOrderByCustUsername(username));
        this.customerMembership = calculateMembership();
        this.customerOrder.addAll(Order.getOrderByCustUsername(username));
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
                "(%s) %s [%s:%s] == [%.2f] {{%s}}", customerID, customerName, getUsername(), getPassword(),
                calculateTotalSpending(), customerMembership);
    }

    public String[] CustomerFormat() {
        String[] formatted = {
                getUsername(),
                getPassword(),
                customerID,
                customerName,
        };
        return formatted;
    }

    public ArrayList<Order> getOrders() {
        return this.customerOrder;
    }
    public Double calculateTotalSpending() {
        Double totalSpending = 0.0;
        for (Order x : getOrders()) {
            if (x.getStatus().equals(Order.Status.DELIVERED.value))
                totalSpending += x.calculateTotal();
        }
        return totalSpending;
    }



    public String calculateMembership() {
        if (calculateTotalSpending() >= Membership.PLATINUM_THRES) {
            return Membership.PLATINUM;
        }
        if (calculateTotalSpending() >= Membership.GOLD_THRES) {
            return Membership.GOLD;
        }
        if (calculateTotalSpending() >= Membership.SILVER_THRES) {
            return Membership.SILVER;
        }
        return Membership.NORMAL;
    }

    public String getMembership() {
        return this.customerMembership;
    }

    public void setID(String newID) {
        this.customerID = newID;
    }

}