package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixEllipses;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixEllipses} test.
 * 
 * @author budi
 */
public class FixEllipsesTest extends BaseSubtitleTest {

    /**
     * {@link FixEllipses} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixEllipsesTest.class);

        collection.add("..Hello.. World..", "...Hello... World...");

        collection.add("....I", "...I");
        collection.add("...this kaleidoscopic. ..", "...this kaleidoscopic...");

        return collection.getCollection();
    }

}
