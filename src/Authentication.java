interface LoginType {
    String CUSTOMER = "customer";
    String ADMIN = "admin";
}

interface LoginInfo {
    int USERNAME = 0;
    int PASSWORD = 1;
}

interface SensitiveData {
    String CUSTOMER_DETAILS = "user_details.txt";
    String ADMIN_DETAILS = "admin_details.txt";
}

class Authentication implements LoginType, SensitiveData, LoginInfo {
    private User createUser(String type, String[] loginRecord) {
        if (type.equals(CUSTOMER)) {
            return new Customer(loginRecord);
        } else if (type.equals(ADMIN)) {
            return new Admin(loginRecord);
        } else {
            System.out.println("Error: Invalid login type");
            return null;
        }
    }

    public String[] inputAccount() {
        System.out.print("Username: ");
        String username = UserInput.getInput();
        System.out.print("Password: ");
        String password = UserInput.getInput();
        String[] result = {username, password};
        return result;
    }

    private User login(String type, String username, String password) {
        String file;
        switch (type) {
            case CUSTOMER:
                file = CUSTOMER_DETAILS;
                break;
            case ADMIN:
                file = ADMIN_DETAILS;
                break;
            default:
                System.out.println("Error: Invalid login type");
                return null;
        }

        String[] userInput = {username, password};
        String[] loginRecord = Utilities.searcher(file, userInput);
        if (loginRecord == null) {
            System.out.println("Error: Invalid username or password");
            return null;
        }

        if (type.equals(CUSTOMER)) {
            return createUser(CUSTOMER, loginRecord);
        } else {
            return createUser(ADMIN, loginRecord);
        }
    }

    public Customer Customer_Login(String[] input) {
        User customer = this.login(CUSTOMER, input[USERNAME], input[PASSWORD]);
        if (customer != null) {
            System.out.println("Customer successfully logged in!");
            return (Customer) customer;
        } else {
            System.out.println("Wrong username or password");
            return null;
        }
    }

    public Customer Customer_Login() {
        return Customer_Login(inputAccount());
    } // wrapper

    public Admin Admin_Login(String[] input) {
        User admin = this.login(ADMIN, input[USERNAME], input[PASSWORD]);
        if (admin != null) {
            System.out.println("Admin successfully logged in!");
            return (Admin) admin;
        } else {
            System.out.println("Wrong username or password");
            return null;
        }
    }
}
