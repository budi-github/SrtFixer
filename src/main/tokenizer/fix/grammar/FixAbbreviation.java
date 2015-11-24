package main.tokenizer.fix.grammar;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix abbreviation.
 * 
 * @author budi
 */
public class FixAbbreviation {

    /**
     * Set of all abbreviations.
     */
    private static final Set<String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.add("dr");
        ABBREVIATIONS.add("mr");
        ABBREVIATIONS.add("mrs");
        ABBREVIATIONS.add("ms");
    }

    /**
     * Fix abbreviation.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        Token current;
        for (int i = 0; i < tokens.size(); ++i) {
            current = tokens.get(i);
            if (current.containsProperty(TokenProperty.WORD)) {
                if (ABBREVIATIONS.contains(current.toString())) {
                    current.capitalize();
                    if (i + 1 < tokens.size()) {
                        Token nextToken = tokens.get(i + 1);
                        if (!nextToken.equals(TokenConstants.PERIOD) && !nextToken.equals(TokenConstants.ELLIPSES)) {
                            tokens.add(i + 1, TokenConstants.PERIOD);
                        }
                    } else {
                        tokens.add(i + 1, TokenConstants.PERIOD);
                    }
                }
            }
        }
    }

}
