package proxy;
import java.io.*;

public class BlockURL implements Command {
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
            System.err.println("Copying failed.");
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
        if (file.length() > 0) {
            writer.write(System.lineSeparator());
        }
        writer.write(URL);
    }

    private void closeWriter(Writer writer) {
        try {
            writer.close();
        } catch (Exception e) {}
    }
}
