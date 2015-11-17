package main.subtitle.fix;

import main.regex.RegexEnum;
import main.regex.RegexUtil;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;

/**
 * Fix consecutive single quotes to double quotes.
 * 
 * @author budi
 */
public class FixSingleDoubleQuotes implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixSingleDoubleQuotes} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || (!line.contains("''") && !line.contains("' '"))) {
            return line;
        }

        String temp = line;

        line = fixSingleDoubleQuotes(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix consecutive single quotes to double quotes.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixSingleDoubleQuotes(String line) {
        return RegexUtil.patternReplaceAll(RegexEnum.QUOTES, line, "\"");
    }

}
