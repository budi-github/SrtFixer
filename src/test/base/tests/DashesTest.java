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
import test.subtitle.fix.FixDashesTest;
import test.subtitle.fix.fixMultipleLines.FixOneLineTest;
import test.subtitle.fix.fixMultipleLines.FixThreeLinesTest;
import test.subtitle.fix.fixMultipleLines.FixTwoLinesTest;

/**
 * Dashes test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class DashesTest extends BaseTest {

    /**
     * Set of tests to exclude.
     */
    private static final Set<Class<? extends BaseSubtitleTest>> EXCLUDE;

    static {
        EXCLUDE = new HashSet<Class<? extends BaseSubtitleTest>>();
        EXCLUDE.add(FixDashesTest.class);
        EXCLUDE.add(FixOneLineTest.class);
        EXCLUDE.add(FixTwoLinesTest.class);
        EXCLUDE.add(FixThreeLinesTest.class);
    }

    /**
     * Class constructor.
     * 
     * @param expected expected value
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     * @param classContainer {@link ClassContainer}
     */
    public DashesTest(String expected, String actual, List<TestProperty> properties, ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test single line dashes.
     * 
     * Adds dash and space (- ) to the beginning of the line.
     */
    @Test
    public void testSingleLineDashes() {
        assumeFalse(expected.contains("\n"));
        assertEquals(StringUtil.prepend(expected, "- "), fix(StringUtil.prepend(actual, "-")));
    }

    /**
     * Test single line dashes, without a space.
     * 
     * Adds dash (-) to the beginning of the line.
     */
    @Test
    public void testSingleLineDashesNoSpace() {
        assumeFalse(expected.contains("\n"));
        assertEquals(StringUtil.prepend(expected, "- "), fix(StringUtil.prepend(actual, "-")));
    }

    // TODO: add dashes to multilines (For FixTwoLinesTest)

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()));
        assumeFalse(expected.startsWith("-"));
    }

}
