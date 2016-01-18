package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixCommonErrors;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixCommonErrors} test.
 * 
 * @author budi
 */
public class FixCommonErrorsTest extends BaseSubtitleTest {

    /**
     * {@link FixCommonErrors} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixCommonErrorsTest.class);

        // [- "... ] -> [- "...]

        collection.add("\"... who is that?\"", "\"...who is that?\"");
        collection.add("- \"...What?");
        collection.add("- \"... super-realism.\"", "- \"...super-realism.\"");

        // [-... ] -> [- ...]

        collection.add("-... 250 miles apart.", "- ...250 miles apart.");

        // [-...] -> [- ...]

        collection.add("-...Is this the end?", "- ...Is this the end?");

        // [- ... ] -> [- ...]

        collection.add("- ... Zoinks!", "- ...Zoinks!");

        // starts with [- #]

        collection.add("- #Hello", "- # Hello");

        return collection.getCollection();
    }

}
