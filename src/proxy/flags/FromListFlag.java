package proxy.flags;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

public class FromListFlag extends Flag {
    private Set<String> blockedSites;
    private URL url;
    public FromListFlag(String fileName, URL url) {
        super(fileName);
        this.url = url;
    }

    private void readBlockedSites() {
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line;
            blockedSites = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                blockedSites.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println(READ_ERROR);
        }
    }

    @Override
    public Boolean isValid() {
        readBlockedSites();
        try {
            if (isBlocked(url.getHost()))
                throw new IOException(DENIED);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isBlocked(String url) {
        readBlockedSites();
        for (String blockedSite : blockedSites) {
            try {
                URI uri = new URI(url);
                URL blockedURL = new URL(blockedSite);
                URI blockedUri = new URI(blockedURL.getHost());
                if (uri.equals(blockedUri) || uri.toString().startsWith(blockedUri.toString())) {
                    return true;
                }
            } catch (URISyntaxException | MalformedURLException e) {
                return false;
            }
        }
        return false;
    }

}

