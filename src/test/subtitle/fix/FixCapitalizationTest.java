package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixCapitalization;
import test.ParameterizedCollection;
import test.TestProperty;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixCapitalization} test.
 * 
 * @author budi
 */
public class FixCapitalizationTest extends BaseSubtitleTest {

    /**
     * {@link FixCapitalization} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixCapitalizationTest.class);

        collection.add("It's aaron", "It's Aaron", TestProperty.CAPITALIZE_ONLY);

        collection.add("cia", "CIA", TestProperty.CAPITALIZE_ONLY);
        collection.add("R2D2", TestProperty.CAPITALIZE_ONLY);
        collection.add("Rico, Rico, RICO!", TestProperty.CAPITALIZE_ONLY);

        collection.add("Wow. wow? wow!", "Wow. Wow? Wow!", TestProperty.CAPITALIZE_ONLY);

        collection.add("yo Alex.", "Yo Alex.", TestProperty.CAPITALIZE_ONLY);

        collection.add("I yelled, \"Hey you guys!\"", TestProperty.CAPITALIZE_ONLY);
        collection.add("I yelled, \"hey you guys!\"", "I yelled, \"Hey you guys!\"", TestProperty.CAPITALIZE_ONLY);

        return collection.getCollection();
    }

}
