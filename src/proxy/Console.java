package proxy;
import proxy.commands.*;

import javax.naming.NamingException;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Console implements Errors {
    public static final String LINE_PREFIX = "> ";
    public static final int SUCCESS = 0, DOWNLOAD_ARGUMENT_MIN = 3;
    public static final int COMMAND = 0, FLAGS_OR_URL = 1, OUT_OR_URL = 2, OUT = 3;
    public static final String BLOCK_URL = "b", UNBLOCK_URL = "u", PRINT = "p", DOWNLOAD = "d", EXIT = "q";
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(LINE_PREFIX);
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            try {
                switch (tokens[COMMAND]) {
                    case BLOCK_URL:
                        if (isValidUrl(tokens[FLAGS_OR_URL]))
                            executeCommand(new BlockURL(tokens[FLAGS_OR_URL]));
                        else throw new IllegalArgumentException(INVALID_URL);
                        break;
                    case UNBLOCK_URL:
                        if (isValidUrl(tokens[FLAGS_OR_URL]))
                            executeCommand(new UnblockURL(tokens[FLAGS_OR_URL]));
                        else throw new IllegalArgumentException(INVALID_URL);
                        break;
                    case PRINT:
                        executeCommand(new PrintList());
                        break;
                    case DOWNLOAD:
                        executeDownload(tokens);
                        break;
                    case EXIT:
                        exit();
                        break;
                    default:
                        System.out.println(INVALID_COMMAND);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void executeCommand(Command command) {
        command.execute();
    }

    private void exit() {
        System.exit(SUCCESS);
    }

    private void executeDownload(String[] tokens) throws IOException {
        String flags = "";
        String url = "";
        String out = "";

        if(tokens.length < DOWNLOAD_ARGUMENT_MIN)
            throw new IllegalArgumentException(INVALID_COMMAND);
        else if (tokens.length == DOWNLOAD_ARGUMENT_MIN) {
            flags = "";
            url = tokens[FLAGS_OR_URL];
            out = tokens[OUT_OR_URL];
        } else {
            flags = tokens[FLAGS_OR_URL].substring(1);
            url = tokens[OUT_OR_URL];
            out = tokens[OUT];
        }

        if(isValidUrl(url))
            executeCommand(new DownloadFromURL(url, flags, out));
        else throw new IllegalArgumentException(INVALID_URL);
    }
    public static boolean isValidUrl(String urlString) {
        try {
            URI uri = new URI(urlString);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
