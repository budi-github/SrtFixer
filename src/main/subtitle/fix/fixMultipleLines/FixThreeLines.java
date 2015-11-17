package main.subtitle.fix.fixMultipleLines;

import main.regex.RegexEnum;
import main.regex.RegexUtil;
import main.subtitle.Fixer;

/**
 * Fix subtitles that span three lines.
 * 
 * @author budi
 */
public class FixThreeLines implements Fixer {

    /**
     * {@link FixThreeLines} fix.
     * 
     * @param text text to fix
     * @return fixed text
     */
    public static String fix(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder builder = new StringBuilder();
        String[] split = RegexUtil.split(RegexEnum.NEWLINE, text); // TODO: cache this when fixed
        if (split.length <= 2) {
            return text;
        } else {
            for (String line : split) {
                if (line.startsWith("-")) {
                    builder.append(line);
                    builder.append('\n');
                } else {
                    if (builder.length() > 0 && builder.charAt(builder.length() - 1) == '\n') {
                        builder.deleteCharAt(builder.length() - 1);
                        builder.append(' ');
                    }
                    builder.append(line);
                    builder.append('\n');
                }

                // TODO: optimize after adding more tests
            }
        }

        if (builder.charAt(builder.length() - 1) == '\n') {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

}
