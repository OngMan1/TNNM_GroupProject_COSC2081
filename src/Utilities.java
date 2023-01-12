import java.io.*;
import java.util.*;

// set the format for the ID
interface IDFormat {
    String numbers = "%03d";
    String numbersRegex = "0*[0-9]{3}";
}
class Utilities implements IDFormat, Membership {
    public static String IDFormatter(int currCount) {
        return String.format(numbers, currCount);
    }

    public static boolean checkSearch(boolean[] all) {
        for (boolean x : all) {
            if (!x) {
                return false;
            }
        }
        return true;
    }

    public static <E> void printArrayList(ArrayList<E> curr) {
        for (E x : curr) {
            System.out.println(x);
        }
    }

    public static <E> String arrayListToString(ArrayList<E> curr) {
        String allCurr = "";
        for (E x : curr) {
            allCurr += x.toString() + "\n";
        }
        return allCurr;
    }

    public static void printStringArray(String[] list) {
        for (String x : list) {
            System.out.println(x);
        }
    }

    public static void printStringBullet(String[] list) {
        int i = 0;
        for (String x : list) {
            System.out.println(String.format("[%d] %s", i++, x));
        }
    }

    public static <E> void printArrayBullet(ArrayList<E> curr) {
        int i = 0;
        for (E x : curr) {
            System.out.println(String.format("[%d] %s", i++, x));
        }
    }

    public static <E> void addToArrayList(ArrayList<E> to, E from) {
        to.add(from);
    }

    public static <E> boolean isNull(E thing) {
        if (thing == null) {
            return true;
        }
        return false;
    }

    public static boolean isInRange(double value, double min, double max) {
        if (min < 0 || min >= max) {
            return false;
        }
        return value >= min && value <= max;
    }

    public static boolean isInRange(double value, double[] range) {
        if (range.length != 2) {
            return false;
        }
        return isInRange(value, range[0], range[1]);
    }
}
