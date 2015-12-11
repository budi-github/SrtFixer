package main.subtitle.fix.fixMultipleLines;

import java.util.ArrayList;
import java.util.List;

import main.srtFixer.config.SrtFixerConfig;
import main.subtitle.Fixer;
import main.subtitle.util.SubtitleUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;
import main.util.string.StringUtil;

/**
 * Fix subtitles that span one line.
 * 
 * @author budi
 */
public class FixOneLine implements Fixer {

    /**
     * {@link FixOneLine} fix.
     * 
     * @param line line to fix
     * @return fixed line
     */
    public static String fix(String line) {
        if (line == null || line.isEmpty() || StringUtil.count(line, '\n') != 0
                || line.length() < SrtFixerConfig.getMaxLineLength()) {
            return line;
        }

        List<StringBuilder> list = new ArrayList<StringBuilder>();

        if (!line.contains("...")) {
            list.addAll(SubtitleUtil.balanceString(line, "\\."));
        }
        list.addAll(SubtitleUtil.balanceString(line, ","));
        list.addAll(SubtitleUtil.balanceString(line, "\\?"));
        list.addAll(SubtitleUtil.balanceString(line, "!"));

        StringBuilder balanced = SubtitleUtil.mostBalanced(list);
        if (balanced != null) {
            String balancedString = balanced.toString();
            String[] split = RegexUtil.split(RegexEnum.NEWLINE, balancedString);
            if (Math.abs(split[0].length() - split[1].length()) / (float) balancedString.length() < SrtFixerConfig
                    .getBalanceWeight()) {
                return FixTwoLines.fix(balancedString);
            }
        }
        if (line.contains(" ")) {
            return SubtitleUtil.splitClosestToMiddle(line, ' ').toString();
        }

        return line;
    }

}
