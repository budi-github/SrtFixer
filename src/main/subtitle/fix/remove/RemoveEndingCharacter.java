package main.subtitle.fix.remove;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;

/**
 * Remove starting character through ending character from string.
 * 
 * @author budi
 */
public class RemoveEndingCharacter implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link RemoveEndingCharacter} fix.
     * 
     * @param line line to fix
     * @param start Starting character. All characters including and beyond the
     *            index of this character will be deleted until ending character
     *            is found.
     * @param end Ending character. When starting character is found, all
     *            characters up to and including the index of this character
     *            will be deleted.
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, char start, char end, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || line.indexOf(start) == -1) {
            return line;
        }

        String temp = line;

        line = removeEndingCharacter(line, start, end);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Remove starting character through ending character from string.
     * 
     * @param line line to fix
     * @param start start character
     * @param end end character
     * @return fixed line
     */
    private static String removeEndingCharacter(String line, char start, char end) {
        boolean omit = false;
        StringBuilder builder = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == start) {
                omit = true;
            }
            if (!omit) {
                builder.append(c);
            }
            if (c == end) {
                omit = false;
            }
        }

        return builder.toString();
    }

}
