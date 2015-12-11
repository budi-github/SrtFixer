package main.subtitle.fix.remove;

import main.subtitle.Fixer;
import main.subtitle.subtitleObject.SubtitleObject;
import main.util.string.StringUtil;

/**
 * Remove character name from the beginning of the line.
 * 
 * @author budi
 */
public class RemoveCharacterName implements Fixer {

    /**
     * Minimum number of tokens.
     */
    private static final int MIN_TOKENS = 1;

    /**
     * {@link RemoveCharacterName} fix.
     * 
     * @param line line to fix
     * @param so {@link SubtitleObject}
     * @return fixed line
     */
    public static String fix(String line, SubtitleObject so) {
        if (Fixer.exclude(line, so, MIN_TOKENS, ' ') || !line.contains(":")) {
            return line;
        }

        String temp = line;

        line = removeCharacterName(line);
        temp = SubtitleObject.clearMapIfChanged(so, temp, line);

        return line;
    }

    /**
     * Remove character name from the beginning of the line.
     * 
     * @param line line to fix
     * @return fixed line
     */
    private static String removeCharacterName(String line) {
        int index = line.indexOf(':');
        String temp = line.substring(0, index);
        if (!StringUtil.isNumeric(temp.replace("\"", "").trim()) && temp.equals(temp.toUpperCase())) {
            StringBuilder builder = new StringBuilder();
            if (temp.startsWith("-")) {
                temp = temp.substring(1, temp.length()).trim();
                builder.append("- ");
            } else if (temp.startsWith("...")) {
                temp = temp.substring(3, temp.length()).trim();
                builder.append("...");
            } else if (temp.startsWith(". . .")) {
                temp = temp.substring(5, temp.length()).trim();
                builder.append(". . .");
            }
            if (!StringUtil.isNumeric(temp.replace("\"", ""))) {
                if (!line.substring(0, index).contains(".")) {
                    builder.append(line.substring(index + 1, line.length()).trim());
                    return builder.toString();
                }
            }
        }

        return line;
    }

}
