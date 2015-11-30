package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixDashes;
import main.util.string.StringUtil;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixDashes} test.
 * 
 * @author budi
 */
public class FixDashesTest extends BaseSubtitleTest {

    /**
     * {@link FixDashes} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixDashesTest.class);

        int iterations = 3;
        addBatch(collection, "Hello World", iterations);
        addBatch(collection, "...Hello World", iterations);
        addBatch(collection, "Dash-dash", iterations);

        collection.add("-Dash-dash", "- Dash-dash");
        collection.add("- Dash-dash");

        return collection.getCollection();
    }

    /**
     * Add batch tests.
     * 
     * @param collection collection
     * @param line line
     * @param iterations number of times to repeat string
     */
    private static void addBatch(ParameterizedCollection collection, String line, int iterations) {
        String expected = StringUtil.prepend(line, "- ");
        for (int i = 0; i < iterations; ++i) {
            for (int j = 0; j < iterations; ++j) {
                StringBuilder builder = new StringBuilder();
                builder.append(StringUtil.repeat("- ", i + 1));
                builder.append(StringUtil.repeat(" -", j + 1));
                builder.append(line);
                collection.add(builder.toString(), expected);
            }
        }
    }

}
