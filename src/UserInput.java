import java.util.Scanner;
import java.io.IOException;

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
        if (input.isEmpty() || !input.equalsIgnoreCase(yes)) {
            return false;
        }
        return true;
    }
    public static double[] getPriceRange(String min, String max) {
        try {
            Double realMin = Double.parseDouble(min);
            Double realMax = Double.parseDouble(max);
            return new double[] {
                    realMin, realMax
            };
        } catch (Exception e) {
            System.out.println("Invalid range. Showing all prices");
            return new double[] {
                    0, Integer.MAX_VALUE
            };
        }
    }

    public static void clearConsole() { // Source: https://stackoverflow.com/a/64038023
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
        }
    }

    public static void waitForKeyPress() {
        System.out.println("Press any key to continue...");
        getInput();
    }
}