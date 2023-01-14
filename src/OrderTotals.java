interface Order_Totals {
    String ORDER_TOTALS = "src/order_totals.txt";
    int ID = 0;
    int total = 1;
}

class OrderTotals implements Order_Totals, AttributeFormat {
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
        return new String[] {
                orderID,
                String.valueOf(orderTotals)
        };
    }

}