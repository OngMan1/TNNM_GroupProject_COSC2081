class Authentication {
    private static final String USER = "user.txt";
    private static final String ADMIN = "admin.txt";

    public static String getUserFile(Admin admin) {
        if (admin != null) {
            return USER;
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
                return search(USER, username, password);
            case "admin":
                return search(ADMIN, username, password);
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
    public Customer Customer_Login(String[] input) {
        String[] logging = this.logging_in("customer", input[0], input[1]);
        if (logging != null) {
            System.out.println("Customer successfully logged in!");
            return Customer.createCustomer(logging);
        } else {
            System.out.println("Wrong username or password");
            return null;
        }
    }

    public Customer Customer_Login() {
        return Customer_Login(inputAccount());
    }

    public Admin Admin_Login(String[] input) {
        String[] logging = this.logging_in("admin", input[0], input[1]);
        if (logging != null) {
            System.out.println("Admin successfully logged in!");
            return new Admin(logging);
        } else {
            System.out.println("Wrong username or password");
            return null;
        }
    }

    public Admin Admin_Login() {
        return Admin_Login(inputAccount());
    }
}
