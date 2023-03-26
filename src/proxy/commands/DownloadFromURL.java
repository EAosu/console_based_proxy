package proxy.commands;

import proxy.*;
import proxy.flags.*;
import javax.naming.ServiceUnavailableException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;
import java.util.function.Supplier;

/**
 * DownloadFromURL is a class that implements the Command interface, allowing users to download content from a specified URL
 * with various filtering options.
 */
public class DownloadFromURL implements Command, Errors {
    /**
     * An instance of the "FlagFactory" class, used to create instances of denying flags based on the given option.
     */
    FlagFactory flagFactory = new FlagFactory();

    /**
     * An instance of the "HttpURLConnection" class, used to establish a connection to the URL.
     */
    HttpURLConnection con;

    /**
     * The name of the file where the downloaded contents will be saved
     * and the denying options that will be applied to the download, represented as a string of characters.
     */
    String out, flags;

    /**
     * The URL to download from.
     */
    private final URL url;

    /**
     * The HTTP status code returned by the URL.
     */
    private Integer statusCode;

    /**
     * Constants representing the HTTP header fields "Content-Type" and "Set-Cookie".
     */
    public static final String CONTENT_FIELD = "Content-Type", COOKIE_FIELD = "Set_Cookie";

    /**
     * Constants representing the denying options that can be applied to the download.
     * "BLOCK_IMAGES" represents the option to block images,
     * "BLOCK_COOKIES" represents the option to block HTTP responses containing cookies,
     * "BLOCK_HTML" represents the option to block HTML documents
     * and "BLOCK_FROM_LIST" represents the option to deny access to blocked sites from the list in the file.
     */
    public static final char BLOCK_IMAGES = 'i', BLOCK_COOKIES = 'c', BLOCK_HTML = 'h', BLOCK_FROM_LIST = 'b';

    /**
     * Constructs a DownloadFromURL object with the provided URL, flags, and output file path.
     *
     * @param url   The URL to download the content from.
     * @param flags A string containing flags that determine the filtering options.
     * @param out   The path to the output file where the downloaded content will be saved.
     * @throws IOException If an error occurs while initializing the connection.
     */
    public DownloadFromURL(String url, String flags, String out) throws IOException {
        this.url = new URL(url);
        this.con =  getConnection();
        this.out = out;
        this.flags = flags;
        //'-ibh'
        for (int i = 0; i < flags.length(); i++) {
            registerFlag(flags.charAt(i));
        }
    }

    /**
     * Registers a flag to the flagFactory based on the flag character provided.
     *
     * @param flag The flag character representing the filtering option.
     */
    public void registerFlag(char flag) {
        Supplier<Flag> supplier;
        FlagType type;
        switch (flag) {
            case BLOCK_IMAGES -> {
                String conTypeForImage = this.con.getHeaderField(CONTENT_FIELD);
                supplier = () -> new ImageFlag(conTypeForImage);
                type = FlagType.Images;
            }
            case BLOCK_HTML -> {
                String conTypeForHTML = this.con.getHeaderField(CONTENT_FIELD);
                supplier = () -> new HTMLFlag(conTypeForHTML);
                type = FlagType.HTML;
            }
            case BLOCK_FROM_LIST -> {
                supplier = () -> new FromListFlag(fileName, url);
                type = FlagType.FromFile;
            }
            case BLOCK_COOKIES -> {
                String cookieHeader = this.con.getHeaderField(COOKIE_FIELD);
                supplier = () -> new CookiesFlag(cookieHeader);
                type = FlagType.Cookies;
            }
            default -> throw new IllegalArgumentException(INVALID_OPTION);
        }
        flagFactory.register(type, supplier);
    }

    /**
     * Initializes an HttpURLConnection object and connects to the specified URL.
     *
     * @return The HttpURLConnection object connected to the specified URL.
     * @throws IOException If an error occurs while opening the connection.
     */
    public HttpURLConnection getConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();
        con.setRequestMethod("GET");
        // now we connect
        con.connect();

        // get the HTTP response code
        this.statusCode = con.getResponseCode();
        return con;
    }

    /**
     * Downloads the content from the specified URL and saves it to the output file path.
     *
     * @throws IOException If an error occurs while downloading or saving the content.
     */
    private void download() throws IOException {
        InputStream input = new BufferedInputStream(url.openStream());
        OutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(out));
            int b;
            while ((b = input.read()) != -1) {
                output.write(b);
            }
        } catch (IOException e) {
            // Throw an exception when failing to open output file
            throw new IOException(OUTPUT_ERROR);
        } finally {
            // Close the output file only if it was opened successfully
            if (output != null) {
                try { output.close(); } catch (IOException e) {}
            }
            // Close the input stream regardless of whether the output file was opened successfully or not
            try { input.close(); } catch (IOException e) {}
        }
    }

    /**
     * Executes the command, downloading the content from the specified URL and applying the filtering options
     * based on the provided flags.
     */
    @Override
    public void execute() {
        try {
            for (int i = 0; i < flags.length(); i++) {
                FlagType type = getType(flags.charAt(i));
                Flag currFlag = flagFactory.getFlag(type);
                if (currFlag != null && type != FlagType.FromFile && statusCode != HttpURLConnection.HTTP_OK)
                        throw new ServiceUnavailableException(statusCode.toString());
                else if (currFlag != null && !currFlag.isValid()) return;
            }
            if(statusCode == HttpURLConnection.HTTP_OK) download();
            else throw new ServiceUnavailableException(statusCode.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Retrieves the FlagType corresponding to the flag character provided.
     *
     * @param c The flag character representing the filtering option.
     * @return The FlagType corresponding to the flag character.
     */
    public FlagType getType(char c) {
        return switch (c) {
            case BLOCK_IMAGES -> FlagType.Images;
            case BLOCK_COOKIES -> FlagType.Cookies;
            case BLOCK_HTML -> FlagType.HTML;
            case BLOCK_FROM_LIST -> FlagType.FromFile;
            default -> null;
        };
    }
}
