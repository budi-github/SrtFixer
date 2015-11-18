package test;

import test.base.tests.QuoteTest;

/**
 * Enum of all test properties.
 * 
 * <li>{@link #ALL}</li>
 * <li>{@link #EXCLUDE_QUOTE_TEST}</li>
 * 
 * @author budi
 */
public enum TestProperty {
    /**
     * Test all tests in suite.
     */
    ALL,

    /**
     * Exclude {@link QuoteTest}.
     */
    EXCLUDE_QUOTE_TEST
}
