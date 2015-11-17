package test.subtitle.fix.fixMultipleLines;

import java.util.Collection;

import main.subtitle.fix.fixMultipleLines.FixOneLine;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixOneLine} test.
 * 
 * @author budi
 */
public class FixOneLineTest extends BaseSubtitleTest {

    /**
     * {@link FixOneLine} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixOneLineTest.class);

        collection.add("\"Well China could dominate the world economy in the next decade.\"",
                "\"Well China could dominate the world economy in the next decade.\"");

        return collection.getCollection();
    }

}
