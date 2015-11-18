package test.subtitle.fix.fixMultipleLines;

import java.util.Collection;

import main.subtitle.fix.fixMultipleLines.FixUnbalancedDashes;
import test.ParameterizedCollection;

/**
 * {@link FixUnbalancedDashes} test.
 * 
 * @author budi
 */

public class FixUnbalancedDashesTest {

    /**
     * {@link FixUnbalancedDashes} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixTwoLinesTest.class);

        collection.add("- Hello.\n- Hi. How are you?");
        collection.add("- Hello. - Hi. How are you?", "- Hello.\n- Hi. How are you?");
        collection.add("- Hello. - Hi.\nHow are you?", "- Hello.\n- Hi. How are you?");

        collection.add("- Hello. How are you?\n- Hi.");
        collection.add("- Hello. How are you? - Hi.", "- Hello. How are you?\n- Hi.");
        collection.add("- Hello.\nHow are you? - Hi.", "- Hello. How are you?\n- Hi.");

        collection.add("- I...\n- Quit interupting.");
        collection.add("- I... - Quit interupting.", "- I...\n- Quit interupting.");
        collection.add("- I... - Quit\ninterupting.", "- I...\n- Quit interupting.");

        return collection.getCollection();
    }

}
