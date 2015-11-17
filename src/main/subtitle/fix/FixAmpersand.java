package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;

/**
 * Fix ampersand (&).
 * 
 * @author budi
 */
public class FixAmpersand implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixAmpersand} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains("&")) {
            return line;
        }

        String temp = line;

        line = fixAmpersandAcronyms(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix ampersand in acronyms.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixAmpersandAcronyms(String line) {
        int index = line.indexOf(" & ");
        if (index != -1 && line.length() > index + 3) {
            if (Character.isUpperCase(line.charAt(index - 1)) && Character.isUpperCase(line.charAt(index + 3))) {
                line = line.replace(" & ", "&"); // TODO: can be regex
            }
        }

        return line;
    }

}
