import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

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

class Order implements OrderInfo, OrderStatus, ProductDetail, OrderProduct, AttributeFormat {
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
                Loader.loadOrderProduct(parts[OrderInfo.ID]),
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
        for (Order x : Loader.loadOrder()) {
            if (x.userName.equals(custUsername)) {
                result.add(x);
            }
        }
        return result;
    }

    public Double calculateTotal() {
        Double sum = 0.0;
        for (Product x : orderProducts) {
            sum += x.getPrice();
        }
        return sum;
    }
    @Override
    public String[] getWriteFormat() {
        return new String[] {
                getUsername(),
                getID(),
                getStatus(),
                getDate().format(dtf)
        };
    }

    public ArrayList<String[]> getOrderProductWriteFormat() {
        ArrayList<String[]> formatted = new ArrayList<>();
        for (Product x : orderProducts) {
            formatted.add(OrderProductFormat(x));
        }
        return formatted;
    }

    public static Comparator<Order> byDate() {
        return new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
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