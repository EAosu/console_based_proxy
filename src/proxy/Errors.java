package proxy;

/**
 * Class Errors contains the errors in the program (except for error codes).
 */
public interface Errors {
    /**
     * Invalid command error message.
     */
    String INVALID_COMMAND = "invalid command";

    /**
     * Invalid URL error message.
     */
    String INVALID_URL = "invalid URL";

    /**
     * Invalid option error message.
     */
    String INVALID_OPTION = "invalid option";

    /**
     * Denied error message.
     */
    String DENIED = "denied";

    /**
     * Output error message.
     */
    String OUTPUT_ERROR = "cannot write output file";

    /**
     * Read from file error message.
     */
    String READ_ERROR = "cannot read blocked.txt";

    /**
     * Write to file error message.
     */
    String WRITE_ERROR = "cannot write blocked.txt";
}
