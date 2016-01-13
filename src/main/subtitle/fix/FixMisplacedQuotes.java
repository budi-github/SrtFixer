package main.subtitle.fix;

import java.util.Set;
import java.util.TreeSet;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.string.StringUtil;

/**
 * Fix misplaced quotes.
 * 
 * @author budi
 */
public class FixMisplacedQuotes implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 3;

    /**
     * Strings that should have quote appended to.
     */
    private static final Set<String> APPEND_QUOTE;

    /**
     * Strings that should have quote prepended to.
     */
    private static final Set<String> PREPEND_QUOTE;

    static {
        APPEND_QUOTE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        APPEND_QUOTE.add("an");
        APPEND_QUOTE.add("d");
        APPEND_QUOTE.add("ol");
        APPEND_QUOTE.add("po");
        APPEND_QUOTE.add("sho");
        APPEND_QUOTE.add("y");

        PREPEND_QUOTE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        PREPEND_QUOTE.add("bout");
        PREPEND_QUOTE.add("cause");
        PREPEND_QUOTE.add("clock");
        PREPEND_QUOTE.add("em");
        PREPEND_QUOTE.add("im");
        PREPEND_QUOTE.add("Nam");
    }

    /**
     * {@link FixMisplacedQuotes} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains("'")) {
            return line;
        }

        String temp = line;

        line = fixMisplacedQuotes(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix misplaced quotes.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixMisplacedQuotes(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();

        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrev != null) {
                if (prev.equals("'")) {
                    if (prevPrev.equalsIgnoreCase("o") || prevPrev.equalsIgnoreCase("d")
                            || prevPrev.equalsIgnoreCase("y")) {
                        builder.deleteCharAt(builder.length() - 2);
                        addSpace = false;
                    } else if ((APPEND_QUOTE.contains(prevPrev)
                            || (prevPrev.endsWith("in") && !prevPrev.equalsIgnoreCase("in")))
                            || prevPrev.endsWith("s") && !PREPEND_QUOTE.contains(current)) { // TODO: something's wrong
                        builder.deleteCharAt(builder.length() - 2);
                    } else if (PREPEND_QUOTE.contains(current) || StringUtil.isNumeric(current.replace("s", ""))) {
                        addSpace = false;
                    }
                }
            }

            if (i > 0 && addSpace) {
                builder.append(' ');
            }
            builder.append(current);
        }

        return builder.toString();
    }

}
