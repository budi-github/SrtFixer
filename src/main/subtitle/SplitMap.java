package main.subtitle;

import java.util.HashMap;
import java.util.Map;

import main.regex.RegexEnum;
import main.regex.RegexUtil;

/**
 * This class functions as a caching mechanism for String.split.
 * 
 * @author budi
 */
public class SplitMap {

    /**
     * Split map containing {@link RegexEnum} as keys and split lists as values.
     */
    private Map<RegexEnum, String[]> splitMap;

    /**
     * Split map containing {@link RegexEnum} as keys and inner maps containing
     * cached string values.
     */
    private Map<RegexEnum, Map<String, Map<Integer, String>>> patternMap;

    /**
     * Class constructor.
     */
    public SplitMap() {
        this.splitMap = new HashMap<RegexEnum, String[]>();
        this.patternMap = new HashMap<RegexEnum, Map<String, Map<Integer, String>>>();
    }

    /**
     * Split line by regex.
     * 
     * @param re {@link RegexEnum}
     * @param line line to split
     * @return list of strings split by regex
     */
    public String[] getSplit(RegexEnum re, String line) {
        if (splitMap.containsKey(re)) {
            return splitMap.get(re);
        } else {
            String[] split = RegexUtil.split(re, line);
            splitMap.put(re, split);
            return split;
        }
    }

    /**
     * Replace all using pattern.
     * 
     * @param re {@link RegexEnum}
     * @param split list to split
     * @param index index of word in split
     * @param replace string to replace
     * @return fixed string
     */
    public String getPatternReplaceAll(RegexEnum re, String[] split, int index, String replace) {
        boolean addToPatternMap = false, addToReplaceMap = false, addToIndexMap = false;
        if (patternMap.containsKey(re)) {
            Map<String, Map<Integer, String>> replaceMap = patternMap.get(re);
            if (replaceMap.containsKey(replace)) {
                Map<Integer, String> indexMap = replaceMap.get(replace);
                if (indexMap.containsKey(index)) {
                    return indexMap.get(index);
                } else {
                    addToIndexMap = true;
                }
            } else {
                addToReplaceMap = true;
                addToIndexMap = true;
            }
        } else {
            addToPatternMap = true;
            addToReplaceMap = true;
            addToIndexMap = true;
        }

        if (addToPatternMap) {
            patternMap.put(re, new HashMap<String, Map<Integer, String>>());
        }
        if (addToReplaceMap) {
            Map<String, Map<Integer, String>> replaceMap = patternMap.get(re);
            replaceMap.put(replace, new HashMap<Integer, String>());
        }
        if (addToIndexMap) {
            Map<String, Map<Integer, String>> replaceMap = patternMap.get(re);
            Map<Integer, String> indexMap = replaceMap.get(replace);
            String result = RegexUtil.patternReplaceAll(re, split[index], replace);
            indexMap.put(index, result);
            return result;
        }

        return null;
    }

    /**
     * Clear both {@link #splitMap} and {@link #patternMap}.
     */
    public void clear() {
        splitMap.clear();
        patternMap.clear();
    }

}
