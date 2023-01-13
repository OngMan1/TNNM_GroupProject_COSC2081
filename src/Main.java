public class Main {
    public static void main(String[] args) {
        MainCLI session = new MainCLI();
        while (session.run()) {
            System.out.println("Do you want to end the program? (Y/N)");
            if (UserInput.getConfirmation("Y", "N")) {
                System.out.println("Thank you for using!");
                return;
            }
        }
    }
}