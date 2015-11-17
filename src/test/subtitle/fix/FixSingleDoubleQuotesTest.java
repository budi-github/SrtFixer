package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixSingleDoubleQuotes;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixSingleDoubleQuotes} test.
 * 
 * @author budi
 */
public class FixSingleDoubleQuotesTest extends BaseSubtitleTest {

    /**
     * {@link FixSingleDoubleQuotes} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixSingleDoubleQuotesTest.class);

        collection.add("\"Hello World\"", "\"Hello World\"");
        collection.add("''Hello World''", "\"Hello World\"");
        collection.add("'''Hello World'''", "\"Hello World\"");
        collection.add("\"Hello World\"", "\"Hello World\"");
        collection.add("'''It's me.''''", "\"It's me.\"");
        collection.add("''''Oh'', he said, ''No'''", "\"Oh\", he said, \"No\"");
        collection.add("''This''' Is ''Fine''", "\"This\" Is \"Fine\"");

        collection.add("\"Hmm' '", "\"Hmm\"");
        collection.add("\"Wow.\nSuch wow.' '", "\"Wow.\nSuch wow.\"");

        collection.add("This is correct!");

        return collection.getCollection();
    }

}
