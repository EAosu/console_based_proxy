package proxy.flags;
import proxy.Errors;
import java.io.IOException;

/**
 * A class representing the cookies flag.
 */
public class CookiesFlag extends Flag {

    /**
     * Constructor for creating a new CookiesFlag object.
     *
     * @param conType the connection type string
     */
    public CookiesFlag(String conType){
        super(conType);
    }

    /**
     * Checks whether the flag is valid.
     *
     * @return true if the flag is valid, false otherwise
     */
    @Override
    public Boolean isValid() {
        try{
            if (data != null) {
                throw new IOException(Errors.DENIED);
            }
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}