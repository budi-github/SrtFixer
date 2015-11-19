package test;

import main.srtFixer.config.SrtFixerConfig;
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
     * Test only if {@link SrtFixerConfig#isToggleCorrectCapitalization()} is
     * true.
     */
    CAPITALIZE_ONLY,

    /**
     * Exclude if {@link SrtFixerConfig#isToggleCorrectCapitalization()} is
     * true.
     */
    EXCLUDE_CAPITALIZE,

    /**
     * Exclude {@link QuoteTest}.
     */
    EXCLUDE_QUOTE_TEST;
}
