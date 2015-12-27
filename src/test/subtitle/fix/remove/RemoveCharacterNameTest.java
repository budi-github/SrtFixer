package test.subtitle.fix.remove;

import java.util.Collection;

import main.subtitle.fix.remove.RemoveCharacterName;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link RemoveCharacterName} test.
 * 
 * @author budi
 */
public class RemoveCharacterNameTest extends BaseSubtitleTest {

    /**
     * {@link RemoveCharacterName} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(RemoveCharacterNameTest.class);

        collection.add("BOY: Gee whiz!", "Gee whiz!");
        collection.add("GIRL: Wow.", "Wow.");
        collection.add("MALE REPORTER 1: Reporting live from...", "Reporting live from...");
        collection.add("LEO: ...I haven't won an Oscar...", "...I haven't won an Oscar...");
        collection.add("1 GUY: 9:00", "9:00");
        collection.add("GUY 2: We like sportz", "We like sportz");
        collection.add("CP3O: Beep boop", "Beep boop");

        collection.add("I know this:");
        collection.add("I know this: the end is near.");
        collection.add("9:00");
        collection.add("It's 9:00");
        collection.add("P.S.: I love you.");

        collection.add("BOY: Gee whiz!\nGIRL: Wow.", "- Gee whiz!\n- Wow.");

        return collection.getCollection();
    }

}
