package test.subtitle.fix;

import java.util.Collection;

import org.junit.runner.RunWith;

import main.subtitle.fix.FixEnding;
import test.Parallelized;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixEnding} test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class FixEndingTest extends BaseSubtitleTest {

    /**
     * {@link FixEnding} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixEndingTest.class);

        collection.add("Whoa .", "Whoa.");

        collection.add("Yo '", "Yo'");
        collection.add("Where are you '", "Where are you'");

        collection.add("Do '.", "Do'.");
        collection.add("Where am I '.", "Where am I'.");

        collection.add("Hey, man, I'm just sayin'. If I'm", "Hey, man, I'm just sayin'. If I'm");
        collection.add("Hey, Papi, you like my drivin'?", "Hey, Papi, you like my drivin'?");

        collection.add("What ',", "What',");
        collection.add("What are you ',", "What are you',");

        collection.add("got a face only a mother can love \"?", "got a face only a mother can love\"?");

        collection.add("Hey... \"", "Hey...\"");
        collection.add("Hello World... \"", "Hello World...\"");

        collection.add("Hi. \"", "Hi.\"");
        collection.add("Hooray. Hooray. \"", "Hooray. Hooray.\"");

        collection.add("Hot-doggin' !", "Hot-doggin'!");

        collection.add("Oi' ?", "Oi'?");
        collection.add("- \"... make friends in the real world.\"", "- \"...make friends in the real world.\"");

        return collection.getCollection();
    }
}
