package main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.regex.RegexEnum;
import main.regex.RegexUtil;

/**
 * Subtitle utilities.
 * 
 * @author budi
 */
public class SubtitleUtil {

    /**
     * Get most balanced line out of list of {@link StringBuilder}.
     * 
     * @param list list of {@link StringBuilder} containing lines.
     * @return most balanced line.
     */
    public static StringBuilder mostBalanced(List<StringBuilder> list) {
        StringBuilder balanced = null;
        int bestDifference = Integer.MAX_VALUE;
        for (StringBuilder builder : list) {
            String[] split = RegexUtil.split(RegexEnum.NEWLINE, builder.toString());
            if (split.length == 2) {
                int difference = Math.abs(split[0].length() - split[1].length());
                if (difference < bestDifference) {
                    balanced = builder;
                    bestDifference = difference;
                }
            }
        }
        return balanced;
    }

    /**
     * Determines if sentence contains at least one word in the set.
     * 
     * @param sentence sentence to check
     * @param words set of words to check for
     * @return True if sentence contains at least one word in the set, otherwise
     *         false.
     */
    public static boolean sentenceContainsWords(String sentence, Set<String> words) {
        sentence = sentence.toLowerCase();
        for (String word : words) {
            if (sentence.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Split sentence into two most balanced lines.
     * 
     * @param line line to balance
     * @param delimiter split by delimiter if possible
     * @return most balanced @{link StringBuilder}
     */
    public static StringBuilder splitClosestToMiddle(String line, char delimiter) {
        if (line == null || line.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        int indexClosestToMiddle = -1;
        int index = 0;
        int midIndex = line.length() / 2;
        line = line.replace("\n", " ").trim();
        for (char c : line.toCharArray()) {
            ++index;
            if (c == delimiter) {
                if (indexClosestToMiddle == -1
                        || (Math.abs(index - midIndex) < Math.abs(indexClosestToMiddle - midIndex))) {
                    indexClosestToMiddle = index;
                }
            }
        }
        builder.append(line.substring(0, indexClosestToMiddle).trim());
        builder.append('\n');
        builder.append(line.substring(indexClosestToMiddle, line.length()).trim());
        return builder;
    }

    /**
     * Get list of most balanced strings split by regex.
     * 
     * @param line line to balance
     * @param regex regex to split by
     * @return list of {@link StringBuilder} of most balanced strings
     */
    public static List<StringBuilder> balanceString(String line, String regex) {
        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder builder;

        char delimiter = regex.replace("\\", "").charAt(0);

        String[] splitArray = line.split(regex);
        if (splitArray.length == 2) {
            builder = new StringBuilder();
            builder.append(splitArray[0].trim());
            builder.append(delimiter);
            builder.append('\n');
            builder.append(splitArray[1].trim());
            if (line.endsWith(String.valueOf(delimiter))) {
                builder.append(delimiter);
            }
            list.add(builder);
        } else if (splitArray.length == 3) {
            builder = new StringBuilder();
            builder.append(splitArray[0].trim());
            builder.append(delimiter);
            builder.append(' ');
            builder.append(splitArray[1].trim());
            builder.append(delimiter);
            builder.append('\n');
            builder.append(splitArray[2].trim());

            if (line.endsWith(String.valueOf(delimiter))) {
                builder.append(delimiter);
            }
            list.add(builder);

            builder = new StringBuilder();
            builder.append(splitArray[0].trim());
            builder.append(delimiter);
            builder.append('\n');
            builder.append(splitArray[1].trim());
            builder.append(delimiter);
            builder.append(' ');
            builder.append(splitArray[2].trim());
            if (line.endsWith(String.valueOf(delimiter))) {
                builder.append(delimiter);
            }
            list.add(builder);
        } else if (splitArray.length > 3) {
            list.add(splitClosestToMiddle(line, delimiter));
        }

        return list;
    }

    /**
     * Determines if two strings are approximately equal.
     * 
     * @param result first string to check
     * @param text second string to check
     * @return True if strings are approximately equal, otherwise false.
     */
    public static boolean isApproximatelyEqual(String result, String text) {
        boolean dotSpace = false, spaceDot = false;
        if (result.length() == text.length()) {
            result = result.replace("\n", " ");
            text = text.replace("\n", " ");
            char r, t;
            for (int i = 0; i < result.length(); ++i) {
                r = result.charAt(i);
                t = text.charAt(i);
                if (r != t) {
                    if (dotSpace) {
                        if (r == ' ' && t == '.') {
                            dotSpace = false;
                            continue;
                        } else {
                            return false;
                        }
                    } else if (spaceDot) {
                        if (r == '.' && t == ' ') {
                            spaceDot = false;
                            continue;
                        } else {
                            return false;
                        }
                    } else if (r == '.' && t == ' ') {
                        dotSpace = true;
                    } else if (r == ' ' && t == '.') {
                        spaceDot = true;
                    } else {
                        return false;
                    }
                } else if (dotSpace || spaceDot) {
                    return false;
                }
            }
        } else {
            return false;
        }

        if (dotSpace || spaceDot) {
            return false;
        }
        return true;
    }

}
