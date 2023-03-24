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
    String out = new String();
    private URL url;

    public DwFromUrl(String url, String flags, String out) throws IOException {
        this.url = new URL(url);
        this.con = getConnection();
        this.out = out;
        //'-ibh'
        for (int i = 0; i < flags.length(); i++) {
            registerFlag(flags.charAt(i));
        }
    }
    public void registerFlag(char flag) {
        switch (flag) {
            case 'i':
                String conType = this.con.getHeaderField("Content-Type");
                Supplier<Flag> supplier = () -> new ImageFlag(conType);
                flagFactory.register(FlagType.Images, supplier);
                break;
            case 'h':
                //flagFactory.register(FlagType.Images, ImageFlag::new);
                //case 'b':
                //flagFactory.register(FlagType.Images, ImageFlag::new);
            case 'f':
                //flagFactory.register(FlagType.Images, ImageFlag::new);
            default:
                throw new IllegalArgumentException("invalid option");
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
        try {
            for(FlagType type : FlagType.values()){
                Flag currFlag = flagFactory.getFlag(type);
                if(currFlag == null) continue;
                else if (!currFlag.isValid()) return;
            }
            download();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void download() throws IOException {
        InputStream input = new BufferedInputStream(url.openStream());
        OutputStream output = new BufferedOutputStream(new FileOutputStream(out));
        int b;
        while ((b = input.read()) != -1) {
            output.write(b);
        }

        // we finished writing, fail on closing input is not fatal
        try { input.close(); } catch (Exception e) {}

        output.close();
    }
}
