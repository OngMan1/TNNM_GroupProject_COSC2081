import java.util.ArrayList;
import java.util.Comparator;

interface ProductDetail {
    String PRODUCT_DETAILS = "products_detail.txt";
    int ID = 0;
    int NAME = 1;
    int PRICE = 2;
    int CATEGORY = 3;
}

class Product implements ProductDetail {
    // private static final int P_ID = 0, P_Name = 1, P_Price = 2, P_Category = 3;
    private String ProductID, ProductName;
    private double ProductPrice;
    private String ProductCategory;

    static Comparator<Product> compareToByID() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(
                        Integer.parseInt(p1.getID()),
                        Integer.parseInt(p2.getID()));
            }
        };
    }

    static Comparator<Product> compareToByPrice() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        };
    }

    public Product(String productID, String productName, double productPrice, String productCategory) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductCategory = productCategory;
    }

    public Product(String[] parts) {
        this(parts[ID],
                parts[NAME],
                Double.parseDouble(parts[PRICE]),
                parts[CATEGORY]);
    }

    @Override
    public String toString() {
        return String.format(
                "(%s) %s [%.2f] - {%s}",
                ProductID, ProductName, ProductPrice, ProductCategory);
    }

    public String byCategory() {
        return String.format(
                "{%s} (%s) %s [%.2f]",
                ProductCategory, ProductID, ProductName, ProductPrice);
    }

    public String[] ProductFormat() {
        return new String[]{
                ProductID,
                ProductName,
                String.valueOf(ProductPrice),
                ProductCategory,
        };
    }

    public static ArrayList<Product> loadProduct() {
        ArrayList<String[]> loaded = Utilities.loader(PRODUCT_DETAILS);
        ArrayList<Product> allProducts = new ArrayList<>();
        assert loaded != null;
        for (String[] x : loaded) {
            allProducts.add(new Product(x));
        }
        return allProducts;
    }

    public static ArrayList<Product> searchProductByID(String productID) {
        return searchProduct(productID, null, null);
    }

    public static ArrayList<Product> searchProductByName(String productName) {
        return searchProduct(null, productName, null);
    }

    public static ArrayList<Product> searchProductByCategory(String productCategory) {
        return searchProduct(null, null, productCategory);
    }

    private static ArrayList<Product> searchProduct(String productID, String productName, String category) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product x : loadProduct()) {
            if ((productID != null && x.ProductID.equals(productID)) ||
                    (productName != null && x.ProductName.equals(productName)) ||
                    (category != null && x.ProductCategory.equals(category))) {
                result.add(x);
            }
        }
        return result;
    }

    public static void browseProducts() {
        Utilities.printArrayList(loadProduct());
    }

    public String getID() {
        return this.ProductID;
    }

    public Double getPrice() {
        return this.ProductPrice;
    }

}
