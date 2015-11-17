package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixLetterS;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixLetterS} test.
 * 
 * @author budi
 */
public class FixLetterSTest extends BaseSubtitleTest {

    /**
     * {@link FixLetterS} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixLetterSTest.class);

        collection.add("hard 17 s", "hard 17s");
        collection.add("7.77s");
        collection.add("It was pressed metal, shot 7. 62s,", "It was pressed metal, shot 7.62s,");

        collection.add("L.A.'s number-one");
        collection.add("L.A.'s... number-one");

        return collection.getCollection();
    }

}
