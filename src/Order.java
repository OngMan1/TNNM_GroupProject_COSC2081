import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

interface OrderInfo {
    String ORDER_DETAILS = "order_details.txt";
    int ORDER_USER = 0;
    int ID = 1;
    int STATUS = 2;
    int DATE = 3;
}

interface OrderProduct {
    String ORDER_PRODUCT = "order_products.txt";
    int OR_ID = 0;
    int PR_ID = 1;
}

interface OrderStatus {
    String PLACED = "placed";
    String DELIVERED = "delivered";
}

class Order implements OrderInfo, OrderStatus, ProductDetail, OrderProduct {
    private String orderID;
    private ArrayList<Product> orderProducts;
    private String orderStatus = null;
    private String userName;
    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private LocalDate orderDate = LocalDate.now();

    public Order(String userName, String orderID, ArrayList<Product> orderProducts, String orderStatus) {
        this.userName = userName;
        this.orderID = orderID;
        this.orderProducts = orderProducts;
        this.orderStatus = orderStatus;
    }

    public Order(String userName, String orderID, ArrayList<Product> orderProducts, String orderStatus,
                 String orderDate) {
        this(userName, orderID, orderProducts, orderStatus);
        this.orderDate = LocalDate.parse(orderDate, dtf);
    }

    public Order(String[] parts) {
        this(parts[OrderInfo.ORDER_USER],
                parts[OrderInfo.ID],
                loadOrderProduct(parts[OrderInfo.ID]),
                parts[OrderInfo.STATUS],
                parts[OrderInfo.DATE]);
    }

    @Override
    public String toString() {
        return getShortOrderInfo() + "\n" + getOrderProducts();
    }

    public String getShortOrderInfo() {
        return String.format(
                "(%s) {%s} [%s] - %s === Total: [%.2f]",
                getUsername(), getID(), getStatus(), getDate(), calculateTotal());
    }

    public String getOrderProducts() {
        return Utilities.arrayListToString(orderProducts);
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
            if (x[OR_ID].equals(orderID)) {
                for (Product y : Product.searchProductByID(x[PR_ID]))
                    allProducts.add(y);
            }
        }
        Collections.sort(allProducts, Product.compareToByID());
        return allProducts;
    }

    public String[] OrderFormat() {
        String[] formatted = {
                getUsername(),
                getID(),
                getStatus(),
                getDate().format(dtf)
        };
        return formatted;
    }

    private String[] OrderProductFormat(Product product) {
        String[] formatted = {
                this.orderID,
                product.getID()
        };
        return formatted;
    }

    public ArrayList<String[]> OrderProductFormat() {
        ArrayList<String[]> formatted = new ArrayList<>();
        for (Product x : orderProducts) {
            formatted.add(OrderProductFormat(x));
        }
        return formatted;
    }

    public void setOrderStatus(Admin admin, String status) {
        if (admin != null) {
            if (this.orderStatus.equals(status)) {
                System.out.println("Order is already " + this.orderStatus);
                return;
            }
            if (this.orderStatus.equals(DELIVERED)) {
                System.out.println("Order is already delivered");
                return;
            }
            switch (status) {
                case "DELIVERED":
                    System.out.println("Order Delivered!");
                    this.orderStatus = "delivered";
                    return;
                default:
                    System.out.println("Err: Invalid status");
                    return;
            }
        } else {
            System.out.println("Err: Not Admin");
        }
    }

    public static ArrayList<Order> getOrderByCustUsername(String custUsername) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order x : loadOrder()) {
            if (x.userName.equals(custUsername)) {
                result.add(x);
            }
        }
        return result;
    }

    private static void totalWithDiscount(Customer customer, Order order) {
        // To be implemented
    }

    public Double calculateTotal() {
        Double sum = 0.0;
        for (Product x : orderProducts) {
            sum += x.getPrice();
        }
        return sum;
    }

    public String getStatus() {
        return this.orderStatus;
    }

    public String getID() {
        return this.orderID;
    }

    public String getUsername() {
        return this.userName;
    }

    public LocalDate getDate() {
        return this.orderDate;
    }

}