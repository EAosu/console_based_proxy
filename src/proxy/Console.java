package proxy;

import proxy.commands.*;
import java.net.UnknownHostException;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Console implements Errors {
    public static final int SUCCESS = 0, UB_ARGUMENT_MIN = 2 ,DOWNLOAD_ARGUMENT_MIN = 3;
    public static final int COMMAND = 0, FLAGS_OR_URL = 1, OUT_OR_URL = 2, OUT = 3;
    public static final String BLOCK_URL = "b", UNBLOCK_URL = "u", PRINT = "p", DOWNLOAD = "d", EXIT = "q";
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            try {
                switch (tokens[COMMAND]) {
                    case BLOCK_URL -> {
                        checkUBArguments(tokens);
                        if (isValidUrl(tokens[FLAGS_OR_URL]))
                            executeCommand(new BlockURL(tokens[FLAGS_OR_URL]));
                        else throw new IllegalArgumentException(INVALID_URL);
                    }
                    case UNBLOCK_URL -> {
                        checkUBArguments(tokens);
                        executeCommand(new UnblockURL(tokens[FLAGS_OR_URL]));
                    }
                    case PRINT -> executeCommand(new PrintList());
                    case DOWNLOAD -> executeDownload(tokens);
                    case EXIT -> exit();
                    default -> System.out.println(INVALID_COMMAND);
                }
            } catch (UnknownHostException e) {
                System.out.println(INVALID_URL);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkUBArguments(String[] tokens) throws IllegalArgumentException {
        if (tokens.length < UB_ARGUMENT_MIN)
            throw new IllegalArgumentException(INVALID_COMMAND);
    }
    private void executeCommand(Command command) {
        command.execute();
    }
    private void exit() {
        System.exit(SUCCESS);
    }

    private void executeDownload(String[] tokens) throws IOException {
        String flags;
        String url;
        String out;

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
