package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Prepare line for tokenization.
 * <p>
 * Allows for splitting line by both spaces and punctuation, removing any excess
 * spaces as necessary.
 * <p>
 * For example:
 * <p>
 * The string <code>"  A.  K .A."</code> will be fixed to the string
 * <code>"A . K . A."</code>. Spliting this string by a space, returns the list
 * <code>["A", ".", "K", ".", "A", "."]</code>.
 * 
 * @author budi
 */
public class PrepareLine implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link PrepareLine} fix.
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

        if (line.contains("  ")) {
            line = removeExcessSpaces(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        line = addSpaceBetweenPunctuation(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Remove excess (2+ consecutive) spaces from line.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String removeExcessSpaces(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (!current.isEmpty()) {
                if (i > 0) {
                    builder.append(' ');
                }
                builder.append(current);
            }
        }

        return builder.toString();
    }

    /**
     * Add spaces in between all punctuation.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String addSpaceBetweenPunctuation(String line, SubtitleObject so) {
        return RegexUtil.patternReplaceAll(RegexEnum.SPACES_BETWEEN_PUNCTUATION, line, " ");
    }

}
