package test.base.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import main.util.string.StringUtil;
import test.ClassContainer;
import test.Parallelized;
import test.TestProperty;
import test.base.BaseTest;
import test.subtitle.BaseSubtitleTest;

/**
 * Whitespace test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class WhitespaceTest extends BaseTest {

    /**
     * Set of tests to exclude.
     */
    private static final Set<Class<? extends BaseSubtitleTest>> EXCLUDE;

    static {
        EXCLUDE = new HashSet<Class<? extends BaseSubtitleTest>>();
    }

    /**
     * Class constructor.
     * 
     * @param expected expected value
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     * @param classContainer {@link ClassContainer}
     */
    public WhitespaceTest(String expected, String actual, List<TestProperty> properties,
            ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test prepend and append newline.
     * 
     * Adds a newline (\n) to the beginning and the end of the line.
     */
    @Test
    public void testPrependAndAppendNewline() {
        assertEquals(expected, fix(StringUtil.prependAndAppend(actual, "\n", "\n")));
    }

    /**
     * Test prepend newline.
     * 
     * Adds a newline (\n) to the beginning of the line.
     */
    @Test
    public void testPrependNewline() {
        assertEquals(expected, fix(StringUtil.prepend(actual, "\n")));
    }

    /**
     * Test append newline.
     * 
     * Adds a newline (\n) to the end of the line.
     */
    @Test
    public void testAppendNewline() {
        assertEquals(expected, fix(StringUtil.append(actual, "\n")));
    }

    /**
     * Test prepend and append space.
     * 
     * Adds a space ( ) to the beginning and the end of the line.
     */
    @Test
    public void testPrependAndAppendSpace() {
        assertEquals(expected, fix(StringUtil.prependAndAppend(actual, " ", " ")));
    }

    /**
     * Test prepend space.
     * 
     * Adds a space ( ) to the beginning of the line.
     */
    @Test
    public void testPrependSpace() {
        assertEquals(expected, fix(StringUtil.prepend(actual, " ")));
    }

    /**
     * Test append space.
     * 
     * Adds a space ( ) to the end of the line.
     */
    @Test
    public void testAppendSpace() {
        assertEquals(expected, fix(StringUtil.append(actual, " ")));
    }

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()));
    }

}
