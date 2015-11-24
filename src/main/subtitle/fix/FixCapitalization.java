package main.subtitle.fix;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.data.DataPath;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;
import main.util.Util;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * @author budi
 */
public class FixCapitalization implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * A string preceding any string in this set should be capitalized.
     */
    private static final Set<String> PRECEDING_SHOULD_CAPITALIZE;

    /**
     * Set of all proper nouns (should not be lowercase).
     */
    private static final Set<String> PROPER_NOUNS;

    /**
     * Set of all potential proper nouns (should not be set to lowercase).
     */
    private static final Set<String> POTENTIAL_PROPER_NOUNS;

    /**
     * Set of all words that must be all uppercase (should not be set to
     * lowercase).
     */
    private static final Set<String> ALL_UPPERCASE;

    /**
     * Set of all potential words that must be all uppercase (should not be set
     * to lowercase).
     */
    private static final Set<String> POTENTIAL_ALL_UPPERCASE;

    /**
     * Set of all titles.
     */
    private static final Set<String> TITLES;

    /**
     * Set of all potential titles.
     */
    private static final Set<String> POTENTIAL_TITLES;

    static {
        PRECEDING_SHOULD_CAPITALIZE = new HashSet<String>();
        PRECEDING_SHOULD_CAPITALIZE.add(".");
        PRECEDING_SHOULD_CAPITALIZE.add("?");
        PRECEDING_SHOULD_CAPITALIZE.add("!");

        PROPER_NOUNS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(PROPER_NOUNS, DataPath.getProperNounsPath());

        POTENTIAL_PROPER_NOUNS = new HashSet<String>();
        initSet(POTENTIAL_PROPER_NOUNS, DataPath.getPotentialProperNounsPath());

        ALL_UPPERCASE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(ALL_UPPERCASE, DataPath.getAllUppercasePath());

        POTENTIAL_ALL_UPPERCASE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(POTENTIAL_ALL_UPPERCASE, DataPath.getPotentialAllUppercasePath());

        TITLES = new HashSet<String>();
        initSet(TITLES, DataPath.getTitlesPath());

        POTENTIAL_TITLES = new HashSet<String>();
        initSet(POTENTIAL_TITLES, DataPath.getPotentialTitlesPath());
    }

    /**
     * {@link FixCapitalization} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ')) {
            return line;
        }

        String originalLine = line;

        boolean shouldCapitalizeFirstWord = line.equals(StringUtil.capitalize(line));

        String temp = line;

        line = fixProperNouns(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        line = fixAllUppercase(line, originalLine, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        line = fixPrecedingShouldCapitalize(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        if (shouldCapitalizeFirstWord) {
            line = StringUtil.capitalize(line);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        if (line.contains("\"")) {
            line = fixQuoteCapitalization(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        line = fixTitle(line, originalLine, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix all proper nouns.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixProperNouns(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current = null;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (!StringUtil.isPunctuation(current) && !StringUtil.containsLettersAndNumbers(current)) {
                if (StringUtil.startsUpperCase(current) && current.length() > 1
                        && StringUtil.countUppercase(current) == 1) {
                    if (!PROPER_NOUNS.contains(current) && !POTENTIAL_PROPER_NOUNS.contains(current)) {
                        split[i] = current.toLowerCase();
                    }
                } else {
                    if (PROPER_NOUNS.contains(current)) {
                        split[i] = StringUtil.capitalize(current);
                    }
                }
            }
        }

        return String.join(" ", split);
    }

    /**
     * Fix all words that should be all uppercase and lowercases all words that
     * are currently all uppercase (but shouldn't be).
     * 
     * @param line line to fix
     * @param originalLine original line
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixAllUppercase(String line, String originalLine, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current = null;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (ALL_UPPERCASE.contains(current)) {
                if (!current.equals(current.toUpperCase())) {
                    split[i] = split[i].toUpperCase();
                }
            } else if (POTENTIAL_ALL_UPPERCASE.contains(current)) {
                if (originalLine.indexOf(current.toUpperCase()) == line.indexOf(current)) {
                    split[i] = split[i].toUpperCase();
                }
            } else if (current.length() > 1 && current.equals(current.toUpperCase())
                    && !StringUtil.containsLettersAndNumbers(current)) {
                split[i] = split[i].toLowerCase();
            }
        }

        return String.join(" ", split);
    }

    /**
     * Fix all words that should be capitalized upon preceding a certain string.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixPrecedingShouldCapitalize(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean shouldCapitalize = false;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            if (prev != null && PRECEDING_SHOULD_CAPITALIZE.contains(prev)) {
                shouldCapitalize = true;
                if (prev.equals(".") && prevPrev != null && prevPrev.equals(".")) { // ellipses
                    shouldCapitalize = false;
                } else if (!current.equals("\"") && !RegexUtil.matches(RegexEnum.MATCH_DIGIT_AND_S, current)) {
                    if (!StringUtil.startsUpperCase(current)) {
                        split[i] = StringUtil.capitalize(current);
                    }
                    shouldCapitalize = false;
                }
            } else if (shouldCapitalize) {
                if (!StringUtil.isPunctuation(current)) {
                    split[i] = StringUtil.capitalize(current);
                    shouldCapitalize = false;
                }
            }
        }

        return String.join(" ", split);
    }

    /**
     * Capitalize first word at the beginning of a quote.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixQuoteCapitalization(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String prev = null, current = null;
        boolean firstQuote = true, shouldCapitalize = false;
        for (int i = 0; i < split.length; ++i) {
            prev = current;
            current = split[i];
            if (prev != null && prev.equals(",")) {
                if (current.equals("\"")) {
                    if (firstQuote) {
                        shouldCapitalize = true;
                        firstQuote = false;
                    } else {
                        firstQuote = true;
                    }
                }
            } else if (current.equals("\"")) {
                firstQuote = !firstQuote;
            } else if (shouldCapitalize && !StringUtil.isPunctuation(current)) {
                split[i] = StringUtil.capitalize(current);
                shouldCapitalize = false;
            }
        }

        return String.join(" ", split);
    }

    /**
     * Fix all words in titles that should be capitalized.
     * 
     * @param line line to fix
     * @param originalLine original line
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fixTitle(String line, String originalLine, SubtitleObject so) {
        for (String title : TITLES) {
            if (line.toLowerCase().contains(title.toLowerCase()) && originalLine.contains(title)) {
                String temp = line;
                int index = temp.toLowerCase().indexOf(title.toLowerCase());
                line = temp.substring(0, index);
                line += title;
                line += temp.substring(index + title.length());
            }
        }
        for (String title : POTENTIAL_TITLES) {
            int index = originalLine.indexOf(title);
            if (index != -1) {
                String temp = line;
                line = temp.substring(0, index);
                line += title;
                line += temp.substring(index + title.length());
            }
        }
        return line;
    }

    /**
     * Initialize set with data from path.
     * 
     * @param set set to initialize
     * @param path path to file containing data
     */
    private static void initSet(Set<String> set, String path) {
        List<String> list = null;
        try {
            list = Util.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            set.addAll(list);
        }
    }

}
