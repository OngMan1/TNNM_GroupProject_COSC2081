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
                Authentication tmp = new Authentication();
                tmp.customerRegistration();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        return false;

    }

    public String[] getWelcomeScreen() {
        String[] welcome = {
                "GROUP ASSIGNMENT - COSC2081",
                "NEW WORLD ORDER MANAGEMENT SYSTEM",
                "Group: TNNM",
                "Student 1, Nguyen Ngoc Minh Thu",
                "Student 2, Ong Gia Man",
                "Student 3, Nguyen Le Thu Nhan",
                "Student 4, Tran Minh Nhat",
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