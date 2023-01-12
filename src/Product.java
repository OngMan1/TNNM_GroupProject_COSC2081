import java.util.ArrayList;

class Product {
    // Test
    private static final String PRODUCT_DETAILS = "items.txt";
    private String ProductID, ProductName;
    private double ProductPrice;
    private String ProductCategory;

    public enum Prod {    // an enum class to store constants
        // ex: 001:Coke:10000:TypeA
        P_ID(0), P_NAME(1), P_PRICE(2), P_CATEGORY(3);

        public final int VALUE;

        private Prod(int value) {
            this.VALUE = value;
        }

        public int getVALUE() {
            return VALUE;
        }
    }

    public Product(String productID, String productName, double productPrice, String productCategory) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductCategory = productCategory;
    }

    public Product(String[] parts) {
        this(parts[Prod.P_ID.VALUE],
                parts[Prod.P_NAME.VALUE],
                Double.parseDouble(parts[Prod.P_PRICE.VALUE]),
                parts[Prod.P_CATEGORY.VALUE]);
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

    public static ArrayList<Product> searchProduct(String productID, String productName, String category) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product x : loadProduct()) {
            if (productID != null) {
                if (x.ProductID.equals(productID)) {
                    result.add(x);
                    continue;
                }
            }
            if (productName != null) {
                if (x.ProductName.equals(productName)) {
                    result.add(x);
                    continue;
                }
            }
            if (category != null) {
                if (x.ProductCategory.equals(category)) {
                    result.add(x);
                    continue;
                }
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
