package proxy.flags;
import proxy.Errors;

/**
 * An abstract class that represents a flag for a connection, such as "cookies" or "images".
 */
public abstract class Flag implements Errors {
    /**
     * Each flag has different kind of String data.
     */
    String data;

    /**
     * The prefix of image type content.
     */
    public static final String IMAGE_PREFIX = "image/";

    /**
     * The prefix of HTML type content.
     */
    public static final String HTML_PREFIX = "text/html";

    /**
     * Constructs a new Flag object with the given connection type.
     *
     * @param connectionType the type of connection to apply the flag to
     */
    public Flag(String connectionType){
        this.data = connectionType;
    }

    /**
     * Checks whether the flag is valid for the given connection and returns a Boolean value accordingly.
     *
     * @return true if the flag is valid for the given connection, false otherwise
     */
    public abstract Boolean isValid();
}
