package test.base.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import main.util.StringUtil;
import test.ClassContainer;
import test.Parallelized;
import test.TestProperty;
import test.base.BaseTest;
import test.subtitle.BaseSubtitleTest;
import test.subtitle.fix.FixEllipsesTest;
import test.subtitle.fix.fixMultipleLines.FixOneLineTest;
import test.subtitle.fix.fixMultipleLines.FixThreeLinesTest;
import test.subtitle.fix.fixMultipleLines.FixTwoLinesTest;
import test.subtitle.fix.remove.RemoveCharacterNameTest;

/**
 * Ellipses test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class EllipsesTest extends BaseTest {

    /**
     * Set of tests to exclude.
     */
    private static final Set<Class<? extends BaseSubtitleTest>> EXCLUDE;

    static {
        EXCLUDE = new HashSet<Class<? extends BaseSubtitleTest>>();
        EXCLUDE.add(FixEllipsesTest.class);
        EXCLUDE.add(FixOneLineTest.class);
        EXCLUDE.add(FixTwoLinesTest.class);
        EXCLUDE.add(FixThreeLinesTest.class);
        EXCLUDE.add(RemoveCharacterNameTest.class); // TODO: fix
    }

    /**
     * Class constructor.
     * 
     * @param expected expected value
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     * @param classContainer {@link ClassContainer}
     */
    public EllipsesTest(String expected, String actual, List<TestProperty> properties, ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test prepend and append ellipses.
     * 
     * Adds ellipses (...) to the beginning and the end of the line.
     */
    @Test
    public void testPrependAndAppendEllipses() {
        assumeFalsePrependEllipses();
        assumeFalseAppendEllipses();
        assertEquals(StringUtil.prependAndAppend(expected, "...", "..."),
                fix(StringUtil.prependAndAppend(actual, "...", "...")));
    }

    /**
     * Test prepend ellipses.
     * 
     * Adds ellipses (...) to the beginning of the line.
     */
    @Test
    public void testPrependEllipses() {
        assumeFalsePrependEllipses();
        assertEquals(StringUtil.prepend(expected, "..."), fix(StringUtil.prepend(actual, "...")));
    }

    /**
     * Test append ellipses.
     * 
     * Adds ellipses (...) to the end of the line.
     */
    @Test
    public void testAppendEllipses() {
        assumeFalseAppendEllipses();
        assertEquals(StringUtil.append(expected, "..."), fix(StringUtil.append(actual, "...")));
    }

    /**
     * Test prepend and append malformed ellipses.
     * 
     * Adds malformed ellipses (..) to the beginning and the end of the line.
     */
    @Test
    public void testPrependAndAppendMalformedEllipses() {
        assumeFalsePrependEllipses();
        assumeFalseAppendEllipses();
        assertEquals(StringUtil.prependAndAppend(expected, "...", "..."),
                fix(StringUtil.prependAndAppend(actual, "..", "..")));
    }

    /**
     * Test prepend malformed ellipses.
     * 
     * Adds malformed ellipses (..) to the beginning of the line.
     */
    @Test
    public void testPrependMalformedEllipses() {
        assumeFalsePrependEllipses();
        assertEquals(StringUtil.prepend(expected, "..."), fix(StringUtil.prepend(actual, "..")));
    }

    /**
     * Test append malformed ellipses.
     * 
     * Adds malformed ellipses (..) to the end of the line.
     */
    @Test
    public void testAppendMalformedEllipses() {
        assumeFalseAppendEllipses();
        assertEquals(StringUtil.append(expected, "..."), fix(StringUtil.append(actual, "..")));
    }

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()));
    }

    /**
     * Assumptions to prepend ellipses.
     */
    private void assumeFalsePrependEllipses() {
        assumeFalse(expected.startsWith("...") || expected.startsWith("-") || expected.startsWith("\"...")
                || expected.startsWith("#"));
    }

    /**
     * Assumptions to append ellipses.
     */
    private void assumeFalseAppendEllipses() {
        assumeFalse(
                expected.endsWith(".") || expected.endsWith("\"") || expected.endsWith("'") || expected.endsWith("#"));
    }

}
