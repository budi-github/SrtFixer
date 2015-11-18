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
        DO_NOT_FIX.add("Twenty-six years old.\nHe's batting .350. Three-fifty."); // 42, 110
        DO_NOT_FIX.add("He batted .625 in the exhibition games\nagainst them."); // 42, 803
        DO_NOT_FIX.add("...batting .301 with 31 doubles\nand 10 home runs."); // 42, 1625
        DO_NOT_FIX.add("Your truck's ready. Im... impossible."); // American Sniper, 877
        DO_NOT_FIX.add("bl-i-i-i-i-nd!"); // Anchorman 2 The Legend Continues, 1858
        DO_NOT_FIX.add("looking like... l... like...\nJodie Foster from Taxi Driver?"); // Begin Again, 190
        DO_NOT_FIX.add("For jazz, check out the Hungry i.\nFor Italian, Vanessi's."); // Big Eyes, 21
        DO_NOT_FIX.add("Order 16. 16."); // Chef, 1891
        DO_NOT_FIX.add("- 16. 16, here.\n- Thank you."); // Chef, 1892
        DO_NOT_FIX.add("Get 'im, get 'im!"); // Django Unchained, 1018
        DO_NOT_FIX.add("But that **** James"); // Get Hard, 498
        DO_NOT_FIX.add("Regs section 1.469-2 (B) (I), Diane.\nThanks."); // Stranger Than Fiction, 23
    }

}
