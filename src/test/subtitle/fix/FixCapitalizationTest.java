package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixCapitalization;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixCapitalization} test.
 * 
 * @author budi
 */
public class FixCapitalizationTest extends BaseSubtitleTest {

    /**
     * {@link FixCapitalization} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixCapitalizationTest.class);

        return collection.getCollection();
    }

}
