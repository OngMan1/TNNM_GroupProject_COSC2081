import java.util.Scanner;

public class UserInput {
    private static Scanner sc = new Scanner(System.in);

    public static String getInput() {
        String input = sc.nextLine();
        return input;
    }
}
