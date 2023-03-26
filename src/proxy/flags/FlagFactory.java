package proxy.flags;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A factory class that creates Flag objects based on their type.
 */
public class FlagFactory {
    /**
     * Default c-tor.
     */
    public FlagFactory() {};

    /**
     * Data structure to keep flags.
     */
    final static Map<FlagType, Supplier<Flag>> map = new HashMap<>();

    /**
     * Registers a flag type with its corresponding creation function in the factory's map.
     *
     * @param type the type of flag to register
     * @param flagCreateFunction the function that creates a new instance of the flag
     */
    public static void register(FlagType type, Supplier<Flag> flagCreateFunction) {
        map.put(type, flagCreateFunction);
    }

    /**
     * Returns a new Flag object of the given type.
     *
     * @param type the type of flag to create
     * @return a new Flag object of the given type, or null if the type is not registered
     */
    public Flag getFlag(FlagType type) {
        // get the supplier for the flag type: a reference to the "new" method
        Supplier<Flag> flagFunc = map.get(type);

        if (flagFunc != null) {
            return flagFunc.get(); // this is equivalent to calling the constructor of the flag
        }
        else {
            return null;
        }
    }
}
