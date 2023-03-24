package proxy;

import java.io.IOException;

public class HTMLFlag extends Flag {
    public HTMLFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
        try{
            if (conType.startsWith("text/html")) {
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
