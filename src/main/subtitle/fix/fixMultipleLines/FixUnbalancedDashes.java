package main.subtitle.fix.fixMultipleLines;

import main.subtitle.Fixer;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;
import main.util.string.StringUtil;

/**
 * Fix unbalanced dashes.
 * 
 * @author budi
 */
public class FixUnbalancedDashes implements Fixer {

    /**
     * {@link FixUnbalancedDashes} fix.
     * 
     * @param text text to fix
     * @return fixed text
     */
    public static String fix(String text) {
        if (text == null || text.isEmpty() || StringUtil.count(text, '-') <= 1) {
            return text;
        }

        String[] split = RegexUtil.split(RegexEnum.NEWLINE, text); // TODO: cache this when fixed
        if (split.length == 2) {
            String first = split[0];
            String second = split[1];
            if (second.contains("-")) {
                int index = determineShouldSplit(second);
                if (index > 0) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(first);
                    builder.append(' ');
                    builder.append(second.substring(0, index).trim());
                    builder.append('\n');
                    builder.append(second.substring(index).trim());
                    return builder.toString();
                }
            } else if (first.contains("-")) {
                int index = determineShouldSplit(first);
                if (index > 0) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(first.substring(0, index).trim());
                    builder.append('\n');
                    builder.append(first.substring(index).trim());
                    builder.append(' ');
                    builder.append(second);
                    return builder.toString();
                }
            }
        } else if (split.length == 1) {
            int index = determineShouldSplit(text);
            if (index > 0) {
                StringBuilder builder = new StringBuilder();
                builder.append(text.substring(0, index).trim());
                builder.append('\n');
                builder.append(text.substring(index).trim());
                return builder.toString();
            }
        }

        return text;
    }

    /**
     * Determine where line should be split.
     * 
     * @param text text to fix
     * @return index of where to split
     */
    private static int determineShouldSplit(String text) {
        int index;
        while (true) {
            index = text.lastIndexOf('-');
            if (index > 0) {
                text = text.substring(0, index).trim();
                char c = text.charAt(text.length() - 1);
                if (c != '-' && StringUtil.isPunctuation(c)) {
                    return index;
                }
            } else {
                break;
            }
        }
        return index;
    }

}
