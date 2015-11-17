package main.subtitle.fix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import main.regex.RegexEnum;
import main.subtitle.Fixer;
import main.subtitle.SubtitleObject;
import main.util.StringUtil;

/**
 * Fix contractions.
 * 
 * @author budi
 */
public class FixContractions implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Common contraction endings.
     */
    private static final Set<String> CONTRACTIONS_ENDINGS;

    /**
     * Unique contractions.
     */
    private static final Map<String, String> UNIQUE;

    /**
     * Contractions exceptions.
     */
    private static final Map<String, String> EXCEPTIONS;

    /**
     * Set of contractions.
     */
    private static final Set<String> CONTRACTIONS;

    /**
     * Map of contraction keys to index of "'" values.
     */
    private static final Map<String, Integer> CONTRACTIONS_ERROR_MAP;

    static {
        // TODO: passes if contraction is captialized, fails for "D'Artagnan"
        //CONTRACTIONS_ENDINGS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        CONTRACTIONS_ENDINGS = new HashSet<String>();
        CONTRACTIONS_ENDINGS.add("am");
        CONTRACTIONS_ENDINGS.add("all");
        CONTRACTIONS_ENDINGS.add("d");
        CONTRACTIONS_ENDINGS.add("ll");
        CONTRACTIONS_ENDINGS.add("m");
        CONTRACTIONS_ENDINGS.add("re");
        CONTRACTIONS_ENDINGS.add("t");
        CONTRACTIONS_ENDINGS.add("s");
        CONTRACTIONS_ENDINGS.add("ve");

        UNIQUE = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        UNIQUE.put("Savai", "i");
        UNIQUE.put("Shi", "a");
        UNIQUE.put("c", "mon");
        UNIQUE.put("s", "il");

        EXCEPTIONS = new HashMap<String, String>();
        EXCEPTIONS.put("van't", "van 't");

        CONTRACTIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        CONTRACTIONS.add("can't");
        CONTRACTIONS.add("couldn't");
        CONTRACTIONS.add("don't");
        CONTRACTIONS.add("haven't");
        CONTRACTIONS.add("he's");
        CONTRACTIONS.add("how'd");
        CONTRACTIONS.add("how'll");
        CONTRACTIONS.add("how's");
        CONTRACTIONS.add("i'm");
        CONTRACTIONS.add("i've");
        CONTRACTIONS.add("it'll");
        CONTRACTIONS.add("it'd");
        CONTRACTIONS.add("ma'am");
        CONTRACTIONS.add("s'il");
        CONTRACTIONS.add("she's");
        CONTRACTIONS.add("shouldn't");
        CONTRACTIONS.add("that'd");
        CONTRACTIONS.add("that'll");
        CONTRACTIONS.add("that's");
        CONTRACTIONS.add("there's");
        CONTRACTIONS.add("they'd");
        CONTRACTIONS.add("they'll");
        CONTRACTIONS.add("they're");
        CONTRACTIONS.add("they've");
        CONTRACTIONS.add("we've");
        CONTRACTIONS.add("what'd");
        CONTRACTIONS.add("what'll");
        CONTRACTIONS.add("what're");
        CONTRACTIONS.add("what's");
        CONTRACTIONS.add("when'd");
        CONTRACTIONS.add("when'll");
        CONTRACTIONS.add("when's");
        CONTRACTIONS.add("where'd");
        CONTRACTIONS.add("where'll");
        CONTRACTIONS.add("where's");
        CONTRACTIONS.add("who'd");
        CONTRACTIONS.add("who'll");
        CONTRACTIONS.add("who's");
        CONTRACTIONS.add("why'd");
        CONTRACTIONS.add("why'll");
        CONTRACTIONS.add("why's");
        CONTRACTIONS.add("won't");
        CONTRACTIONS.add("y'all");
        CONTRACTIONS.add("you'd");
        CONTRACTIONS.add("you're");

        CONTRACTIONS_ERROR_MAP = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        for (String contraction : CONTRACTIONS) {
            CONTRACTIONS_ERROR_MAP.put(contraction.replace("\'", ""), contraction.indexOf("'"));
        }
    }

    /**
     * {@link FixContractions} fix.
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

        if (line.contains("'")) {
            line = fixContractions(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);

            line = fixUnique(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);

            line = fixExceptions(line, so);
            temp = SubtitleObject.clearMapIfChanged(so, temp, line);
        }

        line = fixSpelling(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Fix contractions.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixContractions(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prev != null && prev.equals("'")) {
                if (CONTRACTIONS_ENDINGS.contains(current)) {
                    if (current.equalsIgnoreCase("all") && prevPrev != null && prevPrev.endsWith("n")) {
                        // pass
                    } else if (prevPrev != null && StringUtil.isPunctuation(prevPrev)) {
                        addSpace = false;
                    } else {
                        builder.deleteCharAt(builder.length() - 2);
                        addSpace = false;
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
     * Fix unique contractions.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixUnique(String line, SubtitleObject so) {
        StringBuilder builder = new StringBuilder();
        String[] split = so.split(RegexEnum.SPACE, line);
        String prevPrev = null, prev = null, current = null;
        boolean addSpace;
        for (int i = 0; i < split.length; ++i) {
            prevPrev = prev;
            prev = current;
            current = split[i];
            addSpace = true;
            if (prevPrev != null) {
                if (prev.equals("'")) {
                    if (UNIQUE.containsKey(prevPrev) && UNIQUE.get(prevPrev).equalsIgnoreCase(current)) {
                        builder.deleteCharAt(builder.length() - 2);
                        addSpace = false;
                        if (current.equals("I")) {
                            current = current.toLowerCase();
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
     * Fix exceptions.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixExceptions(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (EXCEPTIONS.containsKey(current)) {
                split[i] = EXCEPTIONS.get(current);
            }
        }

        return String.join(" ", split);
    }

    /**
     * Fix common contraction spelling errors.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String fixSpelling(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (CONTRACTIONS_ERROR_MAP.containsKey(current)) {
                int contractionIndex = CONTRACTIONS_ERROR_MAP.get(current);
                int index = 0;
                char c = current.charAt(0);
                while (current.length() > index + 1) {
                    if (Character.toLowerCase(current.charAt(index)) == Character.toLowerCase(c)) {
                        break;
                    }
                    ++index;
                }
                StringBuilder builder = new StringBuilder();
                builder.append(current.substring(0, contractionIndex + index));
                builder.append('\'');
                builder.append(current.substring(contractionIndex + index));
                split[i] = builder.toString();
            }
        }

        return String.join(" ", split);
    }

}
