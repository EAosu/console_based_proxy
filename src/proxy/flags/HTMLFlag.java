package proxy.flags;

import java.io.IOException;

public class HTMLFlag extends Flag {
    public HTMLFlag(String conType){
        super(conType);
    }
    @Override
    public Boolean isValid(){
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
