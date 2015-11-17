package main.subtitle;

import main.util.StringUtil;

/**
 * Fixer interface. Must be implemented for all "fix" methods.
 * 
 * @author budi
 */
public interface Fixer {

    /**
     * Exclude specific tests if line is null or empty, or if when the list is
     * split by a specific delimiter, the resulting list's length is less than
     * the minimum number of tokens - 1.
     * 
     * @param line line to check
     * @param so subtitle object
     * @param minTokens minimum number of tokens
     * @param delimiter Character to split line by. Resulting split list will
     *            determine number of tokens.
     * @return True if test should be excluded, false otherwise.
     */
    public static boolean exclude(String line, SubtitleObject so, int minTokens, char delimiter) {
        // TODO: cache StringUtil.count(line, delimiter) with SubtitleObject
        return line == null || line.isEmpty() || (minTokens != 1 && StringUtil.count(line, delimiter) < minTokens - 1);
    }

}
