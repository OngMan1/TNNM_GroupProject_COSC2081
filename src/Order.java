import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {

    private static final String ORDER_DETAILS = "order_details.txt";
    private static final String ORDER_PRODUCT = "order_products.txt";
    private String OrderID;
    private ArrayList<Product> OrderProducts;
    private String OrderStatus = null;
    private String userName;
    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private String OrderDate = LocalDate.now().format(dtf);

    public enum Or {
        // username:id:status:date
        O_username(0), O_ID(1), O_Status(2), O_Date(3);

        public final int value;

        private Or(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Status {
        PLACED("placed"), DELIVERED("delivered");

        public final String value;

        private Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Order(String userName, String orderID, ArrayList<Product> orderProducts, String orderStatus) {
        this.userName = userName;
        OrderID = orderID;
        OrderProducts = orderProducts;
        OrderStatus = orderStatus;
    }

    public Order(String userName, String orderID, ArrayList<Product> orderProducts, String orderStatus,
                 LocalDate orderDate) {
        this(userName, orderID, orderProducts, orderStatus);
        this.OrderDate = orderDate.format(dtf);
    }

    public Order(String[] parts) {
        this(parts[Or.O_username.value],
                parts[Or.O_ID.value],
                loadOrderProduct(parts[Or.O_ID.value]),
                parts[Or.O_Status.value],
                LocalDate.parse(parts[Or.O_Date.value], dtf));
    }

    @Override
    public String toString() {
        String formatted = String.format(
                "(%s) {%s} [%s] - %s === Total: [%.2f]\n",
                userName, OrderID, OrderStatus, OrderDate, Utilities.calculateTotal(OrderProducts));
        for (Product x : this.OrderProducts) {
            formatted += x + "\n";
        }
        return formatted;
    }

    public static ArrayList<Order> loadOrder() {
        ArrayList<String[]> loaded = Utilities.loader(ORDER_DETAILS);
        ArrayList<Order> allOrders = new ArrayList<>();
        for (String[] x : loaded) {
            allOrders.add(new Order(x));
        }
        return allOrders;
    }

    public static ArrayList<Product> loadOrderProduct(String orderID) {
        // orderID:productID
        ArrayList<String[]> raw = Utilities.loader(ORDER_PRODUCT);
        ArrayList<Product> allProducts = new ArrayList<>();
        for (String[] x : raw) {
            if (x[0].equals(orderID)) {
                for (Product y : Product.searchProduct(x[1], null, null))
                    allProducts.add(y);
            }
        }
        return allProducts;
    }

    public String[] OrderFormat() {
        String[] formatted = {
                userName,
                OrderID,
                OrderStatus,
                OrderDate
        };
        return formatted;
    }

    private String[] OrderProductFormat(Product product) {
        String[] formatted = {
                this.OrderID,
                product.getID()
        };
        return formatted;
    }

    public ArrayList<String[]> OrderProductFormat() {
        ArrayList<String[]> formatted = new ArrayList<>();
        for (Product x : OrderProducts) {
            formatted.add(OrderProductFormat(x));
        }
        return formatted;
    }

    public void setOrderStatus(Admin admin, String status) {
        if (admin != null) {
            if (this.OrderStatus.equals(status)) {
                System.out.println("Order is already " + this.OrderStatus);
                return;
            }
            if (this.OrderStatus.equals(Status.DELIVERED.value)) {
                System.out.println("Order is already delivered");
                return;
            }
            switch (status) {
                case "delivered":
                    System.out.println("Order Delivered!");
                    this.OrderStatus = "delivered";
                    return;
                default:
                    System.out.println("Err: Invalid status");
                    return;
            }
        } else {
            System.out.println("Err: Not Admin");
        }
    }
}
