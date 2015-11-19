package main.subtitle.fix.fixMultipleLines;

import main.srtFixer.config.SrtFixerConfig;
import main.subtitle.Fixer;
import main.subtitle.util.SubtitleUtil;
import main.util.StringUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * Fix subtitles that span two lines.
 * 
 * @author budi
 */
public class FixTwoLines implements Fixer {

    // TODO: check this (All wrong)

    /**
     * {@link FixTwoLines} fix.
     * 
     * @param text text to fix
     * @return fixed text
     */
    public static String fix(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String[] split = RegexUtil.split(RegexEnum.NEWLINE, text); // TODO: cache this when fixed
        if (split.length == 2) {
            String first = split[0];
            String second = split[1];
            if (!first.startsWith("-") && !second.startsWith("-")) {
                if (first.length() > SrtFixerConfig.getMaxLineLength()
                        || second.length() > SrtFixerConfig.getMaxLineLength()) {
                    return SubtitleUtil.splitClosestToMiddle(text, ' ').toString();
                }
            }
            if (StringUtil.count(first, '\"') == 1 && StringUtil.count(second, '\"') == 3) {
                StringBuilder builder = new StringBuilder();
                builder.append(first.trim());
                builder.append(' ');
                int index = second.indexOf('"');
                builder.append(second.substring(0, index).trim());
                builder.append('\"');
                builder.append('\n');
                builder.append('\"');
                builder.append(second.substring(index + 2).trim());
                return builder.toString();
            } else if (StringUtil.count(first, '\"') == 3 && StringUtil.count(second, '\"') == 1) {
                StringBuilder builder = new StringBuilder();
                int index = first.lastIndexOf('"');
                builder.append(first.substring(0, index + 1));
                builder.append('\n');
                builder.append(first.substring(index + 2));
                builder.append(' ');
                builder.append(second.trim());
                return builder.toString();
            } else if (!first.startsWith("\"") && !second.endsWith("\"") && StringUtil.count(first, '\"') == 1
                    && StringUtil.count(second, '\"') == 1) {
                StringBuilder builder = new StringBuilder();
                int firstIndex = first.indexOf('"');
                int secondIndex = second.indexOf('"');

                if (Math.abs((first.length() + secondIndex) - (second.length() - secondIndex)) < Math
                        .abs(firstIndex - (first.length() - firstIndex + second.length()))) {
                    builder.append(first.trim());
                    builder.append(' ');
                    builder.append(second.substring(0, secondIndex + 1));
                    builder.append('\n');
                    builder.append(second.substring(secondIndex + 2)); // TODO: check all of these
                    return builder.toString();
                } else {
                    builder.append(first.substring(0, firstIndex - 1));
                    builder.append('\n');
                    builder.append(first.substring(firstIndex).trim());
                    builder.append(' ');
                    builder.append(second);
                    return builder.toString();
                }
            }
        }
        return text;
    }

}
