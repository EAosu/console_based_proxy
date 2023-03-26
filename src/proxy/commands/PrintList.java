package proxy.commands;
import proxy.Errors;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 The PrintList class implements the Command interface, used to execute the command of printing the contents of a file to the console.
 This class reads the contents of the specified file and prints them to the console.
 */

public class PrintList implements Command, Errors {
    /**
     * Default c-tor.
     */
    public PrintList() {}

    /**
     * Reads the contents of a file and prints them to the console.
     */
    @Override
    public void execute() {
        try {
            List<String> fileContents = readFileContents();
            printFileContents(fileContents);
        } catch (IOException ioe) {
            System.out.println(READ_ERROR);
        }
    }

    /**
     * Reads the contents of the specified file.
     *
     * @return A list containing the lines of the file.
     * @throws IOException If there is an error while reading the file.
     */
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

    /**
     * Prints the contents of the file to the console.
     *
     * @param fileContents A list containing the lines of the file to print.
     */
    private void printFileContents(List<String> fileContents) {
        for (String line : fileContents) {
            System.out.println(line);
        }
        System.out.println();
    }
}
