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
    public static final int MAX_LINE_LENGTH = 60;

    /**
     * Balance weight of line.
     * 
     * Only for cases where a subtitle text spans two lines, if either line's
     * length is less than the other line's length multiplied this number, the
     * program will attempt to "balance" both lines.
     */
    public static final double BALANCE_WEIGHT = 0.70;

}
