package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixWebsites;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixWebsites} test.
 * 
 * @author budi
 */
public class FixWebsitesTest extends BaseSubtitleTest {

    /**
     * {@link FixWebsites} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixWebsitesTest.class);

        collection.add("google.com");
        collection.add("Go to google.com");
        collection.add("Who uses bing.com?");

        collection.add("BeautifulHandwrittenLetters.com,");
        collection.add("BeautifulHandwrittenLetters. com,", "BeautifulHandwrittenLetters.com,");
        collection.add("BeautifulHandwrittenLetters .com,", "BeautifulHandwrittenLetters.com,");
        collection.add("BeautifulHandwrittenLetters . com,", "BeautifulHandwrittenLetters.com,");

        collection.add("Hey. Come on.");

        return collection.getCollection();
    }

}
