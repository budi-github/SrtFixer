package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.string.StringUtil;

/**
 * Fix time.
 * 
 * @author budi
 */
public class FixTime implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 3;

    /**
     * {@link FixTime} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains(":")) {
            return line;
        }

        String temp = line;

        line = fixTime(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix time.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixTime(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        int currentNumber, prevPrevNumber;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrev != null && StringUtil.isNumeric(prevPrev)) {
                if (prev.equals(":")) {
                    String noPunct = current;
                    if (!StringUtil.isNumeric(noPunct)) {
                        noPunct = so.patternReplaceAll(RegexEnum.ALPHABET, split, i, "");
                    }
                    if (StringUtil.isNumeric(noPunct)) {
                        prevPrevNumber = Integer.parseInt(prevPrev);
                        currentNumber = Integer.parseInt(noPunct);
                        if (prevPrevNumber >= 0 && prevPrevNumber <= 23 && currentNumber >= 0 && currentNumber <= 59) {
                            builder.deleteCharAt(builder.length() - 2);
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
