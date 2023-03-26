package proxy.flags;

import proxy.Errors;

public abstract class Flag implements Errors {
    String data;
    public static final String IMAGE_PREFIX = "image/";
    public static final String HTML_PREFIX = "text/html";

    public Flag(String connectionType){
        this.data = connectionType;
    }
    public abstract Boolean isValid();
}
