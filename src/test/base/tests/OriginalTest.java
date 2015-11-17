package test.base.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import test.ClassContainer;
import test.Parallelized;
import test.TestProperty;
import test.base.BaseTest;
import test.subtitle.BaseSubtitleTest;

/**
 * Original test.
 * 
 * @author budi
 */
@RunWith(Parallelized.class)
public class OriginalTest extends BaseTest {

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
    public OriginalTest(String expected, String actual, List<TestProperty> properties, ClassContainer classContainer) {
        super(expected, actual, properties, classContainer);
    }

    /**
     * Test original.
     */
    @Test
    public void testOriginal() {
        assertEquals(expected, fix(actual));
    }

    @Override
    protected void subExclude() {
        assumeFalse(EXCLUDE.contains(classContainer.getClazz()));
    }

}
