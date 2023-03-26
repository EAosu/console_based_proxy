package proxy;
import proxy.commands.*;
import java.net.UnknownHostException;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The Console is the main component in the program, it accepts commands from the user.
 */
public class Console implements Errors {
    /**
     * Default c-tor.
     */
    public Console() {};

    /**
     * SUCCESS is for exiting successfully, UB_ARGUMENT_MIN/DOWNLOAD_ARGUMENT_MIN are for arguments confirmation.
     */
    public static final int SUCCESS = 0, UB_ARGUMENT_MIN = 2 ,DOWNLOAD_ARGUMENT_MIN = 3;

    /**
     * The following represent the symbolic indexes of the input.
     */
    public static final int COMMAND = 0, FLAGS_OR_URL = 1, OUT_OR_URL = 2, OUT = 3;

    /**
     * The following represent the console-based commands.
     */
    public static final String BLOCK_URL = "b", UNBLOCK_URL = "u", PRINT = "p", DOWNLOAD = "d", EXIT = "q";

    /**
     * Starts the console interface for the Proxy application and listens for user input until the application is exited.
     * The method reads input from the standard input stream and parses it to execute the corresponding command.
     * The supported commands are block URL, unblock URL, print blocked URLs list, download file from URL, and exit.
     * The method executes the corresponding command object based on the user input and handles any exceptions that may occur.
     */
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
    /**
     * Checks the given array of tokens to ensure that it has at least the minimum number of arguments required for block URL
     * or unblock URL command, and throws an IllegalArgumentException if it doesn't.
     *
     * @param tokens the array of tokens to check for the required number of arguments
     * @throws IllegalArgumentException if the array does not have the required number of arguments
     */
    private void checkUBArguments(String[] tokens) throws IllegalArgumentException {
        if (tokens.length < UB_ARGUMENT_MIN)
            throw new IllegalArgumentException(INVALID_COMMAND);
    }

    /**
     * Executes the given command by calling its execute() method.
     *
     * @param command the Command object to execute
     */
    private void executeCommand(Command command) {
        command.execute();
    }

    /**
     * Exits the application by calling System.exit() with the SUCCESS status code.
     */
    private void exit() {
        System.exit(SUCCESS);
    }

    /**
     * Executes the download from URL command with the given tokens by creating a DownloadFromURL object with the given
     * URL, flags, and output file name, and executing it. If the URL is invalid, throws an IllegalArgumentException.
     *
     * @param tokens the array of tokens containing the URL, flags, and output file name for the download command
     * @throws IllegalArgumentException if the URL is invalid
     * @throws IOException if there is an error reading or writing the file
     */
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

    /**
     * Checks whether the given URL string is a valid URL.
     *
     * @param urlString the string to check for validity as a URL
     * @return true if the string is a valid URL, false otherwise
     */
    public static boolean isValidUrl(String urlString) {
        try {
            URI uri = new URI(urlString);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }


}
