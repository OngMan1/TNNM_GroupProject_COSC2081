import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Writer implements Delimiter {
    public static String writeParser(String[] content) {
        return String.join(TEXT_DELIMITER, content);
    }

    public static void appendFile(String filename, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            File file = new File(filename);
            if (file.length() == 0) {
                writer.append(content);
            } else {
                writer.append("\n" + content);
            }
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
}