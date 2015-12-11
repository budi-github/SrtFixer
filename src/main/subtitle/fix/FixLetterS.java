package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;

/**
 * Fix letter "s".
 * 
 * @author budi
 */
public class FixLetterS implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixLetterS} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains("s")) {
            return line;
        }

        String temp = line;

        line = fixLetterS(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix letter "s".
     * 
     * This fixes all instances of "s" or "'s".
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixLetterS(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            addSpace = true;
            if (current.equals("s") || current.equals("'s")) {
                addSpace = false;
            }

            if (i > 0 && addSpace) {
                builder.append(' ');
            }
            builder.append(current);
        }

        return builder.toString();
    }

}
