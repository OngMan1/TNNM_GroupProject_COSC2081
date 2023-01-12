import java.util.ArrayList;

interface CategoryDetails {
    String CATEGORY_DETAIL = "category_details.txt";
    int ID = 0;
    int NAME = 1;
}

class Category implements CategoryDetails {
    private String categoryID;
    private String categoryName;

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category(String[] parts) {
        this(parts[ID], parts[NAME]);
    }

    public static ArrayList<Category> loadCategories() {
        return null;
    }
}
