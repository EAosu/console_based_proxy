package proxy.flags;
import java.io.IOException;

/**
 * The HTMLFlag class represents the flag for HTML content type.
 * It extends the abstract Flag class and implements the isValid method to check if the
 * content type is valid for this flag.
 * If the content type starts with the HTML_PREFIX, it is not valid and an IOException with DENIED message is thrown.
 */
public class HTMLFlag extends Flag {
    /**
     * Constructs a new HTMLFlag object.
     * @param conType the content type associated with this flag
     */
    public HTMLFlag(String conType){
        super(conType);
    }

    /**
     * Checks if the content type is valid for this flag.
     * @return true if the content type is valid, false otherwise
     */
    @Override
    public Boolean isValid() {
        try{
            if (data.startsWith(HTML_PREFIX)) {
                throw new IOException(DENIED);
            }
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
