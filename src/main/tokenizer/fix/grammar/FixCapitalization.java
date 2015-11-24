package main.tokenizer.fix.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix capitalization.
 * 
 * @author budi
 */
public class FixCapitalization {

    /**
     * A string preceding any token in this set should be capitalized.
     */
    private static final Set<Token> PRECEDING_SHOULD_CAPITALIZE;
    
    static {
        PRECEDING_SHOULD_CAPITALIZE = new HashSet<Token>();
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.PERIOD);
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.QUESTION_MARK);
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.EXCLAMATION_POINT);
    }
    
    /**
     * Fix capitalization.
     * 
     * @param tt {@link TokenizedText}
     * 
     * @see #fixPrecedingPunctuation(TokenizedText)
     */
    public static void fix(TokenizedText tt) {
        // TODO: first word in line optional capitalize
        fixPrecedingPunctuation(tt);
    }

    /**
     * Fix preceding punctuation.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixPrecedingPunctuation(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevToken != null && PRECEDING_SHOULD_CAPITALIZE.contains(prevToken)) {
                if (currentToken.containsProperty(TokenProperty.WORD) && currentToken.startsWith(TokenProperty.LOWER)) {
                    currentToken.capitalize();
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
