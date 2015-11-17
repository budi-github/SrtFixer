package main.subtitle;

import java.util.HashMap;
import java.util.Map;

/**
 * Exceptions to lines that are incorrectly or not fixed in the program.
 * 
 * This class will eventually be deleted once all bugs are found.
 * 
 * @author budi
 */
public class ManuelFix {

    /**
     * Map of incorrect lines to corrected lines.
     */
    public static final Map<String, String> MANUEL_FIX;

    static {
        MANUEL_FIX = new HashMap<String, String>();
        MANUEL_FIX.put("They said, \"The guy in the gray flannel\nsuit. \" I think I said, \"The, uh,",
                "They said, \"The guy in the gray flannel\nsuit.\" I think I said, \"The, uh,"); // The Game, 722
    }

}
