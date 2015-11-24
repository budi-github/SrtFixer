package main.tokenizer.fix.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix acronym.
 * 
 * @author budi
 */
public class FixAcronym {

    /**
     * Set of acronyms that should be all lowercase.
     */
    private static final Set<String> LOWERCASE_ACRONYMS;

    static {
        LOWERCASE_ACRONYMS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        LOWERCASE_ACRONYMS.add("i.e.");
    }

    /**
     * Remove excess space in acronyms.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void removeExcessSpace(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token currentToken;
        boolean foundSingleLetter = false, foundPeriod = false;
        for (int i = 0; i < tokens.size(); ++i) {
            currentToken = tokens.get(i);
            if (currentToken.containsProperty(TokenProperty.WORD)) {
                if (currentToken.length() == 1) {
                    foundSingleLetter = true;
                } else if (removeIndexSet.contains(i - 1)) {
                    removeIndexSet.remove(i - 1);
                }
            } else if (foundSingleLetter) {
                if (currentToken.equals(TokenConstants.PERIOD)) {
                    foundPeriod = true;
                }
                foundSingleLetter = false;
            } else if (foundPeriod) {
                if (currentToken.equals(TokenConstants.SPACE)) {
                    removeIndexSet.add(i);
                }
                foundPeriod = false;
            }
            /*else if (removeIndexSet.contains(i - 1)) {
                removeIndexSet.remove(i - 1);
            }*/
        }

        tt.removeIndices(removeIndexSet);
    }

    /**
     * Fix capitalization.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixCapitalization(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        for (Token token : tokens) {
            if (token.containsProperty(TokenProperty.ACRONYM)) {
                if (LOWERCASE_ACRONYMS.contains(token.toString())) {
                    token.modifyToLowerCase();
                } else {
                    token.modifyToUpperCase();
                }
            }
        }
    }

}
