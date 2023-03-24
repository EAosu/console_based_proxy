package proxy;

import java.io.IOException;

public class CookiesFlag extends Flag {
    public CookiesFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
        try{
            if (conType != null && conType.toLowerCase().contains("cookie")) {
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
