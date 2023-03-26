package proxy.commands;
import proxy.Errors;

import java.io.*;

public class UnblockURL implements Command, Errors {
    private final String URL;
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
            System.out.println(READ_ERROR);
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
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.equals(lineToRemove)) {
                modifiedContent.append(line);
                if (i != lines.length - 1) {
                    modifiedContent.append(System.lineSeparator());
                }
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
