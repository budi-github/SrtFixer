package main.subtitle.fix;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;

/**
 * Fix all non traditional strings.
 * 
 * @author budi
 */
public class FixNonTraditionalStrings implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Map of non-traditional string keys to traditional string values.
     */
    private static final Map<String, String> MAP;

    /**
     * Compiled regex to determine if string contains non-traditional strings.
     */
    private static final Pattern PATTERN;

    static {
        MAP = new HashMap<String, String>(); // TODO: use TreeMap here?
        MAP.put("’", "'");

        StringBuilder regex = new StringBuilder();
        regex.append("[^");
        for (String key : MAP.keySet()) {
            regex.append(key);
        }
        regex.append("]+");
        PATTERN = Pattern.compile(regex.toString());
    }

    /**
     * {@link FixNonTraditionalStrings} fix.
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

        if (!PATTERN.matcher(line).matches()) {
            line = fixNonTraditionalStrings(line);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        return line;
    }

    /**
     * Fix all non traditional strings.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixNonTraditionalStrings(String line) {
        for (Entry<String, String> entry : MAP.entrySet()) {
            line = line.replace(entry.getKey(), entry.getValue());
        }

        return line;
    }

}
