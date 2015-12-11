package main.subtitle.fix;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;
import main.util.string.StringUtil;
import main.util.stringBuilder.StringBuilderUtil;

/**
 * Fix acronyms.
 * 
 * @author budi
 */
public class FixAcronym implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 4;

    /**
     * Set of acronyms that should not be capitalized.
     */
    private static final Set<Character[]> DO_NOT_CAPITALIZE;

    /**
     * Set of acronym strings that should not be capitalized.
     */
    private static final Set<String> DO_NOT_CAPITALIZE_STRINGS;

    static {
        DO_NOT_CAPITALIZE = new HashSet<Character[]>();
        DO_NOT_CAPITALIZE.add(new Character[] { 'e', 'g' });
        DO_NOT_CAPITALIZE.add(new Character[] { 'i', 'e' });

        DO_NOT_CAPITALIZE_STRINGS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Character[] array : DO_NOT_CAPITALIZE) {
            StringBuilder builder = new StringBuilder();
            for (Character abbrev : array) {
                builder.append(abbrev);
                builder.append('.');
            }
            DO_NOT_CAPITALIZE_STRINGS.add(builder.toString());
        }
    }

    /**
     * {@link FixAcronym} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || StringUtil.count(line, '.') <= 1) {
            return line;
        }

        String temp = line;

        line = fixAcronyms(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        line = fixUncapitalizedAcronyms(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix acronyms.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixAcronyms(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrevPrev = null, prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrevPrev = prevPrev;
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrevPrev != null && prevPrevPrev.length() == 1 && Character.isLetter(prevPrevPrev.charAt(0))) {
                if (prevPrev.equals(".")) {
                    if (prev.length() == 1 && Character.isLetter(prev.charAt(0))) {
                        if (current.equals(".")) {
                            StringBuilderUtil.deleteSpaceAt(builder, builder.length() - 2);
                            StringBuilderUtil.deleteOnlyIfSpaceAt(builder, builder.length() - 3);
                            StringBuilderUtil.capitalizeAt(builder, builder.length() - 1);
                            StringBuilderUtil.capitalizeAt(builder, builder.length() - 3);
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

    /**
     * Fix all uncapitalized acronyms.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixUncapitalizedAcronyms(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);

        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (DO_NOT_CAPITALIZE_STRINGS.contains(current)) {
                split[i] = split[i].toLowerCase();
            }
        }

        return String.join(" ", split);
    }

}
