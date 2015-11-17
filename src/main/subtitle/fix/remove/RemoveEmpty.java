package main.subtitle.fix.remove;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Remove empty lines or lines that contain only punctuation.
 * 
 * @author budi
 */
public class RemoveEmpty implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link RemoveEmpty} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ')) {
            return line;
        }

        String temp = line;

        line = removeEmpty(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Remove empty lines.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String removeEmpty(String line) {
        if (RegexUtil.matches(RegexEnum.MATCH_PUNCTUATION_AND_SPACE, line)) {
            return "";
        }

        return line;
    }

}
