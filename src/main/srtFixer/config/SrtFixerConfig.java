package main.srtFixer.config;

/**
 * SrtFixer configurations.
 * 
 * @author budi
 */
public class SrtFixerConfig {

    /**
     * Maximum (single) line length.
     */
    private static final int MAX_LINE_LENGTH = 50;

    /**
     * Minimum subtitle duration.
     */
    private static final int MIN_DURATION = 400; // TODO: this number should be based on the number of letters in the subtitle

    /**
     * Balance weight of line.
     * <p>
     * Only for cases where a subtitle text spans two lines, if either line's
     * length is less than the other line's length multiplied this number, the
     * program will attempt to "balance" both lines.
     */
    private static final double BALANCE_WEIGHT = 0.70;

    /**
     * If true, attempt to correct capitalization.
     * <p>
     * Currently experimental. Use at your own risk!
     */
    private static final boolean TOGGLE_CORRECT_CAPITALIZATION = false;

    /**
     * @return {@link #MAX_LINE_LENGTH}
     */
    public static int getMaxLineLength() {
        return MAX_LINE_LENGTH;
    }

    /**
     * @return {@link #MIN_DURATION}
     */
    public static int getMinDuration() {
        return MIN_DURATION;
    }

    /**
     * @return {@link #BALANCE_WEIGHT}
     */
    public static double getBalanceWeight() {
        return BALANCE_WEIGHT;
    }

    /**
     * @return {@link #TOGGLE_CORRECT_CAPITALIZATION}
     */
    public static boolean isToggleCorrectCapitalization() {
        return TOGGLE_CORRECT_CAPITALIZATION;
    }

}
