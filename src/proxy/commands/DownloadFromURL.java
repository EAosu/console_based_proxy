package proxy.commands;

import proxy.*;
import proxy.flags.*;
import javax.naming.ServiceUnavailableException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;
import java.util.function.Supplier;

public class DownloadFromURL implements Command, Errors {
    FlagFactory flagFactory = new FlagFactory();
    HttpURLConnection con;
    String out, flags;
    private final URL url;
    private Integer statusCode;
    public static final String CONTENT_FIELD = "Content-Type", COOKIE_FIELD = "Set_Cookie";
    public static final char BLOCK_IMAGES = 'i', BLOCK_COOKIES = 'c', BLOCK_HTML = 'h', BLOCK_FROM_LIST = 'b';

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

    public HttpURLConnection getConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();
        con.setRequestMethod("GET");
        // now we connect
        con.connect();

        // get the HTTP response code
        this.statusCode = con.getResponseCode();
        return con;
    }
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
