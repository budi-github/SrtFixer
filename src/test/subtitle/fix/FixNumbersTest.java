package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixNumbers;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixNumbers} test.
 * 
 * @author budi
 */
public class FixNumbersTest extends BaseSubtitleTest {

    /**
     * {@link FixNumbers} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixNumbersTest.class);

        collection.add("3. 14", "3.14");
        collection.add("3. 14, 3. 15", "3.14, 3.15");
        collection.add("3.14, 3. 14", "3.14, 3.14");

        collection.add("1,234", "1,234");
        collection.add("1, 234", "1,234");
        collection.add("1 ,234", "1,234");
        collection.add("1 , 234", "1,234");

        collection.add("1, 2, 3, 4, 5", "1, 2, 3, 4, 5");
        collection.add("01, 02", "01, 02");

        collection.add("1 2 3 4 5", "1 2 3 4 5");

        collection.add("January 1, 15", "January 1, 15");
        collection.add("January 1, 2015", "January 1, 2015");
        collection.add("January 01, 15", "January 01, 15");
        collection.add("January 01, 2015", "January 01, 2015");

        collection.add("December 21, 2012", "December 21, 2012");
        collection.add("December 21 ,2012", "December 21, 2012");
        collection.add("December 21,2012", "December 21, 2012");
        collection.add("December21,2012", "December21, 2012");

        collection.add("R2D2. R2D2, R2D2", "R2D2. R2D2, R2D2");
        collection.add("R2D2... R2D2, R2D2", "R2D2... R2D2, R2D2");

        collection.add("7.77, December 25, 2015", "7.77, December 25, 2015");
        collection.add("7. 77, December 25, 2015", "7.77, December 25, 2015");
        collection.add("7 .77, December 25, 2015", "7.77, December 25, 2015");

        collection.add("$100,000", "$100,000");
        collection.add("$100, 000", "$100,000");
        collection.add("$43. 125 million,", "$43.125 million,");

        collection.add("100 times, 200 times", "100 times, 200 times");
        collection.add("It's... We've had that\nargument, like, 100 times.",
                "It's... We've had that\nargument, like, 100 times.");

        return collection.getCollection();
    }

}
