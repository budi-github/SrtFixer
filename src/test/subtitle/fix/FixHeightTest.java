package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixHeight;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixHeight} test.
 * 
 * @author budi
 */
public class FixHeightTest extends BaseSubtitleTest {

    /**
     * {@link FixHeight} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixHeightTest.class);

        collection.add("4'4\"", "4'4\"");
        collection.add("5'11\"", "5'11\"");
        collection.add("10'1\"", "10'1\"");
        collection.add("12'10\"", "12'10\"");

        collection.add("Doctor said he could be 6'4\".", "Doctor said he could be 6'4\".");
        collection.add("Doctor said he could be 6 '4 \".", "Doctor said he could be 6'4\".");

        collection.add("He's probably 6'7\", 6'8\".", "He's probably 6'7\", 6'8\".");

        return collection.getCollection();
    }

}
