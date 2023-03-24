package proxy;
import java.io.*;

public class UnblockURL implements Command {
    private String URL;
    public UnblockURL(String URL) {
        this.URL = URL;
    }
    @Override
    public void execute() {
        try {
            File file = new File(fileName);
            String fileContent = getFileContent(file);
            String modifiedFileContent = removeLine(fileContent, URL);
            writeContentToFile(file, modifiedFileContent);
        } catch (IOException ioe) {
            System.err.println("Removing URL failed.");
        }
    }

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

    private String removeLine(String fileContent, String lineToRemove) {
        StringBuilder modifiedContent = new StringBuilder();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            if (!line.equals(lineToRemove)) {
                modifiedContent.append(line).append(System.lineSeparator());
            }
        }
        return modifiedContent.toString();
    }

    private void writeContentToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

}
