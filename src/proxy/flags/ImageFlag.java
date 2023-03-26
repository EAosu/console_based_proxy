package proxy.flags;
import java.io.IOException;

/**
 * The `ImageFlag` class represents a flag that blocks image content.
 */
public class ImageFlag extends Flag {

    /**
     * Constructs an `ImageFlag` object with the specified connection type.
     *
     * @param conType the connection type
     */
    public ImageFlag(String conType){
        super(conType);
    }

    /**
     * Checks whether the connection type starts with the image prefix and blocks image content if it does.
     *
     * @return true if the connection type does not start with the image prefix, false otherwise
     */
    @Override
    public Boolean isValid() {
        try{
            if (data.startsWith(IMAGE_PREFIX)) {
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
