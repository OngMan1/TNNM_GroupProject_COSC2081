/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author 1: Ong Gia Man (s3938231)
  Author 2: Nguyen Le Thu Nhan (s3932151)
  Author 3: Tran Minh Nhat (s3926629)
  Author 4: Nguyen Ngoc Minh Thu (s3941327)
  Date: 01/2023
  Acknowledgement: Acknowledge the resources that you use here.
*/

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