class Customer {
    private String username, password;
    private String customerName;
    private String customerID;

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

    private Customer(String username, String password, String customerID, String customerName) {
        this.customerID = customerID;
        this.username = username;
        this.password = password;
        this.customerName = customerName;
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

}
