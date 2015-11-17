package test.subtitle.fix.fixMultipleLines;

import java.util.Collection;

import main.subtitle.fix.fixMultipleLines.FixTwoLines;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixTwoLines} test.
 * 
 * @author budi
 */
public class FixTwoLinesTest extends BaseSubtitleTest {

    /**
     * {@link FixTwoLines} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixTwoLinesTest.class);

        collection.add("And he was yelling, \"I\nlove you, I love you\",",
                "And he was yelling, \"I\nlove you, I love you\",");
        collection.add("\"I only heard his name mentioned\nin passing.\" \"I wouldn't know.\"",
                "\"I only heard his name mentioned\nin passing.\" \"I wouldn't know.\"");

        collection.add("Make it work with one line. \"I\ndidn't even know the man.\" Right?",
                "Make it work with one line. \"I\ndidn't even know the man.\" Right?");

        collection.add("I don't believe this. \"The\nunexpected virtue of ignorance\"?",
                "I don't believe this. \"The\nunexpected virtue of ignorance\"?");

        return collection.getCollection();
    }

}
