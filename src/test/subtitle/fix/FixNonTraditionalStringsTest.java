package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixNonTraditionalStrings;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixNonTraditionalStrings} test.
 * 
 * @author budi
 */
public class FixNonTraditionalStringsTest extends BaseSubtitleTest {

    /**
     * {@link FixNonTraditionalStrings} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixNonTraditionalStringsTest.class);

        collection.add("Jonathan’s", "Jonathan's");
        collection.add("Jonathan's fish's food", "Jonathan's fish's food");
        collection.add("Jonathan’s fish's food", "Jonathan's fish's food");
        collection.add("Jonathan's fish’s food", "Jonathan's fish's food");
        collection.add("Jonathan’s fish’s food", "Jonathan's fish's food");

        return collection.getCollection();
    }

}
