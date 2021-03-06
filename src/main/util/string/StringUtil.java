package main.util.string;

import java.util.Set;
import java.util.TreeSet;

import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * {@link String} utilities.
 * 
 * @author budi
 */
public class StringUtil {

    /**
     * List of month names.
     */
    private static final Set<String> MONTHS;

    static {
        MONTHS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        MONTHS.add("january");
        MONTHS.add("february");
        MONTHS.add("march");
        MONTHS.add("april");
        MONTHS.add("may");
        MONTHS.add("june");
        MONTHS.add("july");
        MONTHS.add("august");
        MONTHS.add("september");
        MONTHS.add("october");
        MONTHS.add("november");
        MONTHS.add("december");
    }

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
     * @return Number of occurances of the character in the string.
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
     * Count the number of occurances of uppercase letters in string.
     * 
     * @param s string
     * @return number of occurances of uppercase letters in the string
     */
    public static int countUppercase(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
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
     * Determines if entire string is punctuation.
     * 
     * @param s string to check
     * @return True if entire string is punctuation, otherwise false.
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
     * Determines if the character is punctuation.
     * 
     * @param c character to check.
     * @return True if the character is punctuation, otherwise false.
     */
    public static boolean isPunctuation(char c) {
        return !Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isWhitespace(c);
    }

    /**
     * Determines if the string contains punctuation.
     * 
     * @param s string to check
     * @return True if the string contains punctuation, otherwise false.
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
     * Determines if the string contains both letters and numbers.
     * 
     * @param s string to check
     * @return True if string contains both letters and numbers, otherwise
     *         false.
     */
    public static boolean containsLettersAndNumbers(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        boolean hasLetter = false, hasNumber = false;
        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }

        return hasLetter && hasNumber;
    }

    /**
     * Determines if the string is numeric.
     * 
     * @param s string to check
     * @return True if the string is numeric, otherwise false.
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

    /**
     * Determines if string is a month.
     * 
     * @param string string to check
     * @return True if string is month, otherwise false.
     */
    public static boolean isMonth(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        return MONTHS.contains(string);
    }

}
