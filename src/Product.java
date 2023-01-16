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

import java.util.ArrayList;
import java.util.Comparator;

interface ProductDetail {
    String PRODUCT_DETAILS = "src/products_detail.txt";

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
            return "None";
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

    static Comparator<Product> byPriceDescending() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return -p1.getProductPrice().compareTo(p2.getProductPrice());
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

    public static ArrayList<Product> addToCart() {
        ArrayList<Product> cart = new ArrayList<>();
        while (true) {
            System.out.print("(Cart: " + cart.size() + ") ");
            System.out.println("Enter ProductID or ProductName (Leave empty to stop): ");
            String input = UserInput.getInput();
            if (input == null) {
                break;
            }
            Product tmpProduct = getProduct(input);
            if (tmpProduct == null) {
                System.out.println("Product is not found.");
                continue;
            } else {
                cart.add(tmpProduct);
            }

        }
        return cart;
    }

    public static Product getProduct(String productID) {
        Product resProduct = null;
        if (Utilities.containsPattern(IDFormat.numbersRegex, productID)) {
            ArrayList<Product> tmp = Searcher.searchProductByID(productID);
            if (tmp.size() == 1) {
                resProduct = tmp.get(0);
            }
        } else {
            ArrayList<Product> tmp = Searcher.searchProductByName(productID);
            if (tmp.size() == 1) {
                resProduct = tmp.get(0);
            }
        }
        System.out.println(resProduct);
        return resProduct;
    }

    @Override
    public boolean equals(Object o) {
        Product e;
        if (!(o instanceof Product)) {
            return false;
        } else {
            e = (Product) o;
            if (this.getProductID().equals(e.getProductID()) &&
                    this.getProductName().equals(e.getProductName())) {
                return true;
            }
        }
        return false;
    }

}