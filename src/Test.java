public class Test implements OrderStatus {
    public static void main(String[] args) {
        // Authentication session = new Authentication();

        Authentication session = new Authentication();
        // session.customerRegistration();

        String[] testing = {
                "admin",
                "123456"
        };
        Admin sessAdmin = session.Admin_Login(testing);
        System.out.println(sessAdmin);

        System.out.println(Searcher.searchOrderByID("003"));
        // sessAdmin.viewAllCustomers(false);
        // Product.browseProducts();
        // System.out.println(Searcher.searchCategory("001", null));
        // sessAdmin.addProduct();
        // Product.browseProducts();
        // Product.browseProducts();
        // sessAdmin.viewAllOrders(true);
        System.out.println(sessAdmin.changeOrderStatus("003", OrderStatus.DELIVERED));
        // System.out.println(sessAdmin.searchCustomerByID("005"));
    }
}