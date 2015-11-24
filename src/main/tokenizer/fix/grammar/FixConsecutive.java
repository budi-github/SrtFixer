package main.tokenizer.fix.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenizedText;

/**
 * Fix ellipses.
 * 
 * @author budi
 */
public class FixConsecutive {

    /**
     * Fix consecutive.
     * 
     * @param tt {@link TokenizedText}
     * 
     * @see #fixConsecutiveProperties(TokenizedText, Token, Token)
     */
    public static void fix(TokenizedText tt) {
        fixConsecutiveProperties(tt, TokenConstants.PERIOD, TokenConstants.ELLIPSES);
        fixConsecutiveProperties(tt, TokenConstants.QUESTION_MARK, TokenConstants.QUESTION_MARK);
        fixConsecutiveProperties(tt, TokenConstants.EXCLAMATION_POINT, TokenConstants.EXCLAMATION_POINT);
        fixConsecutiveProperties(tt, TokenConstants.COMMA, TokenConstants.COMMA);
        fixConsecutiveProperties(tt, TokenConstants.SPACE, TokenConstants.SPACE);
        fixConsecutiveProperties(tt, TokenConstants.SINGLE_QUOTE, TokenConstants.DOUBLE_QUOTE);
        fixConsecutiveProperties(tt, TokenConstants.DOUBLE_QUOTE, TokenConstants.DOUBLE_QUOTE);
    }

    /**
     * Fix consectutive properties.
     * 
     * @param tt {@link TokenizedText}
     * @param removeToken token to remove
     * @param replacementToken token to replace with
     */
    private static void fixConsecutiveProperties(TokenizedText tt, Token removeToken, Token replacementToken) {
        List<Token> tokens = tt.getTokens();

        // TODO: check for contains or frequency? or keep counts of elements in TokenizedText?
        /*
        if (Collections.frequency(tokens, removeToken) <= 1) {
            return;
        }
        */

        Set<Integer> updateIndexSet = new HashSet<Integer>();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        boolean foundCharacter = false;
        Token currentToken;
        for (int i = 0; i < tokens.size(); ++i) {
            currentToken = tokens.get(i);
            if (currentToken.equals(removeToken)) {
                if (!foundCharacter) {
                    foundCharacter = true;
                    updateIndexSet.add(i);
                } else {
                    removeIndexSet.add(i);
                }
            } else {
                if (foundCharacter) {
                    foundCharacter = false;
                }
            }
        }

        // set first instance of consectutive removeTokens to replacementToken
        for (int updateIndex : updateIndexSet) {
            if (removeIndexSet.contains(updateIndex + 1)) {
                tokens.set(updateIndex, replacementToken);
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
