package test.subtitle.fix.fixMultipleLines;

import java.util.Collection;

import main.subtitle.fix.fixMultipleLines.FixThreeLines;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixThreeLines} test.
 * 
 * @author budi
 */
public class FixThreeLinesTest extends BaseSubtitleTest {

    /**
     * {@link FixThreeLines} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixThreeLinesTest.class);

        collection.add("And make it\nthe greatest day\nof your life!", "And make it the greatest day of your life!");
        collection.add("Do you...\ndo you think\nwe could be...", "Do you... do you think we could be...");
        collection.add("Ron, aren't you\ngoing to say\nsomething to him?",
                "Ron, aren't you going to say something to him?");

        collection.add("Look, it's not that\nyou're not attractive...\nmmm-hmm?",
                "Look, it's not that you're not attractive... mmm-hmm?");

        collection.add("San Diago. Looks like\nwe begin our search\nright here at home.",
                "San Diago.\nLooks like we begin our search right here at home.");
        collection.add("\"China could dominate\nthe world economy\nin the next decade.\"",
                "\"China could dominate the world\neconomy in the next decade.\"");

        collection.add("Hey. The History Network\nwants in on this.\nWe're news, too.",
                "Hey. The History Network wants in on this. We're news, too.");

        return collection.getCollection();
    }

}
