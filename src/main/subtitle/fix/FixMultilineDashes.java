package main.subtitle.fix;

import main.subtitle.Fixer;
import main.util.StringUtil;

/**
 * Fix dashes that span multiple lines.
 * 
 * @author budi
 */
public class FixMultilineDashes implements Fixer { // TODO: fix this

    /**
     * {@link FixMultilineDashes} fix.
     * 
     * @param line first line
     * @param fix second line
     * @return fixed line
     */
    public static String fix(String line, String fix) {
        if (line.startsWith("--")) {
            return fix;
        }
        if (line.startsWith("-") && !fix.startsWith("-") && !StringUtil.startsLowerCase(fix)) {
            StringBuilder builder = new StringBuilder();
            builder.append("- ");
            builder.append(fix);
            return builder.toString();
        }

        return fix;
    }

}
