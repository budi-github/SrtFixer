package main.subtitle.fix;

import main.regex.RegexEnum;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;

/**
 * Fix height.
 * 
 * @author budi
 */
public class FixHeight implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 4;

    /**
     * {@link FixHeight} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !(line.contains("'") && line.contains("\""))) {
            return line;
        }

        String temp = line;

        line = fixHeight(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix height.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixHeight(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        int length = split.length;
        String prevPrevPrev = null, prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < length; ++i) {
            prevPrevPrev = prevPrev;
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;

            if (prevPrevPrev != null && StringUtil.isNumeric(prevPrevPrev)) {
                if (prevPrev.equals("'")) {
                    if (StringUtil.isNumeric(prev)) {
                        if (current.equals("\"")) {
                            builder.deleteCharAt(builder.length() - prev.length() - 1);
                            builder.deleteCharAt(builder.length() - prev.length() - 2);
                            addSpace = false;
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
