package proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FlagFactory {
    final static Map<FlagType, Supplier<Flag>> map = new HashMap<>();
    public static void register(FlagType type, Supplier<Flag> flagCreateFunction) {
        map.put(type, flagCreateFunction);
    }
    public Flag getFlag(FlagType type) {
        // get the supplier for the flag type: a reference to the "new" method
        Supplier<Flag> flagFunc = map.get(type); //

        if (flagFunc != null) {
            return flagFunc.get(); // this is equivalent to calling the constructor of the flag
        }
        else
            return null;
    }
}