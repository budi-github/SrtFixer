package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;

/**
 * Fix websites.
 * 
 * @author budi
 */
public class FixWebsites implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 2;

    /**
     * {@link FixWebsites} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains(".")) {
            return line;
        }

        String temp = line;

        if (line.contains("www .")) {
            line = fixWWW(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        if (line.toLowerCase().contains(". com")) {
            line = fixDotCom(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        return line;
    }

    /**
     * Fix all "www.".
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixWWW(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean addSpace;

        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrev != null && prevPrev.equalsIgnoreCase("www")) {
                if (prev.equals(".")) {
                    builder.deleteCharAt(builder.length() - 2);
                    addSpace = false;
                }
            }

            if (i > 0 && addSpace) {
                builder.append(' ');
            }
            builder.append(current);
        }

        return builder.toString();

    }

    /**
     * Fix all ".com".
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixDotCom(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prev = null, current = null;
        boolean addSpace;

        for (int i = 0; i < split.length; ++i) {
            prev = current;
            current = split[i];
            addSpace = true;

            if (prev != null && prev.equals(".")) {
                if (current.equalsIgnoreCase("com")) {
                    current = "com";
                    builder.deleteCharAt(builder.length() - 2);
                    addSpace = false;
                }
            }

            if (i > 0 && addSpace) {
                builder.append(' ');
            }
            builder.append(current);
        }

        return builder.toString();
    }

}
