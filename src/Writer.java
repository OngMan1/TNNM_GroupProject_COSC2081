import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Writer implements Delimiter {
    public static String writeParser(String[] content) {
        return String.join(TEXT_DELIMITER, content);
    }

    public static void appendFile(String filename, String content) {
        try {
            content = content.strip();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String lastLine = "";
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            reader.close();

            if (!lastLine.isEmpty() && lastLine.charAt(lastLine.length() - 1) != '\n') {
                content = "\n" + content;
            }

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

    public static void removeLine(String fileName, String lineToRemove) {
        try {
            // Create a BufferedReader to read the file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // Create a StringBuilder to store the new file contents
            StringBuilder sb = new StringBuilder();
            // Read the file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                // If the line to remove is not the current line, add it to the new file
                // contents
                if (!line.startsWith(lineToRemove)) {
                    sb.append(line).append("\n");
                }
            }
            // Close the reader
            reader.close();
            // Convert the StringBuilder to a string
            String newFileContent = sb.toString();
            // Write the new file contents to the file
            FileWriter fw = new FileWriter(fileName);
            fw.write(newFileContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeEmptyLines(String filePath) {
        try {
            File file = new File(filePath);
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String nonEmptyLine : lines) {
                writer.write(nonEmptyLine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading/writing the file: " + e.getMessage());
        }
    }
}