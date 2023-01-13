import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

interface Delimiter {
    String TEXT_DELIMITER = ",";
}

class Loader implements Delimiter, SensitiveData, ProductDetail, CategoryDetails, OrderProduct, OrderInfo, Order_Totals {
    public static String[] readParser(String content) {
        String[] parts = content.split(TEXT_DELIMITER);
        return parts;
    }

    public static ArrayList<String[]> rawLoader(String file_name) {
        BufferedReader reader = null;
        ArrayList<String[]> all = new ArrayList<>();
        try {
            File file = new File(file_name);
            if (!file.exists()) {
                file.createNewFile();
            }
            Writer.removeEmptyLines(file_name);
            reader = new BufferedReader(new FileReader(file_name));
            String line;
            while ((line = reader.readLine()) != null) {
                all.add(readParser(line));
            }
            return all;
        } catch (IOException e) { // Handle exception
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<String[]> loaded = rawLoader(CUSTOMER_DETAILS);
    ArrayList<Customer> allCustomers = new ArrayList<>();
        if (loaded.size() != 0) {
            for (String[] x : loaded) {
                allCustomers.add(new Customer(x));
            }
    }
        return allCustomers;
}
    public static ArrayList<Product> loadProduct() {
        ArrayList<String[]> loaded = rawLoader(PRODUCT_DETAILS);
        ArrayList<Product> allProducts = new ArrayList<>();
        if (loaded.size() != 0) {
            for (String[] x : loaded) {
                allProducts.add(new Product(x));
            }
        }
        return allProducts;
    }

    public static ArrayList<Category> loadCategories() {
        ArrayList<String[]> loaded = rawLoader(CATEGORY_DETAIL);
        ArrayList<Category> allCategory = new ArrayList<>();
        if (loaded.size() != 0) {
            for (String[] x : loaded) {
                allCategory.add(new Category(x));
            }
        }
        return allCategory;
    }

    public static ArrayList<Product> loadOrderProduct(String orderID) {
        ArrayList<String[]> raw = rawLoader(ORDER_PRODUCT);
        ArrayList<Product> allProducts = new ArrayList<>();
        if (raw.size() != 0) {
            for (String[] x : raw) {
                if (x[OR_ID].equals(orderID)) {
                    for (Product y : Searcher.searchProductByID(x[PR_ID]))
                        allProducts.add(y);
                }
            }
        }
        Collections.sort(allProducts, Product.byID());
        return allProducts;
    }

    public static ArrayList<Order> loadOrder() {
        ArrayList<String[]> loaded = rawLoader(ORDER_DETAILS);
        ArrayList<Order> allOrders = new ArrayList<>();
        if (loaded.size() != 0) {
            if (loaded.size() != 0) {
                for (String[] x : loaded) {
                    allOrders.add(new Order(x));
                }
            }
        }
            return allOrders;
    }

    public static ArrayList<OrderTotals> loadOrderTotals() {
        ArrayList<String[]> loaded = rawLoader(ORDER_TOTALS);
        ArrayList<OrderTotals> allOrders = new ArrayList<>();
        if (loaded.size() != 0) {
            for (String[] x : loaded) {
                allOrders.add(new OrderTotals(x));
            }
        }
        return allOrders;
    }

    public static String[] rawSearcher(String file_name, String[] searchInput) {
        BufferedReader reader = null;

        try {
            File file = new File(file_name);
            if (!file.exists()) {
                file.createNewFile();
                return null;
            }
            reader = new BufferedReader(new FileReader(file_name));
            String line;
            while ((line = reader.readLine()) != null) {
                boolean[] allSearch = new boolean[searchInput.length];
                String[] parts = Loader.readParser(line);
                for (int i = 0; i < searchInput.length; i++) {
                    if (parts[i] == null) {
                        continue;
                    }
                    if (parts[i].equals(searchInput[i])) {
                        allSearch[i] = true;
                    } else {
                        break;
                    }
                }
                if (Utilities.checkSearch(allSearch)) {
                    return parts;
                }
            }
            return null;
        } catch (IOException e) { // Handle exception
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

}