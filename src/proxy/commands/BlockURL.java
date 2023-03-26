package proxy.commands;
import proxy.Errors;
import java.io.*;

/**
 The BlockURL class implements the Command interface and represents a command
 to block a specific URL in a file by adding it to the end of the file's content.
 */
public class BlockURL implements Command, Errors {
    /**
     * The URL string to be blocked.
     */
    private final String URL;

    /**
     * Constructs a BlockURL object with the specified URL string to be blocked.
     * @param URL the URL string to be blocked
     */
    public BlockURL(String URL) {
        this.URL = URL;
    }

    /**
     * Executes the command to block the specified URL in a file.
     * The method creates the file if it doesn't exist, opens a writer to append
     * the URL to the end of the file, and closes the writer.
     */
    @Override
    public void execute() {
        try {
            File file = getFile();
            BufferedWriter writer = getWriter(file);
            writeContent(writer);
            closeWriter(writer);
        } catch (IOException ioe) {
            System.err.println(WRITE_ERROR);
        }
    }

    /**
     * Returns a File object for the file to be blocked.
     * If the file doesn't exist, it is created.
     * @return the file object for the file to be blocked
     * @throws IOException if an I/O error occurs while creating the file
     */
    private File getFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * Returns a Writer object for the file to be blocked.
     * The writer is opened in append mode to add the URL to the end of the file.
     * @param file the file to create the writer for
     * @return the writer object for the file
     * @throws IOException if an I/O error occurs while creating the writer
     */
    private BufferedWriter getWriter(File file) throws IOException {
        return new BufferedWriter(new FileWriter(file, true));
    }

    /**
     * Writes the URL to the file using the specified writer object.
     * The URL is written on a new line at the end of the file.
     * @param writer the writer object to write the URL to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void writeContent(BufferedWriter writer) throws IOException {
        writer.write(URL);
        writer.newLine();
    }

    /**
     * Closes the specified writer object.
     * Any errors that occur while closing the writer are ignored.
     * @param writer the writer object to close
     */
    private void closeWriter(BufferedWriter writer) {
        try {
            writer.close();
        } catch (Exception e) {}
    }
}