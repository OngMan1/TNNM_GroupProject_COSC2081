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
    private String productID, productName;
    private double productPrice;
    private String productCategoryID;

    public Product(String productID, String productName, double productPrice, String productCategoryID) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategoryID = productCategoryID;
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
                productID, productName, productPrice, getProductCategoryName());
    }

    public String toCategory() {
        return String.format(
                "{%s} (%s) %s [%.2f]",
                getProductCategoryName(), productID, productName, productPrice);
    }

    public static void browseProducts() {
        Utilities.printArrayList(Loader.loadProduct());
    }

    public String getProductID() {
        return this.productID;
    }

    public Double getProductPrice() {
        return this.productPrice;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductCategoryID() {
        return this.productCategoryID;
    }

    public String getProductCategoryName() {
        Category tmp = Searcher.searchCategory(this.productCategoryID, null);
        if (tmp == null)
            return "";
        return tmp.getCategoryName();
    }

    static Comparator<Product> byID() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(
                        Integer.parseInt(p1.getProductID()),
                        Integer.parseInt(p2.getProductID()));
            }
        };
    }

    static Comparator<Product> byPrice() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductPrice().compareTo(p2.getProductPrice());
            }
        };
    }

    static Comparator<Product> byName() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductName().compareTo(p2.getProductName());
            }
        };
    }

    static Comparator<Product> byCategoryName() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductCategoryName().compareTo(p2.getProductCategoryName());
            }
        };
    }

    static Comparator<Product> byCategoryID() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductCategoryID().compareTo(p2.getProductCategoryID());
            }
        };
    }

    @Override
    public String[] getWriteFormat() {
        return new String[] {
                getProductID(),
                getProductName(),
                String.valueOf(getProductPrice()),
                getProductCategoryID(),
        };
    }

    public static ArrayList<Product> searchProductByID(String productID) {
        return Searcher.searchProduct(productID, null, null, null, null);
    }

    public static ArrayList<Product> searchProductByName(String productName) {
        return Searcher.searchProduct(null, productName, null, null, null);
    }

    public static ArrayList<Product> searchProductByCategoryID(String productCategoryID) {
        return Searcher.searchProduct(null, null, productCategoryID, null, null);
    }

    public static ArrayList<Product> searchProductByCategoryName(String productCategoryName) {
        return Searcher.searchProduct(null, null, null, productCategoryName, null);
    }

    public static ArrayList<Product> searchProductByPriceRange(double min, double max) {
        return Searcher.searchProduct(null, null, null, null, new double[] { min, max });
    }

}