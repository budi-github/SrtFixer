package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;

/**
 * Fix words that should be all uppercase.
 * 
 * @author budi
 */
public class FixToUppercase implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixToUppercase} fix.
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

        line = fixToUppercase(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix words that should be all uppercase.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixToUppercase(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i].toLowerCase();
            switch (current) {
            case "ok":
            case "i":
            case "id":
            case "usa":
                split[i] = split[i].toUpperCase();
                break;
            }
        }

        return String.join(" ", split);
    }

}
