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

interface LoginType {
    String CUSTOMER = "customer";
    String ADMIN = "admin";
}

interface LoginInfo {
    int USERNAME = 0;
    int PASSWORD = 1;
}

interface SensitiveData {
    String CUSTOMER_DETAILS = "src/user_details.txt";
    String ADMIN_DETAILS = "src/admin_details.txt";
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
        String[] result = { username, password };
        return result;
    }

    public Customer customerRegistration(String[] account, String name) {
        if (Loader.rawSearcher(CUSTOMER_DETAILS, new String[] { account[USERNAME] }, true) == null) {
            ArrayList<String[]> allCustomers = Loader.rawLoader(CUSTOMER_DETAILS);
            int totalCustomerCount = allCustomers.size();
            String[] newCustomerInfo = {
                    account[USERNAME],
                    account[PASSWORD],
                    Utilities.IDFormatter(totalCustomerCount),
                    name
            };
            Writer.appendFile(CUSTOMER_DETAILS, Writer.writeParser(newCustomerInfo));
            Customer newCustomer = new Customer(newCustomerInfo);
            return newCustomer;
        } else {
            return null;
        }
    }

    public void customerRegistration() {
        System.out.println("Creating new customer account");
        String[] account = inputAccount();
        System.out.println("Enter name: ");
        String name = UserInput.getInput();
        Customer newUser = customerRegistration(
                account, name);
        if (newUser != null) {
            System.out.println("User created sucessfully");
            System.out.println(newUser);
        } else {
            System.out.println("User already exist");
        }
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

        String[] userInput = { username, password };
        String[] loginRecord = Loader.rawSearcher(file, userInput, true, true);
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
    }

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

    public Admin Admin_Login() {
        return Admin_Login(inputAccount());
    }
}