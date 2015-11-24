package main.tokenizer.fix.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix punctuation spacing.
 * 
 * @author budi
 */
public class FixPunctuationSpacing {

    /**
     * Set of all tokens that do not require removing a preceding space.
     */
    private static final Set<Token> DO_NOT_REMOVE_SPACE;

    static {
        DO_NOT_REMOVE_SPACE = new HashSet<Token>();
        DO_NOT_REMOVE_SPACE.add(TokenConstants.AMPERSAND);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.AT_SIGN);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.DOLLAR_SIGN);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.POUND);
    }

    /**
     * Fix punctuation spacing.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (currentToken.isAll(TokenProperty.PUNCT) && !DO_NOT_REMOVE_SPACE.contains(currentToken)) {
                if (prevToken != null && prevToken.equals(TokenConstants.SPACE)) {
                    removeIndexSet.add(i - 1);
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
