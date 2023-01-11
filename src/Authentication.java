class Authentication {
    private static final String USER_DETAILS = "user_details.txt";
    private static final String ADMIN_DETAILS = "admin_details.txt";

    public static String getUserFile(Admin admin) {
        if (admin != null) {
            return USER_DETAILS;
        }
        return null;
    }

    private String[] search(String loginFile, String username, String password) {
        String[] userInput = { username, password };
        return Utilities.searcher(loginFile, userInput);
    }

    private String[] logging_in(String type, String username, String password) {
        switch (type) {
            case "customer":
                return search(USER_DETAILS, username, password);
            case "admin":
                return search(ADMIN_DETAILS, username, password);
            default:
                System.out.println("Err: Wrong login type");
                return null;
        }
    }

    public String[] inputAccount() {
        System.out.print("Username: ");
        String username = UserInput.getInput();
        System.out.print("Password: ");
        String password = UserInput.getInput();
        String[] result = { username, password };
        return result;
    }

}
