package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;

/**
 * Fix dashes.
 * 
 * @author budi
 */
public class FixDashes implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixDashes} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.startsWith("-")) {
            return line;
        }

        String temp = line;

        line = fixDashes(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix dash spacing.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String fixDashes(String line) {
        // TODO: can be solved with regex
        StringBuilder builder = new StringBuilder();
        builder.append(line);
        if (builder.charAt(0) == '-') {
            int index = 0;
            char c = builder.charAt(index);
            while ((c == '-' || c == ' ') && builder.length() > index + 1) {
                ++index;
                c = builder.charAt(index);
            }
            builder.delete(0, index);
        }

        builder.insert(0, "- ");
        return builder.toString();
    }

}
