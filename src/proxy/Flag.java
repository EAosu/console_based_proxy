package proxy;
import java.util.function.Supplier;

public abstract class Flag {
    String conType= new String();

    public Flag(String connectionType){
        this.conType = connectionType;
    }
    public abstract Boolean isValid();

}
