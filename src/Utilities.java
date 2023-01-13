import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static double getMembershipRate(String membershipType) {
        if (membershipType.equals(SILVER))
            return SILVER_RATE;
        if (membershipType.equals(GOLD))
            return GOLD_RATE;
        if (membershipType.equals(PLATINUM))
            return PLATINUM_RATE;
        return 0;
    }

    public static ArrayList<String> getAllOrderIDs(ArrayList<Order> orders) {
        ArrayList<String> allOrderID = new ArrayList<>();
        for (Order x : orders) {
            allOrderID.add(x.getOrderID());
        }
        return allOrderID;
    }

    // Source: https://stackoverflow.com/a/8923446

    public static boolean containsPattern(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

// Source: https://stackoverflow.com/a/2459753

    public static <E> int countOccurence(ArrayList<E> list, E thing) {
        return Collections.frequency(list, thing);
    }

}