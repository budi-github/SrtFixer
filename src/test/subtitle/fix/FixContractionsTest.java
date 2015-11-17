package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixContractions;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixContractions} test.
 * 
 * @author budi
 */
public class FixContractionsTest extends BaseSubtitleTest {

    /**
     * {@link FixContractions} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixContractionsTest.class);

        collection.add("can't");
        collection.add("can' t", "can't");
        collection.add("can 't", "can't");
        collection.add("can ' t", "can't");

        collection.add("Uh, ma'am,");
        collection.add("Uh, ma 'am,", "Uh, ma'am,");
        collection.add("Uh, ma' am,", "Uh, ma'am,");
        collection.add("Uh, ma ' am,", "Uh, ma'am,");

        collection.add("It's it's it's.");
        collection.add("It ' s it ' s it ' s.", "It's it's it's.");

        collection.add("I haven't. I...");

        collection.add("It's its not it's.");

        collection.add("'What' do you mean?");
        collection.add("Let's-Let's go!");

        collection.add("Hey. Y 'all mind if I roll with you?", "Hey. Y'all mind if I roll with you?");

        collection.add("...Can't you, because I can't, I just can't.");
        collection.add("...Cant you, because I cant, I just cant.", "...Can't you, because I can't, I just can't.");
        collection.add("Well then");
        collection.add("We'll then");

        collection.add("Dont go!", "Don't go!");
        collection.add("Im", "I'm");
        collection.add("yall is cray cray", "y'all is cray cray");

        // UNIQUE

        collection.add("located on the highlands of Savai'i,", "located on the highlands of Savai'i,");
        collection.add("Shi'a cab driver we source is saying", "Shi'a cab driver we source is saying");
        collection.add("c'mon", "c'mon");
        collection.add("s'il", "s'il");
        collection.add("Can I join the gang, s'il vous plaît?", "Can I join the gang, s'il vous plaît?");

        // EXCEPTIONS

        collection.add("It's powder. Are we at van 't Hoff?", "It's powder. Are we at van 't Hoff?");

        return collection.getCollection();
    }
}
