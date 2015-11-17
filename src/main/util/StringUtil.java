package main.util;

import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * {@link String} utilities.
 * 
 * @author budi
 */
public class StringUtil {

    /**
     * Generate a repeated string a set number of times.
     * 
     * @param s string to repeat
     * @param n number to times
     * @return repeated string n times
     */
    public static String repeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }

    /**
     * Prepend string to beginning of line and append string to end of line.
     * 
     * @param line original line
     * @param prepend string to prepend
     * @param append string to append
     * @return fixed line
     */
    public static String prependAndAppend(String line, String prepend, String append) {
        StringBuilder builder = new StringBuilder();
        builder.append(prepend);
        builder.append(line);
        builder.append(append);
        return builder.toString();
    }

    /**
     * Prepend string to line.
     * 
     * @param line original line
     * @param prepend string to prepend
     * @return fixed line
     */
    public static String prepend(String line, String prepend) {
        if (prepend.equals("- ")) {
            StringBuilder builder = new StringBuilder();
            String[] split = RegexUtil.split(RegexEnum.NEWLINE, line);
            for (int i = 0; i < split.length; ++i) {
                if (i > 0) {
                    builder.append('\n');
                }
                builder.append(prepend);
                builder.append(split[i]);
            }
            return builder.toString();
        } else {
            return prependAndAppend(line, prepend, "");
        }
    }

    /**
     * Append string to line.
     * 
     * @param line original line
     * @param append string to append
     * @return fixed string
     */
    public static String append(String line, String append) {
        return prependAndAppend(line, "", append);
    }

    /**
     * Prepend and append spaces to line.
     * 
     * @param line original line
     * @param prepend number of spaces to prepend
     * @param append number of spaces to append
     * @return fixed line
     */
    public static String addSpaces(String line, int prepend, int append) {
        return prependAndAppend(line, repeat(" ", prepend), repeat(" ", append));
    }

    /**
     * Capitalize string.
     * 
     * @param string string to capitalize
     * @return capitalized string
     */
    public static String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        int index = 0, length = string.length();
        while (index < length - 1) {
            if (Character.isAlphabetic(string.charAt(index))) {
                break;
            }
            ++index;
        }
        if (index == length) {
            return string;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(string.substring(0, index));
            builder.append(Character.toUpperCase(string.charAt(index)));
            builder.append(string.substring(index + 1));
            return builder.toString();
        }
    }

    /**
     * Count the number of occurances of a character in a string.
     * 
     * @param s string
     * @param c character to look for
     * @return number of occurances of c in s
     */
    public static int count(String s, char c) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (char c1 : s.toCharArray()) {
            if (c == c1) {
                ++count;
            }
        }
        return count;
    }

    /**
     * Replace last.
     * 
     * @param s string to fix
     * @param replace string to replace
     * @param replacement string to replace with
     * @return fixed string
     */
    public static String replaceLast(String s, String replace, String replacement) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        int index = s.lastIndexOf(replace);
        if (index > -1) {
            return s.substring(0, index) + replacement + s.substring(index + replace.length(), s.length());
        } else {
            return s;
        }
    }

    /**
     * Determines if string starts with uppercase letter.
     * 
     * @param s string to check
     * @return True if s starts with uppercase letter, otherwise false.
     */
    public static boolean startsLowerCase(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return Character.isLowerCase(s.codePointAt(0));
    }

    /**
     * Determines if string starts with lowercase letter.
     * 
     * @param s string to check
     * @return True if s starts with lowercase letter, otherwise false.
     */
    public static boolean startsUpperCase(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return Character.isUpperCase(s.codePointAt(0));
    }

    /**
     * Determines if string is punctuation.
     * 
     * @param s string to check
     * @return True if s is punctuation, otherwise false.
     */
    public static boolean isPunctuation(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!isPunctuation(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if char is punctuation.
     * 
     * @param c char to check.
     * @return True if c is punctuation, otherwise false.
     */
    public static boolean isPunctuation(char c) {
        return !Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isWhitespace(c);
    }

    /**
     * Determines if string contains punctuation.
     * 
     * @param s string to check
     * @return True if s contains punctuation, otherwise false.
     */
    public static boolean containsPunctuation(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if string is numeric.
     * 
     * @param s string to check
     * @return True if s is numeric, otherwise false.
     */
    public static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
