package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixMultilineQuotes;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixMultilineQuotes} test.
 * 
 * @author budi
 */
public class FixMultilineQuotesTest extends BaseSubtitleTest {

    /**
     * {@link FixMultilineQuotes} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixMultilineQuotesTest.class);

        collection.add("\"my wife is skilled in the\nart of vengeance\" rate.");

        return collection.getCollection();
    }
}
