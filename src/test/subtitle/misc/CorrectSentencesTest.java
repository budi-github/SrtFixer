package test.subtitle.misc;

import java.util.Collection;

import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * Correct sentences test.
 * 
 * Random collection of text that should already be correct.
 * 
 * @author budi
 */
public class CorrectSentencesTest extends BaseSubtitleTest {

    /**
     * Correct sentences test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(CorrectSentencesTest.class);
        collection.add("Hi.");
        collection.add("It's me. It is me.");
        collection.add("AHHH!");

        collection.add("Hello, my name is Joe.");

        collection.add("Ronde!");
        collection.add("Brother, please!");
        collection.add("Oh no! Wow!");

        collection.add("What do you mean?");
        collection.add("What is the meaning of life?");

        collection.add("It's 2:00 A.M. What are you still doing up?");

        collection.add("Rondé!");
        collection.add("Piñata.");
        collection.add("I went to the café and saw a piñata.");

        collection.add("These are the days that bind you together forever.\nAnd these little things define you.");

        collection.add("with the. 22?");
        collection.add("with the .22?");

        collection.add("No, please... come on.");

        return collection.getCollection();
    }

}
