import java.io.*;
import java.util.*;

// set the format for the ID
interface IDFormat {
    String numbers = "%03d";
    String numbersRegex = "0*[0-9]{3}";
}
class Utilities {
    private static final String TEXT_DELIMITER = ",";

    public static String IDFormatter(String type, int currCount) {
        return String.format("%03d", currCount);
    }

    public static String[] readParser(String content) {
        String[] parts = content.split(TEXT_DELIMITER);
        return parts;
    }

    public static String writeParser(String[] content) {
        return String.join(TEXT_DELIMITER, content);
    }

    public static void appendFile(String filename, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.append(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLine(String file, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void replaceLine(String file, String lineToReplace, String newLine) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(lineToReplace)) {
                    sb.append(newLine).append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
            reader.close();
            writeLine(file, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> loader(String file_name) {
        BufferedReader reader = null;
        ArrayList<String[]> all = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file_name));
            String line;
            while ((line = reader.readLine()) != null) {
                all.add(readParser(line));
            }
            return all;
        } catch (IOException e) { // Handle exception
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static boolean checkSearch(boolean[] all) {
        for (boolean x : all) {
            if (!x) {
                return false;
            }
        }
        return true;
    }

    // searcher(USER DETAIL, {username, password})
    public static String[] searcher(String file_name, String[] searchInput) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file_name));
            String line;
            while ((line = reader.readLine()) != null) {
                boolean[] allSearch = new boolean[searchInput.length];
                String[] parts = readParser(line);
                for (int i = 0; i < searchInput.length; i++) {
                    if (parts[i].equals(searchInput[i])) {
                        allSearch[i] = true;
                    } else {
                        break;
                    }
                }
                if (checkSearch(allSearch)) {
                    return parts;
                }
            }
            return null;
        } catch (IOException e) { // Handle exception
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static <E> void printArrayList(ArrayList<E> curr) {
        // curr.forEach(x -> System.out.println(x));
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

    public static boolean isInRange(double value, double[] range) {
        if (range.length != 2) {
            return false;
        }
        return value >= range[0] && value <= range[1];
    }

}