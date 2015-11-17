package main.subtitle.fix;

import main.regex.RegexEnum;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;

/**
 * Fix numbers.
 * 
 * @author budi
 */
public class FixNumbers implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 3;

    /**
     * {@link FixNumbers} fix.
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

        line = fixNumbers(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix numbers.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixNumbers(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrev != null) {
                if (prev.equals(".") || prev.equals(",")) {
                    String currentStripped = current;
                    if (currentStripped.endsWith("s")) {
                        currentStripped = current.substring(0, current.length() - 1);
                    }
                    if (StringUtil.isNumeric(currentStripped)) {
                        String prevPrevStripped = prevPrev;
                        if (prevPrev.startsWith("$") || prevPrev.startsWith("£")) {
                            prevPrevStripped = prevPrev.substring(1);
                        }
                        if (StringUtil.isNumeric(prevPrevStripped)) {
                            if (prev.equals(".")) {
                                builder.deleteCharAt(builder.length() - 2);
                                addSpace = false;
                            } else if (prev.equals(",") && current.length() == 3) {
                                builder.deleteCharAt(builder.length() - 2);
                                addSpace = false;
                            }
                        }
                    }
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
