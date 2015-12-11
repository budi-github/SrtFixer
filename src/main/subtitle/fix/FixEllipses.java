package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Fix ellipses (...).
 * 
 * @author budi
 */
public class FixEllipses implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixEllipses} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains("..")) {
            return line;
        }

        String temp = line;

        line = fixEllipses(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix 2+ "."'s to "...".
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixEllipses(String line) {
        return RegexUtil.patternReplaceAll(RegexEnum.ELLIPSES, line, "...");
    }

}
