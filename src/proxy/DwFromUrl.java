package proxy;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.http.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.function.Supplier;

public class DwFromUrl implements Command {
    FlagFactory flagFactory = new FlagFactory();
    HttpURLConnection con;
    public DwFromUrl(String flags, String url) throws IOException {
        this.url = new URL(url);
        this.con = getConnection();
        //'-ibh'
        for (int i = 0; i < flags.length(); i++) {
            registerFlag(flags.charAt(i));
        }

    }


    public void registerFlag(char flag) {
        try{
            switch (flag) {
                case 'i':
                    String conType = this.con.getHeaderField("Content-Type");
                    Supplier<Flag> supplier = () -> new ImageFlag(conType);
                    flagFactory.register(FlagType.Images, supplier);
                case 'h':
                    //flagFactory.register(FlagType.Images, ImageFlag::new);
                case 'b':
                    //flagFactory.register(FlagType.Images, ImageFlag::new);
                case 'f':
                    //flagFactory.register(FlagType.Images, ImageFlag::new);
                default:
                    throw new IllegalArgumentException("Invalid flag: " + flag);
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public HttpURLConnection getConnection() throws IOException{
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();
        con.setRequestMethod("GET");
        // now we connect
        con.connect();
        // get the HTTP response code
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("failed to download " + fileName);
        }
        return con;
    }
    @Override
    public void execute() {
        for(FlagType type : FlagType.values()){
            Flag currFlag = flagFactory.getFlag(type);
            if(currFlag != null && currFlag.isValid()){
                System.out.println("Its valid");
            }
            else System.out.println("denied");
        }

    }


    private URL url;
}
