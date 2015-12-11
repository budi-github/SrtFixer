package test.subtitle.misc;

import java.util.Collection;

import main.subtitle.specialCases.DoNotFix;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link DoNotFix} test.
 * 
 * Test any exceptions in {@link DoNotFix}.
 * 
 * @author budi
 */
public class DoNotFixTest extends BaseSubtitleTest {

    /**
     * {@link DoNotFix} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(DoNotFixTest.class);

        for (String s : DoNotFix.DO_NOT_FIX) {
            collection.add(s);
        }

        return collection.getCollection();
    }

}
