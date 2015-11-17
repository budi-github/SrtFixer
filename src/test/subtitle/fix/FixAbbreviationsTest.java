package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixAbbreviations;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixAbbreviations} test.
 * 
 * @author budi
 */
public class FixAbbreviationsTest extends BaseSubtitleTest {

    /**
     * {@link FixAbbreviations} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixAbbreviationsTest.class);

        collection.add("dr Mann", "Dr. Mann");
        collection.add("The dr. Mann", "The Dr. Mann");
        collection.add("Mr Smith", "Mr. Smith");
        collection.add("mrs smith", "Mrs. smith");
        collection.add("mrs", "Mrs.");
        collection.add("ms.", "Ms.");

        collection.add("Dr. Ken Jeong");
        collection.add("dr mr mrs ms", "Dr. Mr. Mrs. Ms.");

        collection.add("Mr... Smith");
        collection.add("Dr... Dr... Jones?");

        return collection.getCollection();
    }

}
