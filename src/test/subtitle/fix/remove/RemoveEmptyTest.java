package test.subtitle.fix.remove;

import java.util.Collection;

import main.subtitle.fix.remove.RemoveEmpty;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link RemoveEmpty} test.
 * 
 * @author budi
 */
public class RemoveEmptyTest extends BaseSubtitleTest {

    /**
     * {@link RemoveEmpty} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(RemoveEmptyTest.class);

        collection.add("-", "");
        collection.add("##", "");
        collection.add("- ##", "");
        collection.add("???", "");
        collection.add("...", "");
        collection.add("!", "");
        collection.add("- ...", "");

        return collection.getCollection();
    }

}
