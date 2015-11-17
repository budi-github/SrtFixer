package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixSpelling;
import test.ParameterizedCollection;

/**
 * {@link FixSpelling} test.
 * 
 * @author budi
 */
public class FixSpellingTest {

    /**
     * {@link FixSpelling} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixWebsitesTest.class);

        collection.add("vvww.google.com", "www.google.com");
        collection.add("wvvw.FindAmazingAmy.com.", "www.FindAmazingAmy.com.");
        collection.add("wwvv.yahoo.com.", "www.yahoo.com.");
        collection.add("vvvvw.bing.com.", "www.bing.com.");
        collection.add("vvwvv.ask.com.", "www.ask.com.");
        collection.add("vvvvw.aol.com.", "www.aol.com.");
        collection.add("vvvvv.runningoutofsearchengines.com.", "www.runningoutofsearchengines.com.");

        return collection.getCollection();
    }

}
