package main.regex;

import main.util.StringUtil;

/**
 * Regex utilities.
 * 
 * @author budi
 */
public class RegexUtil {

    /**
     * Match using RegexEnum.getPattern.
     * 
     * @param re regex enum
     * @param s string to check
     * @return True if String matches regex, otherwise false.
     */
    public static boolean matches(RegexEnum re, String s) {
        return re.getPattern().matcher(s).matches();
    }

    /**
     * Replace all using RegexEnum.
     * 
     * @param re regex enum
     * @param s string to fix
     * @param replace string to replace
     * @return fixed string
     */
    public static String patternReplaceAll(RegexEnum re, String s, String replace) {
        if ((re.equals(RegexEnum.PUNCTUATION) || re.equals(RegexEnum.STRIP_PUNCTUATION)
                || re.equals(RegexEnum.STRIP_PUNCTUATION_ABBREVIATIONS))) {
            if (!StringUtil.containsPunctuation(s)) {
                return s;
            }
        }

        return re.getPattern().matcher(s).replaceAll(replace);
    }

    /**
     * Split line by regex.
     * 
     * @param re regex enum
     * @param s string to split
     * @return list of strings split by regex
     */
    public static String[] split(RegexEnum re, String s) {
        return re.getPattern().split(s);
    }

}