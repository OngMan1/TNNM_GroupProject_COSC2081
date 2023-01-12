import java.util.ArrayList;


class Customer {
    private String username, password;
    private String customerName;
    private String customerID;
    private ArrayList<Order> customerOrder = new ArrayList<>();
    private String customerMembership;

    public enum CustomerConstants {    // use an enum class to initialize constant values (final vars)
        // username,password,customerID,cusName
        USERNAME(0), PASSWORD(1), CUS_ID(2), CUS_NAME(3);

        public final int VALUE;

        private CustomerConstants(int value) {
            this.VALUE = value;
        }

        public int getValue() {
            return VALUE;
        }
    }
    public class Membership {
        public enum MBSRate {
            NORMAL(0), SILVER(0.05), GOLD(0.1), PLATINUM(0.15);

            public final double rate;

            private MBSRate(double rate) {
                this.rate = rate;
            }

            public double getRate() {
                return rate;
            }

        }

        public enum MBSThreshold {
            SILVER(5_000_000), GOLD(10_000_000), PLATINUM(25_000_000);

            public final double threshold;

            private MBSThreshold(double threshold) {
                this.threshold = threshold;
            }

            public double getThreshold() {
                return threshold;
            }
        }

        public enum MBSType {
            NONE("NONE"), SILVER("SILVER"), GOLD("GOLD"), PLATINUM("PLATINUM");

            public final String type;

            private MBSType(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }
        }
    }


    private Customer(String username, String password, String customerID, String customerName) {
        this.customerID = customerID;
        this.username = username;
        this.password = password;
        this.customerName = customerName;
        this.customerOrder.addAll(Order.getOrderByCustUsername(username));
        this.customerMembership = calculateMembership();
    }

    private Customer(String[] parts) {    // take input as a split string
        this(parts[CustomerConstants.USERNAME.VALUE],
                parts[CustomerConstants.PASSWORD.VALUE],
                parts[CustomerConstants.CUS_ID.VALUE],
                parts[CustomerConstants.CUS_NAME.VALUE]);
    }

    public static Customer createCustomer(String[] parts) {
        if (parts.length == CustomerConstants.values().length) {
            return new Customer(parts);
        } else {
            System.out.println("Arguments overflow (needed " + CustomerConstants.values().length + ", got " + parts.length + ")");
            return null;
        }
    }

    public String toString() {
        return String.format(
                "(%s) %s [%s:%s]", customerID, customerName, username, password);
    }

    public String[] CustomerFormat() {
        String[] formatted = {
                username,
                password,
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
        if (calculateTotalSpending() >= Membership.MBSThreshold.PLATINUM.threshold) {
            return Membership.MBSType.PLATINUM.type;
        }
        if (calculateTotalSpending() >= Membership.MBSThreshold.GOLD.threshold) {
            return Membership.MBSType.GOLD.type;
        }
        if (calculateTotalSpending() >= Membership.MBSThreshold.SILVER.threshold) {
            return Membership.MBSType.SILVER.type;
        }
        return Membership.MBSType.NONE.type;
    }

    public String getMembership() {
        return this.customerMembership;
    }
}
