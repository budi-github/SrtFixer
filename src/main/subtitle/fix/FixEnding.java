package main.subtitle.fix;

import main.regex.RegexEnum;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;

/**
 * Fix end of line spacing.
 * 
 * @author budi
 */
public class FixEnding implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixEnding} fix.
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

        line = fixEnding(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix random spacing at the end of lines.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixEnding(String line, SubtitleObject so) {
        while (true) {
            String[] split = so.split(RegexEnum.SPACE, line);
            int length = split.length;
            if (length > 1) {
                String last = split[length - 1];
                if (!last.equals("#") && StringUtil.isPunctuation(last) && !line.equals("- ...")) {
                    line = StringUtil.replaceLast(line, " ", "");
                    so.clearSplitMap();
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return line;
    }

}
