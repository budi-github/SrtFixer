package main.tokenizer.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix time.
 * 
 * @author budi
 */
public class FixTime {

    /**
     * Fix time.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        removeExcessSpaces(tt);
    }

    /**
     * Remove excess space.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void removeExcessSpaces(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevPrevToken, prevPrevToken = null, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevPrevToken = prevPrevToken;
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevPrevPrevToken != null && prevPrevPrevToken.containsProperty(TokenProperty.NUMBER)) {
                if (prevPrevToken.equals(TokenConstants.COLON)) {
                    if (prevToken.equals(TokenConstants.SPACE)) {
                        if (currentToken.containsProperty(TokenProperty.NUMBER)) {
                            removeIndexSet.add(i - 1);
                        }
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
