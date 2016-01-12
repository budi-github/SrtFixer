package main.subtitle.fix;

import java.util.Map.Entry;
import java.util.TreeMap;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;

/**
 * Fix all common errors.
 * 
 * This class will eventually be deleted once all bugs are found.
 * 
 * @author budi
 */
public class FixCommonErrors implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Map of all special beginning exceptions.
     * 
     * Lines that begin with a key in this map should be fixed to its value.
     */
    private static final TreeMap<String, String> SPECIAL_BEGINNING_EXCEPTIONS;

    /**
     * Map of all special exceptions.
     * 
     * Lines that contain a key in this map should be fixed to its value.
     */
    private static final TreeMap<String, String> SPECIAL_EXCEPTIONS;

    static {
        SPECIAL_BEGINNING_EXCEPTIONS = new TreeMap<String, String>();
        SPECIAL_BEGINNING_EXCEPTIONS.put("- \"... ", "- \"..."); // [- "... ] -> [- "...]
        SPECIAL_BEGINNING_EXCEPTIONS.put("-... ", "- ..."); // [-... ] -> [- ...]
        SPECIAL_BEGINNING_EXCEPTIONS.put("-...", "- ..."); // [-...] -> [- ...]
        SPECIAL_BEGINNING_EXCEPTIONS.put("- ... ", "- ..."); // [- ... ] -> [- ...]
        SPECIAL_BEGINNING_EXCEPTIONS.put("\" '", "\"'"); // [" '] -> ["']

        SPECIAL_EXCEPTIONS = new TreeMap<String, String>();
        SPECIAL_EXCEPTIONS.put(".-", ". - "); // [.-] -> [. - ]
        SPECIAL_EXCEPTIONS.put("?-", "? - "); // [?-] -> [? - ]
        SPECIAL_EXCEPTIONS.put("!-", "! - "); // [!-] -> [! - ]
    }

    /**
     * {@link FixCommonErrors} fix.
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

        line = fixCommonErrors(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix all common errors.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixCommonErrors(String line) {
        Entry<String, String> map = SPECIAL_BEGINNING_EXCEPTIONS.floorEntry(line);
        if (map != null) {
            if (line.startsWith(map.getKey())) {
                line = line.replaceFirst(map.getKey(), map.getValue());
            }
        }

        for (Entry<String, String> entry : SPECIAL_EXCEPTIONS.entrySet()) {
            line = line.replace(entry.getKey(), entry.getValue());
        }

        return line;
    }

}
