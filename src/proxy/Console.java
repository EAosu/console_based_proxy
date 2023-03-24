package proxy;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class Console {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            try {
                switch (tokens[0]) {
                    case "b":
                        if (isValidUrl(tokens[1]))
                            executeCommand(new BlockURL(tokens[1]));
                        else throw new IllegalArgumentException("Invalid URL");
                        break;
                    case "u":
                        if (isValidUrl(tokens[1]))
                            executeCommand(new UnblockURL(tokens[1]));
                        else throw new IllegalArgumentException("Invalid URL");
                        break;
                    case "p":
                        executeCommand(new PrintList());
                        break;
                    case "d":
                        executeDownload(tokens);
                        break;
                    case "q":
                        exit();
                        break;
                    default:
                        System.out.println("invalid command");
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
        System.exit(0);
    }

    private void executeDownload(String[] tokens) throws IOException {
        String flags = "";
        String url = "";
        String out = "";

        if(tokens.length < 3)
            throw new IllegalArgumentException("invalid command");
        else if (tokens.length == 3) {
            flags = "";
            url = tokens[1];
            out = tokens[2];
        } else {
            flags = tokens[1].substring(1);
            url = tokens[2];
            out = tokens[3];
        }

        if(isValidUrl(url))
            executeCommand(new DwFromUrl(url, flags, out));
        else throw new IllegalArgumentException("invalid URL");
    }
    public static boolean isValidUrl(String urlString) {
        try {
            // Create a new URL object with the given string
            URL url = new URL(urlString);

            // The URL constructor does not throw an exception
            // if the URL syntax is valid, so we need to check
            // some other properties to ensure it is valid.

            // Check the protocol (http, https, ftp, etc.)
            String protocol = url.getProtocol();
            if (!"http".equals(protocol) && !"https".equals(protocol) && !"ftp".equals(protocol)) {
                return false;
            }

            // Check that the host name is not empty
            String host = url.getHost();
            if (host == null || host.isEmpty()) {
                return false;
            }

            // Check that the path is valid
            String path = url.getPath();
            if (path == null) {
                return false;
            }

            // If all checks passed, the URL is considered valid
            return true;

        } catch (MalformedURLException e) {
            // If an exception is thrown, the URL syntax is invalid
            return false;
        }
    }
}
