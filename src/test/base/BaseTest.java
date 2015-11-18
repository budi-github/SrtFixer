package test.base;

import static org.junit.Assume.assumeFalse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.runners.Parameterized.Parameters;

import main.subtitle.SubtitleObject;
import test.ClassContainer;
import test.TestProperty;
import test.base.tests.OriginalTest;
import test.subtitle.BaseSubtitleTest;
import test.subtitle.fix.ChangeLsToIsTest;
import test.subtitle.fix.FixAbbreviationsTest;
import test.subtitle.fix.FixAcronymTest;
import test.subtitle.fix.FixAmpersandTest;
import test.subtitle.fix.FixCommonErrorsTest;
import test.subtitle.fix.FixContractionsTest;
import test.subtitle.fix.FixDashesTest;
import test.subtitle.fix.FixEllipsesTest;
import test.subtitle.fix.FixEndingTest;
import test.subtitle.fix.FixHeightTest;
import test.subtitle.fix.FixLetterSTest;
import test.subtitle.fix.FixMisplacedQuotesTest;
import test.subtitle.fix.FixMultilineQuotesTest;
import test.subtitle.fix.FixNonTraditionalStringsTest;
import test.subtitle.fix.FixNumbersTest;
import test.subtitle.fix.FixSingleDoubleQuotesTest;
import test.subtitle.fix.FixSpacingTest;
import test.subtitle.fix.FixTimeTest;
import test.subtitle.fix.FixToUppercaseTest;
import test.subtitle.fix.FixWebsitesTest;
import test.subtitle.fix.fixMultipleLines.FixOneLineTest;
import test.subtitle.fix.fixMultipleLines.FixThreeLinesTest;
import test.subtitle.fix.fixMultipleLines.FixTwoLinesTest;
import test.subtitle.fix.fixMultipleLines.FixUnbalancedDashesTest;
import test.subtitle.fix.remove.RemoveCharacterNameTest;
import test.subtitle.fix.remove.RemoveEmptyTest;
import test.subtitle.misc.CorrectSentencesTest;
import test.subtitle.misc.DoNotFixTest;
import test.subtitle.misc.ManuelFixTest;

/**
 * Base test.
 * 
 * @author budi
 */
public abstract class BaseTest {

    /**
     * Expected value.
     */
    protected final String expected;

    /**
     * Actual value.
     */
    protected final String actual;

    /**
     * List of properties used to exclude specific tests.
     */
    protected final List<TestProperty> properties;

    /**
     * Class type.
     */
    protected final ClassContainer classContainer;

    /**
     * Classes to test using only original tests.
     */
    protected static final Set<Class<? extends BaseSubtitleTest>> TEST_ORIGINAL;

    /**
     * Parameterized test collection.
     */
    private static final Collection<Object[]> TEST_COLLECTION;

    static {
        TEST_ORIGINAL = new HashSet<Class<? extends BaseSubtitleTest>>();
        TEST_ORIGINAL.add(RemoveEmptyTest.class);
        TEST_ORIGINAL.add(DoNotFixTest.class);
        TEST_ORIGINAL.add(ManuelFixTest.class);

        TEST_COLLECTION = new ArrayList<Object[]>();
    }

    /**
     * Class constructor.
     * 
     * @param expected expected value
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     * @param classContainer {@link ClassContainer}
     */
    public BaseTest(String expected, String actual, List<TestProperty> properties, ClassContainer classContainer) {
        this.expected = expected.replace("\\n", "\n");
        this.actual = actual.replace("\\n", "\n");
        this.properties = properties;
        this.classContainer = classContainer;
    }

    /**
     * Generate fixed line.
     * 
     * @param line line to fix
     * @return fixed line
     */
    public String fix(String line) {
        SubtitleObject so = new SubtitleObject(line);
        so.fix();
        return so.getText();
    }

    /**
     * Abstract method used to exclude tests.
     */
    protected abstract void subExclude();

    /**
     * Exclude tests.
     */
    @Before
    public void exclude() {
        assumeFalse(!getClass().equals(OriginalTest.class) && TEST_ORIGINAL.contains(classContainer.getClazz()));
        subExclude();
    }

    /**
     * @return {@link #TEST_COLLECTION}.
     */
    @Parameters(name = "{3} -> {1}")
    public static Collection<Object[]> testCollection() {
        if (TEST_COLLECTION.isEmpty()) {
            TEST_COLLECTION.addAll(FixOneLineTest.testCollection());
            TEST_COLLECTION.addAll(FixThreeLinesTest.testCollection());
            TEST_COLLECTION.addAll(FixTwoLinesTest.testCollection());
            TEST_COLLECTION.addAll(FixUnbalancedDashesTest.testCollection());
            TEST_COLLECTION.addAll(RemoveCharacterNameTest.testCollection());
            TEST_COLLECTION.addAll(RemoveEmptyTest.testCollection());
            TEST_COLLECTION.addAll(ChangeLsToIsTest.testCollection());
            TEST_COLLECTION.addAll(FixAbbreviationsTest.testCollection());
            TEST_COLLECTION.addAll(FixAcronymTest.testCollection());
            TEST_COLLECTION.addAll(FixAmpersandTest.testCollection());
            TEST_COLLECTION.addAll(FixCommonErrorsTest.testCollection());
            TEST_COLLECTION.addAll(FixContractionsTest.testCollection());
            TEST_COLLECTION.addAll(FixDashesTest.testCollection());
            TEST_COLLECTION.addAll(FixEllipsesTest.testCollection());
            TEST_COLLECTION.addAll(FixEndingTest.testCollection());
            TEST_COLLECTION.addAll(FixHeightTest.testCollection());
            TEST_COLLECTION.addAll(FixLetterSTest.testCollection());
            TEST_COLLECTION.addAll(FixMisplacedQuotesTest.testCollection());
            TEST_COLLECTION.addAll(FixMultilineQuotesTest.testCollection());
            TEST_COLLECTION.addAll(FixNonTraditionalStringsTest.testCollection());
            TEST_COLLECTION.addAll(FixNumbersTest.testCollection());
            TEST_COLLECTION.addAll(FixSingleDoubleQuotesTest.testCollection());
            TEST_COLLECTION.addAll(FixSpacingTest.testCollection());
            TEST_COLLECTION.addAll(FixTimeTest.testCollection());
            TEST_COLLECTION.addAll(FixToUppercaseTest.testCollection());
            TEST_COLLECTION.addAll(FixWebsitesTest.testCollection());
            TEST_COLLECTION.addAll(CorrectSentencesTest.testCollection());
            TEST_COLLECTION.addAll(DoNotFixTest.testCollection());
            TEST_COLLECTION.addAll(ManuelFixTest.testCollection());
        }
        return TEST_COLLECTION;
    }

}