package proxy;

import java.awt.*;
import java.io.IOException;
import java.util.function.Supplier;

public class ImageFlag extends Flag
{
    public ImageFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
        try{
            if (conType.startsWith("image/")) {
                throw new IOException("denied");
            }
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
