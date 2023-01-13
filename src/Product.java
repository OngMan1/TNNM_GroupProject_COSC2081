import java.util.ArrayList;
import java.util.Comparator;

interface ProductDetail {
    String PRODUCT_DETAILS = "products_detail.txt";
    int ID = 0;
    int NAME = 1;
    int PRICE = 2;
    int CATEGORY = 3;
}

class Product implements ProductDetail, AttributeFormat {
    // private static final int P_ID = 0, P_Name = 1, P_Price = 2, P_Category = 3;
    private String ProductID, ProductName;
    private double ProductPrice;
    private String ProductCategory;

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

    public String toCategory() {
        return String.format(
                "{%s} (%s) %s [%.2f]",
                ProductCategory, ProductID, ProductName, ProductPrice);
    }

    public static ArrayList<Product> searchProductByID(String productID) {
        return Searcher.searchProduct(productID, null, null, null);
    }

    public static ArrayList<Product> searchProductByName(String productName) {
        return Searcher.searchProduct(null, productName, null, null);
    }

    public static ArrayList<Product> searchProductByCategory(String productCategory) {
        return Searcher.searchProduct(null, null, productCategory, null);
    }

    public static ArrayList<Product> searchProductByPriceRange(double min, double max) {
        return Searcher.searchProduct(null, null, null, new double[]{min, max});
    }

    public static void browseProducts() {
        Utilities.printArrayList(Loader.loadProduct());
    }
    public String getName() {
        return this.ProductName;
    }

    public String getCategory() {
        return this.ProductCategory;
    }

    static Comparator<Product> byID() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(
                        Integer.parseInt(p1.getID()),
                        Integer.parseInt(p2.getID()));
            }
        };
    }

    static Comparator<Product> byPrice() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getPrice().compareTo(p2.getPrice());
            }
        };
    }

    static Comparator<Product> byName() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        };
    }

    static Comparator<Product> byCategory() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getCategory().compareTo(p2.getCategory());
            }
        };
    }

    @Override
    public String[] getWriteFormat() {
        return new String[] {
                ProductID,
                ProductName,
                String.valueOf(ProductPrice),
                ProductCategory,
        };
    }

    public String getID() {
        return this.ProductID;
    }

    public Double getPrice() {
        return this.ProductPrice;
    }

}
