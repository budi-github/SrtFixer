package test.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.base.tests.DashesTest;
import test.base.tests.EllipsesTest;
import test.base.tests.OriginalTest;
import test.base.tests.PunctuationTest;
import test.base.tests.QuoteTest;
import test.base.tests.WhitespaceTest;

/**
 * Run this module to run all tests.
 * 
 * @author budi
 */
@RunWith(Suite.class)
@SuiteClasses({ DashesTest.class, EllipsesTest.class, OriginalTest.class, PunctuationTest.class, QuoteTest.class,
        WhitespaceTest.class })
public class RunAllTests {

}