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