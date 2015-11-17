package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixSpacing;
import main.util.StringUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;
import test.ParameterizedCollection;
import test.TestProperty;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixSpacing} test.
 * 
 * @author budi
 */
public class FixSpacingTest extends BaseSubtitleTest {

    /**
     * {@link FixSpacing} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixSpacingTest.class);

        int iterations = 3;
        addBatch(collection, "Hello", iterations);
        addBatch(collection, "Hello World", iterations);
        addBatch(collection, "I love you", iterations);
        addBatch(collection, "I... I don't know", iterations);

        collection.add("Hello : World", "Hello: World");

        collection.add("He ...", "He...");
        collection.add("Hello ... Hello", "Hello... Hello");
        collection.add("Hello ...World", "Hello... World");
        collection.add("What... is ...life?", "What... is... life?");

        collection.add(" 'Hello World '", "'Hello World'");
        collection.add(" \"Hello World \"", "\"Hello World\"");

        collection.add("Jonathan 's", "Jonathan's");
        collection.add("3 o'clock.");

        collection.add("Dash-Dash");
        collection.add("Dash - Dash", "Dash-Dash");
        collection.add("Dash-Dash-Dash");
        collection.add("Dash -Dash-Dash", "Dash-Dash-Dash");
        collection.add("Dash -Dash -Dash", "Dash-Dash-Dash");
        collection.add("Dash -Dash- Dash", "Dash-Dash-Dash");
        collection.add("Dash-Dash - Dash", "Dash-Dash-Dash");
        collection.add("Dash - Dash-Dash", "Dash-Dash-Dash");

        collection.add("The thing is-- I don't know");
        collection.add("She-- I don't--");

        collection.add("- 'Bob.'");

        collection.add("I looked it up and it said 50/50.");
        collection.add("I looked it up and it said 50/ 50.", "I looked it up and it said 50/50.");
        collection.add("I looked it up and it said 50 /50.", "I looked it up and it said 50/50.");
        collection.add("I looked it up and it said 50 / 50.", "I looked it up and it said 50/50.");

        collection.add("I 'think?'");

        collection.add("I \"love\" you", TestProperty.EXCLUDE_QUOTES);
        collection.add("\"Oh\",he said,\"No\"", "\"Oh\", he said, \"No\"");
        collection.add("Well...\"");

        collection.add("1 , 2 , 3", "1, 2, 3");
        collection.add("1,2,3", "1, 2, 3");

        collection.add("1... 2... 3...");
        collection.add("1... 2. 3...", "1... 2.3...");

        collection.add("We were paying $100");
        collection.add("We were paying $ 100", "We were paying $100");
        collection.add("$1,234,567,890");

        collection.add("@Drake Hotline Bling");
        collection.add("by @prostatewhispers.");
        collection.add("by @ prostatewhispers.", "by @prostatewhispers.");

        collection.add("Oh'.", TestProperty.EXCLUDE_QUOTES); // TODO: check quotes
        collection.add("Oh, you need a donkey kickin' .", "Oh, you need a donkey kickin'.");
        collection.add("How you doin' , Deb?", "How you doin', Deb?");

        collection.add("... speculation, Mount Moa Moa,", "...speculation, Mount Moa Moa,");

        collection.add("- Look at you. Hilarious.");
        collection.add("- Look at you.Hilarious.", "- Look at you. Hilarious.");

        collection.add("# Music #", TestProperty.EXCLUDE_QUOTES);
        collection.add("- # Boo! #\n- # Afternoon delight... #", TestProperty.EXCLUDE_QUOTES);

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
        String[] split = RegexUtil.split(RegexEnum.SPACE, line);
        for (int i = iterations - 1; i >= 0; --i) {
            for (int j = 0; j < iterations; ++j) {
                StringBuilder builder = new StringBuilder();
                for (String s : split) {
                    builder.append(StringUtil.addSpaces(s, i, j));
                    builder.append(' ');
                }
                collection.add(builder.toString(), line);
            }
        }
    }

}
