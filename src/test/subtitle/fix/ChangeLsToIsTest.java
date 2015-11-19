package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.ChangeLsToIs;
import test.ParameterizedCollection;
import test.TestProperty;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link ChangeLsToIs} test.
 * 
 * @author budi
 */
public class ChangeLsToIsTest extends BaseSubtitleTest {

    /**
     * {@link ChangeLsToIs} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(ChangeLsToIsTest.class);

        collection.add("l", "I");
        collection.add("ll");
        collection.add("l I l", "I I I");

        collection.add("I'll");
        collection.add("lll", "Ill");
        collection.add("l'll", "I'll");
        collection.add("l'll lll", "I'll Ill", TestProperty.EXCLUDE_CAPITALIZE);

        collection.add("l'm Bob", "I'm Bob");
        collection.add("l'm ln", "I'm In", TestProperty.EXCLUDE_CAPITALIZE);

        collection.add("lt's me", "It's me");
        collection.add("lt's not it's", "It's not it's");
        collection.add("lt's lt's lts lts", "It's It's Its Its", TestProperty.EXCLUDE_CAPITALIZE);

        collection.add("l am who l am", "I am who I am");
        collection.add("l am who l've been", "I am who I've been");
        collection.add("l... l don't know", "I... I don't know");

        collection.add("Am l", "Am I");
        collection.add("A ll");
        collection.add("All");

        collection.add("lollipop");

        collection.add("l-l", "I-I");
        collection.add("l'm-l'm", "I'm-I'm");
        collection.add("l'm-l'm... the one!", "I'm-I'm... the one!");

        return collection.getCollection();
    }

}
