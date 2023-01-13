public class Test implements OrderStatus {
    public static void main(String[] args) {
        // Authentication session = new Authentication();

        // session.customerRegistration();

        // String[] testing = {
        //        "admin",
        //       "123456"
        // };
        // Admin sessAdmin = session.Admin_Login(testing);
        // System.out.println(sessAdmin);  */

        // System.out.println(Searcher.searchOrderByID("003"));
        // sessAdmin.viewAllCustomers(false);
        // Product.browseProducts();
        // System.out.println(Searcher.searchCategory("001", null));
        // sessAdmin.addProduct();
        // Product.browseProducts();
        // Product.browseProducts();
        // sessAdmin.viewAllOrders(true);
        // System.out.println(sessAdmin.changeOrderStatus("003",
        // OrderStatus.DELIVERED));
        // System.out.println(sessAdmin.searchCustomerByID("005"));
        Authentication session = new Authentication();

        // String[] testing = {
        //        "glancedkryokonite",
        //        "columbinefaienceschesapeake"
        // };
        // Customer currCustomer = session.Customer_Login(testing);
        // System.out.println(currCustomer);
        // currCustomer.searchProduct();
        // Product.browseProducts();
        // currCustomer.newOrder();

        String[] testing = {
                "microporereckonings",
                "dillymencalkshitherward"
        };
        Admin sessAdmin = session.Admin_Login(testing);
        System.out.println(sessAdmin);
        // System.out.println(sessAdmin.numberOfMembershipType());
        // sessAdmin.sortCustomerBySpending(true);
        // sessAdmin.sortProductBySales(true);

        // System.out.println(Searcher.searchOrderByID("001"));
        // sessAdmin.changeOrderStatus("002", OrderStatus.DELIVERED);
        // sessAdmin.sortProductBySales();
        // System.out.println(Searcher.searchCustomerByID("000"));
        // System.out.println(Searcher.searchOrderTotalFromOrderID("001"));
        // System.out.println(Searcher.searchOrderByID("001"));
        // Product.browseProducts();
        // System.out.println();
        // System.out.println(sessAdmin.editProductInfo(Searcher.searchProductByName("Coke").get(0),
        // 9999999.0, null));
        // Product.browseProducts();
        // sessAdmin.addProduct();
        // sessAdmin.addProduct(new String[] {
        // null, "KFC", "12345", "007"
        // });
    }
}