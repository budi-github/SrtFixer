package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixTime;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixTime} test.
 * 
 * @author budi
 */
public class FixTimeTest extends BaseSubtitleTest {

    /**
     * {@link FixTime} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixTimeTest.class);

        collection.add("1:00");
        collection.add("2: 22", "2:22");
        collection.add("3 :14", "3:14");
        collection.add("4 : 20", "4:20");

        collection.add("12:34 P.M.");
        collection.add("8:00 a.m., sharp", "8:00 A.M., sharp");

        collection.add("It's 7:00am.");
        collection.add("8:59pm, not a minute earlier.");

        collection.add("- What time is it?\n- 10:22 A.M.");

        return collection.getCollection();
    }

}
