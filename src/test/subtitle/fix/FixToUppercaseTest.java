package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixToUppercase;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixToUppercase} test.
 * 
 * @author budi
 */
public class FixToUppercaseTest extends BaseSubtitleTest {

    /**
     * {@link FixToUppercase} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixToUppercaseTest.class);
        collection.add("ok", "OK");
        collection.add("Ok", "OK");
        collection.add("OK", "OK");

        collection.add("i", "I");
        collection.add("I", "I");

        collection.add("usa", "USA");
        collection.add("Usa", "USA");
        collection.add("uSa", "USA");
        collection.add("usA", "USA");
        collection.add("USa", "USA");
        collection.add("uSA", "USA");
        collection.add("USA", "USA");

        collection.add("ok Ok OK", "OK OK OK");
        collection.add("I i I", "I I I");
        collection.add("ok i know", "OK I know");
        collection.add("ok... i know", "OK... I know");

        //collection.add("ok usa, i'm ok", "OK USA, I'm OK", TestProperty.EXCLUDE_ORIGINAL);
        return collection.getCollection();
    }
}
