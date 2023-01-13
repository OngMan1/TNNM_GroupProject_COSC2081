import java.text.DecimalFormat;

interface Order_Totals {
    String ORDER_TOTALS = "order_totals.txt";
    int ID = 0;
    int total = 1;
}

public class OrderTotals implements Order_Totals, AttributeFormat {
    private String orderID;
    private Double orderTotals;

    public OrderTotals(String orderID, Double orderTotals) {
        this.orderID = orderID;
        this.orderTotals = orderTotals;
    }

    public OrderTotals(String[] parts) {
        this(parts[ID], Double.parseDouble(parts[total]));
    }

    public String getOrderID() {
        return this.orderID;
    }

    public Double getOrderTotals() {
        return this.orderTotals;
    }

    @Override
    public String toString() {
        return String.format("(%s) %.2f", getOrderID(), getOrderTotals());
    }

    @Override
    public String[] getWriteFormat() {
        DecimalFormat df = new DecimalFormat("#.00");
        return new String[] {
                orderID,
                df.format(orderTotals)
        };
    }

}