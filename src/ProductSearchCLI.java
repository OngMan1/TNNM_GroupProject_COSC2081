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

interface ProductCLIIndex {
    String BY_NAME_ID = "0";
    String BY_CATEGORY = "1";
    String BY_PRICE_RANGE = "2";
    String CLEAR_FILTERS = "3";
    String VIEW_ALL_PRODUCTS = "4";
    String VIEW_ALL_CATEGORIES = "5";
}

public class ProductSearchCLI implements CLI, ProductCLIIndex {

    @Override
    public boolean run() {
        boolean state = true;
        while (state) {
            UserInput.clearConsole();
            System.out.println("SEARCH PRODUCTS: ");
            Utilities.printStringBullet(getOptions());
            System.out.println("Enter an option: ");
            String command = UserInput.getInput();
            state = executeCommand(command);
            System.out.println("Continue searching Product? (Y/N): ");
            if (UserInput.getConfirmation("Y", "N")) {
                state = true;
            } else {
                state = false;
            }
        }
        return false;
    }

    public boolean executeCommand(String command) {
        if (command == null) {
            return false;
        }
        String query;
        ArrayList<Product> productFiltered = new ArrayList<>();
        boolean state = true;
        while (state) {
            switch (command) {
                case BY_NAME_ID:
                    System.out.println("Enter product name or ID");
                    query = UserInput.getInput();
                    if (query == null)
                        break;
                    productFiltered.addAll(Searcher.searchProductByNameOrID(query));
                    break;
                case BY_CATEGORY:
                    System.out.println("Enter category name or ID");
                    query = UserInput.getInput();
                    if (query == null)
                        break;
                    productFiltered.addAll(Searcher.searchProductByCategory(query));
                    break;
                case BY_PRICE_RANGE:
                    System.out.println("Enter min: ");
                    String min = UserInput.getInput();
                    System.out.println("Enter max: ");
                    String max = UserInput.getInput();
                    double[] range = UserInput.getPriceRange(min, max);
                    productFiltered.addAll(Searcher.searchProductByPriceRange(range[0], range[1]));
                    System.out.println("Sort (LOW/HIGH): ");
                    if (UserInput.getConfirmation("LOW", "HIGH")) {
                        productFiltered.sort(Product.byPrice());
                    } else {
                        productFiltered.sort(Product.byPriceDescending());
                    }
                    break;
                case CLEAR_FILTERS:
                    productFiltered.clear();
                    break;
                case VIEW_ALL_PRODUCTS:
                    Product.browseProducts();
                    break;
                case VIEW_ALL_CATEGORIES:
                    Category.browseCategories();
                    break;
                default:
                    System.out.println("Do you want to stop? (Y/N)");
                    if (!UserInput.getConfirmation("Y", "N")) {
                        state = false;
                    }
                    break;
            }
            System.out.println("Results: ");
            if (productFiltered.size() == 0) {
                System.out.println("No product found");
            } else {
                Utilities.printArrayList(productFiltered);
            }
            break;
        }
        return false;

    }

    @Override
    public String[] getOptions() {
        String[] options = {
                "By Name or ID",
                "By Category",
                "By Price Range",
                "Clear filters",
                "View all Products",
                "View all Categories"
        };
        return options;
    }

}