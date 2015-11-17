package test.subtitle.misc;

import java.util.Collection;
import java.util.Map.Entry;

import main.subtitle.ManuelFix;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link ManuelFix} test.
 * 
 * Test any exceptions in {@link ManuelFix}.
 * 
 * @author budi
 */
public class ManuelFixTest extends BaseSubtitleTest {

    /**
     * {@link ManuelFix} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(ManuelFixTest.class);

        for (Entry<String, String> entry : ManuelFix.MANUEL_FIX.entrySet()) {
            collection.add(entry.getKey(), entry.getValue());
        }

        return collection.getCollection();
    }

}
