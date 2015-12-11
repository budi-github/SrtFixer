package main.subtitle.fix;

import java.util.HashSet;
import java.util.Set;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.regex.RegexEnum;

/**
 * Change l's to I's.
 * 
 * @author budi
 */
public class ChangeLsToIs implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * Set of words to be corrected.
     */
    private static final Set<String> WORDS;

    static {
        WORDS = new HashSet<String>();
        WORDS.add("l");
        WORDS.add("ld");
        WORDS.add("lf");
        WORDS.add("lll");
        WORDS.add("lm");
        WORDS.add("ln");
        WORDS.add("lt");
        WORDS.add("lts");
        WORDS.add("ls");
        WORDS.add("lve");
    }

    /**
     * {@link ChangeLsToIs} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains("l")) {
            return line;
        }

        String temp = line;

        line = changeLsToIs(line, so);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Change all l's to I's.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    private static String changeLsToIs(String line, SubtitleObject so) {
        String[] split = so.split(RegexEnum.SPACE, line);
        String current;
        for (int i = 0; i < split.length; ++i) {
            current = split[i];
            if (current.contains("l") && WORDS.contains(current)) {
                if (current.equals("lll")) {
                    split[i] = current.replace("l'll", "I'll");
                    split[i] = current.replace("lll", "Ill");
                } else {
                    split[i] = current.replace("l", "I");
                }
            }
        }

        return String.join(" ", split); // TODO: cache this as well
    }

}
