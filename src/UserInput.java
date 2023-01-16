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

import java.io.IOException;
import java.util.Scanner;

public class UserInput {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInput() {
        String input = scanner.nextLine();
        if (input.isBlank()) {
            return null;
        }
        return input.strip();
    }

    public static boolean getConfirmation(String yes, String no) {
        String input = getInput();
        if (input == null || !input.equalsIgnoreCase(yes)) {
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
        // is used to clear console
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

    public static Double getDoubleFromInput() {
        try {
            Double thing = Double.parseDouble(getInput());
            return thing;
        } catch (Exception e) {
            return null;
        }
    }
}