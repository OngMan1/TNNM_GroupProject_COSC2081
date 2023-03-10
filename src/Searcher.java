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

class Searcher {
    public static ArrayList<Customer> searchCustomer(String customerUsername, String customerID, String customerName,
                                                     double[] customerSpendingRange,
                                                     String customerMembership) {
        // checks if the passed parameters match the customer's properties, if so, add the customer to the result
        //ArrayList and returns it
        ArrayList<Customer> curr = Loader.loadCustomers();
        ArrayList<Customer> res = new ArrayList<>();
        for (Customer x : curr) {
            if ((customerUsername != null && x.getUsername().equals(customerUsername)) ||
                    (customerID != null && x.getCustomerID().equals(customerID)) ||
                    (customerName != null && x.getCustomerName().equalsIgnoreCase(customerName)) ||
                    (customerSpendingRange != null
                            && Utilities.isInRange(x.getCustomerSpending(), customerSpendingRange))
                    ||
                    (customerMembership != null && x.getCustomerMembership().equals(customerMembership))) {
                res.add(x);
            }
        }
        return res;

    }

    public static ArrayList<Customer> searchCustomerByUsername(String customerUsername) {
        return Searcher.searchCustomer(customerUsername, null, null, null, null);
    }

    public static ArrayList<Customer> searchCustomerByID(String customerID) {
        return Searcher.searchCustomer(null, customerID, null, null, null);
    }

    public static ArrayList<Customer> searchCustomerByName(String customerName) {
        return Searcher.searchCustomer(null, null, customerName, null, null);
    }

    public static ArrayList<Customer> searchCustomerBySpendingRange(double min, double max) {
        return Searcher.searchCustomer(null, null, null, new double[] { min, max }, null);
    }

    public static ArrayList<Customer> searchCustomerByMembership(String customerMembership) {
        return Searcher.searchCustomer(null, null, null, null, customerMembership);
    }

    // apply similar of the searchCustomer() for the other methods
    public static ArrayList<Product> searchProduct(String productID, String productName, String categoryID,
                                                   String categoryName, double[] priceRange) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product x : Loader.loadProduct()) {
            if ((productID != null && x.getProductID().equalsIgnoreCase(productID)) ||
                    (productName != null && x.getProductName().equalsIgnoreCase(productName)) ||
                    (categoryID != null && x.getProductCategoryID().equalsIgnoreCase(categoryID)) ||
                    (categoryName != null && x.getProductCategoryName().equalsIgnoreCase(categoryName)) ||
                    (priceRange != null
                            && Utilities.isInRange(x.getProductPrice(), priceRange))) {
                result.add(x);
            }
        }
        return result;
    }

    public static ArrayList<Product> searchProductByID(String productID) {
        return searchProduct(productID, null, null, null, null);
    }

    public static ArrayList<Product> searchProductByName(String productName) {
        return searchProduct(null, productName, null, null, null);
    }

    public static ArrayList<Product> searchProductByNameOrID(String productName) {
        if (!Utilities.containsPattern(IDFormat.numbersRegex, productName)) {
            return searchProductByName(productName);
        } else {
            return searchProductByID(productName);
        }
    }

    public static ArrayList<Product> searchProductByCategoryID(String productCategoryID) {
        return searchProduct(null, null, productCategoryID, null, null);
    }

    public static ArrayList<Product> searchProductByCategoryName(String productCategoryName) {
        return searchProduct(null, null, null, productCategoryName, null);
    }

    public static ArrayList<Product> searchProductByPriceRange(double min, double max) {
        return searchProduct(null, null, null, null, new double[] { min, max });
    }

    public static ArrayList<Product> searchProductByCategory(String productCategoryName) {
        if (!Utilities.containsPattern(IDFormat.numbersRegex, productCategoryName)) {
            return searchProductByCategoryName(productCategoryName);
        } else {
            return searchProductByCategoryID(productCategoryName);
        }
    }

    public static Category searchCategory(String categoryID, String categoryName) {
        for (Category x : Loader.loadCategories()) {
            if ((categoryID != null && x.getCategoryID().equalsIgnoreCase(categoryID)) ||
                    (categoryName != null && x.getCategoryName().equalsIgnoreCase(categoryName))) {
                return x;
            }
        }
        return null;
    }

    public static Category searchCategoryByID(String categoryID) {
        return searchCategory(categoryID, null);
    }

    public static Category searchCategoryByName(String categoryName) {
        return searchCategory(null, categoryName);
    }

    public static ArrayList<Order> searchOrder(String orderID, String orderStatus, String orderUsername,
                                               String orderDate) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order x : Loader.loadOrder()) {
            if ((x.getOrderID().equals(orderID)) ||
                    (x.getOrderStatus().equals(orderStatus)) ||
                    (x.getOrderUsername().equals(orderUsername)) ||
                    (x.getOrderDate().format(DateFormat.dtf).equals(orderDate))) {
                result.add(x);
            }
        }
        return result;
    }

    public static ArrayList<Order> searchOrderByID(String orderID) {
        return searchOrder(orderID, null, null, null);
    }

    public static ArrayList<Order> searchOrderByStatus(String orderStatus) {
        return searchOrder(null, orderStatus, null, null);
    }

    public static ArrayList<Order> searchOrderByUsername(String orderUsername) {
        return searchOrder(null, null, orderUsername, null);
    }

    public static ArrayList<Order> searchOrderByDate(String orderDate) {
        return searchOrder(null, null, null, orderDate);
    }

    public static OrderTotals searchOrderTotalFromOrderID(String orderID) {
        ArrayList<OrderTotals> allOrderTotals = Loader.loadOrderTotals();
        // Utilities.printArrayList(allOrderTotals);
        for (OrderTotals x : allOrderTotals) {
            // System.out.println(x.getOrderID() + " " + orderID);
            if (x != null && x.getOrderID().equals(orderID)) {
                return x;
            }
        }
        return null;
    }
}