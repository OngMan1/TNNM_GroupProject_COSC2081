public class Test {
    public static void main(String[] args) {
        Authentication session = new Authentication();
        String[] testing = {
                "admin",
                "123456"
        };
        Admin sessAdmin = session.Admin_Login(testing);
        System.out.println(sessAdmin);

        Product.browseProducts();
        sessAdmin.addProduct();
        Product.browseProducts();

    }
}