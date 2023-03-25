package proxy.flags;

import proxy.Errors;

import java.io.IOException;

public class CookiesFlag extends Flag {
    public CookiesFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
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
