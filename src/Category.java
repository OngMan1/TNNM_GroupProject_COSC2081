interface CategoryDetails {
    String CATEGORY_DETAIL = "src/category_details.txt";
    int ID = 0;
    int NAME = 1;
}

class Category implements CategoryDetails, AttributeFormat {
    private String categoryID;
    private String categoryName;

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category(String[] parts) {
        this(parts[ID], parts[NAME]);
    }

    public String getCategoryID() {
        return this.categoryID;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", getCategoryID(), getCategoryName());
    }

    @Override
    public String[] getWriteFormat() {
        return new String[] {
                getCategoryID(),
                getCategoryName()
        };
    }

    public static void browseCategories() {
        Utilities.printArrayList(Loader.loadCategories());
    }

}