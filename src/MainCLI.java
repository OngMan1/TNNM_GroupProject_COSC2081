interface MainCLIIndex {
    String LOG_IN_ADMIN = "0";
    String LOG_IN_CUSTOMER = "1";
    String CREATE_CUSTOMER_ACCOUNT = "2";
}

public class MainCLI implements CLI, MainCLIIndex {

    public boolean run() {
        boolean state = true;
        Utilities.printStringArray(getWelcomeScreen());
        UserInput.waitForKeyPress();
        while (state) {
            UserInput.clearConsole();
            System.out.println("MAIN SCREEN: ");
            Utilities.printStringBullet(getOptions());
            System.out.println("Enter an option: ");
            String command = UserInput.getInput();
            state = executeCommand(command);
            System.out.println("Continue Main Screen? (Y/N): ");
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
        CLI session;
        switch (command) {
            case LOG_IN_ADMIN:
                session = new AdminCLI();
                session.run();
                break;
            case LOG_IN_CUSTOMER:
                session = new CustomerCLI();
                session.run();
                break;
            case CREATE_CUSTOMER_ACCOUNT:
                // createCustomerAccount();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        return false;

    }

    public String[] getWelcomeScreen() {
        String[] welcome = {
                "CBRPNK2077 GROUP ASSIGNMENT",
                "NEW WORLD ORDER MANAGEMENT SYSTEM",
                "Instructor: Jordan Peterson",
                "Group: SuperIdolDeXiaoRong",
                "BugSnax 1, Student Name",
                "BugSnax 2, Student Name",
                "BugSnax 3, Student Name",
                "BugSnax 4, Student Name",
        };
        return welcome;
    }

    public String[] getOptions() {
        String[] options = {
                "Login as ADMIN",
                "Login as CUSTOMER",
                "Create a CUSTOMER account"
        };
        return options;
    }

}