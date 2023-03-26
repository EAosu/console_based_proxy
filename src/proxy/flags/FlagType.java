package proxy.flags;

/**
 * An enum that represents the different types of flags that can be applied to a connection.
 */
public enum FlagType {
    /**
     * Images - Blocks images.
     */
    Images,

    /**
     * Cookies - Blocks HTTP responses containing cookies.
     */
    Cookies,

    /**
     * HTML - Blocks HTML documents.
     */
    HTML,

    /**
     * FromFile - Blocks based on the list in the file.
     */
    FromFile
}
