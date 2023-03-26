package proxy.commands;
import proxy.Errors;
import java.io.*;

/**
 The UnblockURL class implements the Command interface and represents a command
 to unblock a specific URL in a file by removing it from the file's content.
 */
public class UnblockURL implements Command, Errors {
    /**
     * The URL string to be unblocked.
     */
    private final String URL;

    /**
     * Constructs an UnblockURL object with the specified URL string to be unblocked.
     * @param URL the URL string to be unblocked
     */
    public UnblockURL(String URL) {
        this.URL = URL;
    }

    /**
     * Executes the command to unblock the specified URL in a file.
     * The method reads the file content, removes the line containing the URL,
     * and writes the modified content back to the file.
     */
    @Override
    public void execute() {
        try {
            File file = new File(fileName);
            String fileContent = getFileContent(file);
            String modifiedFileContent = removeLine(fileContent, URL);
            writeContentToFile(file, modifiedFileContent);
        } catch (IOException ioe) {
            System.out.println(READ_ERROR);
        }
    }

    /**
     * Reads the content of a file and returns it as a string.
     * @param file the file to read
     * @return the content of the file as a string
     * @throws IOException if an I/O error occurs while reading the file
     */
    private String getFileContent(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        reader.close();
        return content.toString();
    }

    /**
     * Removes a specific line from a string of file content and returns the modified content.
     * @param fileContent the content of the file to modify
     * @param lineToRemove the line to remove from the file content
     * @return the modified file content without the specified line
     */
    private String removeLine(String fileContent, String lineToRemove) {
        StringBuilder modifiedContent = new StringBuilder();
        String[] lines = fileContent.split(System.lineSeparator());
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.equals(lineToRemove))
                modifiedContent.append(line).append(System.lineSeparator());
        }
        return modifiedContent.toString();
    }

    /**
     * Writes a string of content to a file.
     * @param file the file to write to
     * @param content the content to write to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void writeContentToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
}