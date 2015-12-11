package main.subtitle.fix;

import java.util.Map;
import java.util.TreeMap;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;

/**
 * Fix all common spelling errors.
 * 
 * @author budi
 */
public class FixSpelling implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Map of incorrect words to correct words.
     */
    private static final Map<String, String> SPELLCHECKER;

    static {
        SPELLCHECKER = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

        SPELLCHECKER.put("vvww", "www");
        SPELLCHECKER.put("wvvw", "www");
        SPELLCHECKER.put("wwvv", "www");
        SPELLCHECKER.put("vvvvw", "www");
        SPELLCHECKER.put("vvwvv", "www");
        SPELLCHECKER.put("vvvvvv", "www");
    }

    /**
     * {@link FixSpelling} fix.
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

        line = fixSpelling(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix all common spelling errors.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixSpelling(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (SPELLCHECKER.containsKey(current)) {
                split[i] = SPELLCHECKER.get(current);
            }
        }

        return String.join(" ", split);
    }

}
