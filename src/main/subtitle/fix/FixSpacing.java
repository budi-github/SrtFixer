package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.string.StringUtil;

/**
 * Fix any remaining line spacing.
 * 
 * @author budi
 */
public class FixSpacing implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 2;

    /**
     * {@link FixSpacing} fix.
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

        line = fixSpacing(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix spacing.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixSpacing(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.getSplitMap().getSplit(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean foundDoubleQuote = false, foundSingleQuote = false, consecutiveDash, addSpace;
        for (int i = 0; i < split.length; ++i) {
            if (split[i].isEmpty()) {
                continue;
            }
            prevPrev = prev;
            prev = current;
            current = split[i];
            consecutiveDash = current.equals("-") && prev != null && prev.equals("-");
            addSpace = true;
            if (consecutiveDash) {
                addSpace = false;
            } else if (current.equals("'")) {
                if (foundSingleQuote) {
                    addSpace = false;
                }
                foundSingleQuote = !foundSingleQuote;
            } else if (current.equals("\"")) {
                if (foundDoubleQuote) {
                    addSpace = false;
                }
                foundDoubleQuote = !foundDoubleQuote;
            } else if (current.equals("$") || current.equals("@") || current.equals("#") || current.equals("&")) {
                // pass
            } else if (StringUtil.isPunctuation(current)) {
                addSpace = false;
            } else if (prev != null) {
                if (foundSingleQuote && prev.equals("'")) {
                    addSpace = false;
                } else if (foundDoubleQuote && prev.equals("\"")) {
                    addSpace = false;
                } else if (prev.equals("/") || prev.equals("$") || prev.equals("@")) {
                    addSpace = false;
                } else if (prev.equals("-")) {
                    if (prevPrev != null && prevPrev.equals("-")) {
                        // pass
                    } else if (builder.length() == 1 && builder.charAt(0) == '-') {
                        // pass
                    } else {
                        addSpace = false;
                    }
                }
            }

            if (builder.length() >= 1 && builder.charAt(0) != '-' && builder.charAt(0) != '#'
                    && (!StringUtil.isPunctuation(current) || current.equals("@") || current.equals("#"))) {
                String temp = builder.toString();
                if (StringUtil.isPunctuation(temp)) {
                    addSpace = false;
                }
            }

            if (i > 0 && addSpace) {
                builder.append(' ');
            }
            builder.append(current);
        }

        String result = builder.toString();
        if (result.startsWith("... ")) {
            builder.deleteCharAt(3);
        } else if (result.startsWith("- ... ")) {
            builder.deleteCharAt(5);
        }
        return builder.toString();
    }

}
