package main.subtitle;

import java.io.IOException;

import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Contains time used for standard .srt files.
 * <p>
 * Format:
 * <p>
 * <code>hr:min:sec,ms</code>
 * <p>
 * Examples:
 * <p>
 * <code>01:23:45,678</code><br>
 * <code>00:00:00,000</code><br>
 * <code>99:59:59,999</code>
 * 
 * @author budi
 */
public class TimeHolder {

    /**
     * Hours to milliseconds. Used in {@link #calcTotalTimeMs()}.
     */
    private static final int HOURS_TO_MILLISECONDS = 3600000;

    /**
     * Minutes to milliseconds. Used in {@link #calcTotalTimeMs()}.
     */
    private static final int MINUTES_TO_MILLISECONDS = 60000;

    /**
     * Seconds to milliseconds. Used in {@link #calcTotalTimeMs()}.
     */
    private static final int SECONDS_TO_MILLISECONDS = 1000;

    /**
     * Hours.
     */
    private int hour;

    /**
     * Minutes.
     */
    private int minute;

    /**
     * Seconds.
     */
    private int second;

    /**
     * Milliseconds.
     */
    private int millisecond;

    /**
     * Class constructor.
     * 
     * @param string time string to parse
     * @throws IOException Only thrown if string to parse is malformed.
     */
    public TimeHolder(String string) throws IOException {
        if (string == null || string.isEmpty()) {
            throw new IOException("String is null or empty");
        }

        String[] array = RegexUtil.split(RegexEnum.COMMA, string);
        if (array.length != 2) {
            throw new IOException(String.format("String \"%s\" is malformed.", string));
        }
        String[] array2 = RegexUtil.split(RegexEnum.COLON, array[0]);
        if (array2.length != 3) {
            throw new IOException(String.format("String \"%s\" is malformed.", string));
        }

        this.hour = Integer.parseInt(array2[0].trim());
        this.minute = Integer.parseInt(array2[1].trim());
        this.second = Integer.parseInt(array2[2].trim());
        this.millisecond = Integer.parseInt(array[1].trim());
        fixTime();
    }

    /**
     * Add time (in seconds) to {@link TimeHolder}.
     * 
     * @param time time to add (in seconds)
     */
    public void addTime(double time) {
        if (time != 0.0) {
            second += (int) time;

            double m = time - (int) time;
            m *= 1000;
            int n = (int) m;
            millisecond += n;

            fixTime();
        }
    }

    /**
     * Add time (in milliseconds) to {@link TimeHolder}.
     * 
     * @param ms time to add (in ms)
     */
    public void setTime(int ms) {
        millisecond = ms;
        hour = 0;
        minute = 0;
        second = 0;
        fixTime();
    }

    /**
     * Fix any malformed time.
     */
    private void fixTime() {
        while (millisecond >= 1000) {
            millisecond -= 1000;
            ++second;
        }
        while (second >= 60) {
            second -= 60;
            ++minute;
        }
        while (minute >= 60) {
            minute -= 60;
            ++hour;
        }
        while (millisecond < 0) {
            millisecond += 1000;
            --second;
        }
        while (second < 0) {
            second += 60;
            --minute;
        }
        while (minute < 0) {
            minute += 60;
            --hour;
        }
    }

    /**
     * Calculate total time in milliseconds.
     * 
     * @return total time in milliseconds
     */
    public int calcTotalTimeMs() {
        return (hour * HOURS_TO_MILLISECONDS) + (minute * MINUTES_TO_MILLISECONDS) + (second * SECONDS_TO_MILLISECONDS)
                + millisecond;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d,%03d", hour, minute, second, millisecond);
    }

}
