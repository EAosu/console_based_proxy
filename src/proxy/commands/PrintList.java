package proxy.commands;
import proxy.Errors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrintList implements Command, Errors {
    @Override
    public void execute() {
        try {
            List<String> fileContents = readFileContents();
            printFileContents(fileContents);
        } catch (IOException ioe) {
            System.out.println(READ_ERROR);
        }
    }

    private List<String> readFileContents() throws IOException {
        List<String> fileContents = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            fileContents.add(line);
        }
        reader.close();
        return fileContents;
    }

    private void printFileContents(List<String> fileContents) {
        for (String line : fileContents) {
            System.out.println(line);
        }
    }

}
