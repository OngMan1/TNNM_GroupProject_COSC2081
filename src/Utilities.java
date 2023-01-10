import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Utilities {
    private static final String TEXT_DELIMITER = ",";

    public static String IDFormatter(int currCount) {
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
                String[] parts = line.split(TEXT_DELIMITER);
                all.add(parts);
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

    public static String[] searcher(String file_name, String[] searchInput) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file_name));
            String line;
            while ((line = reader.readLine()) != null) {
                boolean[] allSearch = new boolean[searchInput.length];
                String[] parts = line.split(TEXT_DELIMITER);
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
        curr.forEach(x -> System.out.println(x));
    }

}
