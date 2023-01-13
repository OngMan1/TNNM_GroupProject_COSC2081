import java.util.Scanner;

public class UserInput {
    private static Scanner sc = new Scanner(System.in);

    public static String getInput() {
        String input = sc.nextLine();
        if (input.isBlank()) {
            return null;
        }
        return input.strip();
    }

    public static boolean getConfirmation(String yes, String no) {
        String input = getInput();
        if (input.equalsIgnoreCase(yes)) {
            return true;
        }
        return false;
    }
}