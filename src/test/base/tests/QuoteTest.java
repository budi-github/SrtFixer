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
import test.subtitle.fix.FixDashesTest;
import test.subtitle.fix.FixHeightTest;
import test.subtitle.fix.FixMultilineQuotesTest;
import test.subtitle.fix.fixMultipleLines.FixOneLineTest;
import test.subtitle.fix.fixMultipleLines.FixThreeLinesTest;
import test.subtitle.fix.fixMultipleLines.FixTwoLinesTest;
import test.subtitle.fix.remove.RemoveCharacterNameTest;

/**
 * Quote test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class QuoteTest extends BaseTest {

    /**
     * Set of tests to exclude.
     */
    private static final Set<Class<? extends BaseSubtitleTest>> EXCLUDE;

    static {
        EXCLUDE = new HashSet<Class<? extends BaseSubtitleTest>>();
        EXCLUDE.add(FixOneLineTest.class);
        EXCLUDE.add(FixTwoLinesTest.class);
        EXCLUDE.add(FixThreeLinesTest.class);
        EXCLUDE.add(RemoveCharacterNameTest.class);
        EXCLUDE.add(FixDashesTest.class);
        EXCLUDE.add(FixHeightTest.class);
        EXCLUDE.add(FixMultilineQuotesTest.class);
    }

    /**
     * Class constructor.
     * 
     * @param expected expected value
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     * @param classContainer {@link ClassContainer}
     */
    public QuoteTest(String expected, String actual, List<TestProperty> properties, ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test prepend and append quote.
     * 
     * Adds quote (") to beginning and end of the line.
     */
    @Test
    public void testPrependAndAppendQuote() {
        assumeFalsePrependQuote();
        assumeFalseAppendQuote();
        assertEquals(StringUtil.prependAndAppend(expected, "\"", "\""),
                fix(StringUtil.prependAndAppend(actual, "\"", "\"")));
    }

    /**
     * Test prepend quote.
     * 
     * Adds quote (") to beginning of the line.
     */
    @Test
    public void testPrependQuote() {
        assumeFalsePrependQuote();
        assertEquals(StringUtil.prepend(expected, "\""), fix(StringUtil.prepend(actual, "\"")));
    }

    /**
     * Test append quote.
     * 
     * Adds quote (") to end of the line.
     */
    @Test
    public void testAppendQuote() {
        assumeFalseAppendQuote();
        assertEquals(StringUtil.append(expected, "\""), fix(StringUtil.append(actual, "\"")));
    }

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()) || properties.contains(TestProperty.EXCLUDE_QUOTES));
    }

    /**
     * Assumptions to prepend quote.
     */
    private void assumeFalsePrependQuote() {
        assumeFalse(expected.startsWith("\"") || expected.startsWith("-"));
    }

    /**
     * Assumptions to append quote.
     */
    private void assumeFalseAppendQuote() {
        assumeFalse(expected.endsWith("\"") || expected.endsWith("#"));
    }

}
