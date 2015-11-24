package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixAmpersand;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixAmpersand} test.
 * 
 * @author budi
 */
public class FixAmpersandTest extends BaseSubtitleTest {

    /**
     * {@link FixAmpersand} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixAmpersandTest.class);

        collection.add("Q&A");
        collection.add("Q & A");
        collection.add("Q& A", "Q & A");
        collection.add("Q &A", "Q & A");

        collection.add("M&M");
        collection.add("M & M");
        collection.add("M&M's");
        collection.add("M & M's");

        collection.add("I like M & M's");

        collection.add("Law & Order");

        return collection.getCollection();
    }

}
