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

/**
 * Punctuation test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class PunctuationTest extends BaseTest {

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
    public PunctuationTest(String expected, String actual, List<TestProperty> properties,
            ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test comma.
     * 
     * Adds a comma (,) to the end of the line.
     */
    @Test
    public void testComma() {
        String p = ",";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test dash.
     * 
     * Adds a dash (-) to the end of the line.
     */
    @Test
    public void testDash() {
        String p = "-";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test exclamation point.
     * 
     * Adds an exclamation point (!) to the end of the line.
     */
    @Test
    public void testExclamationPoint() {
        String p = "!";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test percent.
     * 
     * Adds a percent (%) to the end of the line.
     */
    @Test
    public void testPercent() {
        String p = "%";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test period.
     * 
     * Adds a period (.) to the end of the line.
     */
    @Test
    public void testPeriod() {
        String p = ".";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test question mark.
     * 
     * Adds a question mark (?) to the end of the line.
     */
    @Test
    public void testQuestionMark() {
        String p = "?";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test semi colon.
     * 
     * Adds a semi colon (;) to the end of the line.
     */
    @Test
    public void testSemiColon() {
        String p = ";";
        assertEquals(StringUtil.append(expected, p), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test multiple exclamation points.
     * 
     * Adds multiple exclamation points (!!!) to the end of the line.
     */
    @Test
    public void testMultipleExclamationPoints() {
        String p = "!!!";
        assertEquals(StringUtil.append(expected, "!"), fix(StringUtil.append(actual, p)));
    }

    /**
     * Test multiple question marks.
     * 
     * Adds multiple multiple question marks (???) to the end of the line.
     */
    @Test
    public void testMultipleQuestionMarks() {
        String p = "???";
        assertEquals(StringUtil.append(expected, "?"), fix(StringUtil.append(actual, p)));
    }

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()));
        assumeFalse(StringUtil.isPunctuation(expected.charAt(expected.length() - 1)));
    }

}
