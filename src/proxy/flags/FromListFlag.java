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

/**
 * A flag that blocks connections to sites listed in a file.
 */
public class FromListFlag extends Flag {
    /**
     * The set of blocked sites read from the file.
     */
    private Set<String> blockedSites;

    /**
     * The URL to which this flag applies.
     */
    private final URL url;

    /**
     * Creates a new `FromListFlag` object with the specified file name and URL.
     *
     * @param fileName the name of the file containing the list of blocked sites
     * @param url the URL to which this flag applies
     */
    public FromListFlag(String fileName, URL url) {
        super(fileName);
        this.url = url;
    }

    /**
     * Reads the list of blocked sites from the file.
     */
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

    /**
     * Determines whether this flag is valid for the current URL.
     *
     * @return `true` if the flag is valid, `false` otherwise
     */
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

    /**
     * Checks whether a given URL is blocked by this flag.
     *
     * @param url the URL to check
     * @return `true` if the URL is blocked, `false` otherwise
     */
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

