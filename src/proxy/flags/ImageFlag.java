package proxy.flags;

import java.io.IOException;

public class ImageFlag extends Flag
{
    public ImageFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
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
