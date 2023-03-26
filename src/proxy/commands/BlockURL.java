package proxy.commands;

import proxy.Errors;
import java.io.*;

public class BlockURL implements Command, Errors {
    private String URL;

    public BlockURL(String URL) {
        this.URL = URL;
    }

    @Override
    public void execute() {
        try {
            File file = getFile(fileName);
            Writer writer = getWriter(file);
            writeContent(writer, file);
            closeWriter(writer);
        } catch (IOException ioe) {
            System.err.println(WRITE_ERROR);
        }
    }

    private File getFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private Writer getWriter(File file) throws IOException {
        return new FileWriter(file, true);
    }

    private void writeContent(Writer writer, File file) throws IOException {
        writer.write(URL);
        writer.write(System.lineSeparator());
    }

    private void closeWriter(Writer writer) {
        try {
            writer.close();
        } catch (Exception e) {}
    }
}
