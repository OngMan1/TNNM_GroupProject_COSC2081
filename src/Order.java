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

interface DateFormat {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
}

class Order implements OrderInfo, OrderStatus, ProductDetail, OrderProduct, AttributeFormat, DateFormat, Membership {
    private String orderID;
    private ArrayList<Product> orderProducts;
    private String orderStatus = null;
    private String orderUserName;
    private LocalDate orderDate = LocalDate.now();

    public Order(String userName, String orderID, ArrayList<Product> orderProducts, String orderStatus) {
        this.orderUserName = userName;
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
        return getOrderInfo() + "\n" + getOrderProducts();
    }

    public String getOrderInfo() {
        String info = String.format(
                "(%s) {%s} [%s] - %s === Total: [%.2f]\n(%s) Discounted Total: %.2f",
                getOrderUsername(), getOrderID(), getOrderStatus(), getOrderDate().format(dtf), calculateTotal(),
                getOrderCustomer().getCustomerMembership(), calculateTotal() - getOrderDiscount());
        return info;

    }

    public Customer getOrderCustomer() {
        ArrayList<Customer> tmp = Searcher.searchCustomerByUsername(this.getOrderUsername());
        if (tmp.size() != 1) {
            System.out.println("Err: Invalid number of customers");
            return null;
        }
        return tmp.get(0);
    }

    public Double getOrderDiscountRate() {
        Customer orderCustomer = getOrderCustomer();
        if (orderCustomer == null) {
            System.out.println("Didn't find Customer with username" + this.getOrderUsername());
            return null;
        }

        return Utilities.getMembershipRate(orderCustomer.getCustomerMembership());
    }

    public Double getOrderDiscountTotal() {
        return calculateTotal() * (1 - getOrderDiscountRate());
    }

    public Double getOrderDiscount() {
        return calculateTotal() * getOrderDiscountRate();
    }

    public String getOrderDiscountString() {
        if (getOrderDiscountRate().equals(0.0))
            return "";
        return String.format("Discount total: %.2f", getOrderDiscountTotal());
    }

    public String getOrderProducts() {
        return Utilities.arrayListToString(orderProducts);
    }

    private String[] OrderProductFormat(Product product) {
        String[] formatted = {
                this.orderID,
                product.getProductID()
        };
        return formatted;
    }

    public boolean setOrderStatus(String status) {
        if (this.orderStatus.equals(status)) {
            System.out.println("Order is already " + this.orderStatus);
            return false;
        }

        if (this.orderStatus.equals(DELIVERED)) {
            System.out.println("Order is already delivered");
            return false;
        }

        if (status.equals(DELIVERED)) {
            System.out.println("Order Delivered!");
            this.orderStatus = "delivered";
            return true;
        }

        System.out.println("Err: Invalid status");
        return false;
    }

    public Double calculateTotal() {
        Double sum = 0.0;
        for (Product x : orderProducts) {
            sum += x.getProductPrice();
        }
        return sum;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getOrderUsername() {
        return this.orderUserName;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    @Override
    public String[] getWriteFormat() {
        return new String[] {
                getOrderUsername(),
                getOrderID(),
                getOrderStatus(),
                getOrderDate().format(dtf)
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
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        };
    }

    public void addOrderProducts(Product product) {
        this.orderProducts.add(product);
    }

}