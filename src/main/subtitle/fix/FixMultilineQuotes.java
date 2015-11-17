package main.subtitle.fix;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Fix quotes that span multiple lines.
 * 
 * @author budi
 */
public class FixMultilineQuotes implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link FixMultilineQuotes} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || StringUtil.count(line, '\"') <= 1) {
            return line;
        }

        String temp = line;

        line = fixMultilineQuotes(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix quotes that span multiple lines.
     * 
     * @param text text to fix
     * @param so {@link SubtitleObject}
     * @return fixed text
     */
    public static String fixMultilineQuotes(String text, SubtitleObject so) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String[] split = RegexUtil.split(RegexEnum.NEWLINE, text);
        if (split.length == 2) {
            StringBuilder builder = new StringBuilder();
            String line1 = split[0];
            String line2 = split[1];
            if (StringUtil.count(line1, '\"') == 1 && StringUtil.count(line2, '\"') == 1) {
                int index = line2.indexOf("\"");
                if (index > 0 && line2.charAt(index - 1) == ' ') {
                    char[] array = line2.toCharArray();
                    char temp = array[index];
                    array[index] = array[index - 1];
                    array[index - 1] = temp;
                    String swappedLine = new String(array);
                    builder.append(line1);
                    builder.append('\n');
                    builder.append(swappedLine);
                    return builder.toString();
                }
            }
        }

        return text;
    }

}
