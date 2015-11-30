package main.tokenizer.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenUtil;
import main.tokenizer.TokenizedText;

/**
 * Fix apostrophe.
 * 
 * @author budi
 */
public class FixApostrophe {

    /**
     * Fix apostrophe.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevToken = currentToken;
            currentToken = tokens.get(i);

            if (prevToken != null && prevToken.toString().endsWith("in") && !prevToken.toString().equals("in")) {
                if (currentToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    removeIndexSet.addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, prevToken));
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
