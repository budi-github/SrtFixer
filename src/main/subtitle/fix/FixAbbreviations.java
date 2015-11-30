package main.subtitle.fix;

import java.util.Set;
import java.util.TreeSet;

import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.string.StringUtil;

/**
 * Fix abbreviations.
 * 
 * @author budi
 */
public class FixAbbreviations implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Set of all abbreviations.
     */
    private static final Set<String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.add("dr");
        ABBREVIATIONS.add("mr");
        ABBREVIATIONS.add("mrs");
        ABBREVIATIONS.add("ms");
    }

    /**
     * {@link FixAbbreviations} fix.
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

        line = fixAbbreviations(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix all abbreviations.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixAbbreviations(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String current = null;
        boolean addPeriod;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            addPeriod = false;
            if (ABBREVIATIONS.contains(current)) {
                current = StringUtil.capitalize(current);
                if (i == split.length - 1 || !split[i + 1].equals(".")) {
                    addPeriod = true;
                }
            }
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(current);
            if (addPeriod) {
                builder.append('.');
            }
        }

        return builder.toString();
    }

}
