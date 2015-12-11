package main.subtitle;

import java.util.HashSet;
import java.util.Set;

/**
 * Exceptions to lines that are incorrectly or not fixed in the program.
 * 
 * @author budi
 */
public class DoNotFix {

    /**
     * Set of correct lines that are interpreted to be incorrect.
     */
    public static final Set<String> DO_NOT_FIX;

    static {
        DO_NOT_FIX = new HashSet<String>();
        
        // insert exceptions here
    }

}
