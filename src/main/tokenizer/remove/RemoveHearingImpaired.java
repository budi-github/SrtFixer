package main.tokenizer.remove;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Remove hearing impaired.
 * 
 * @author budi
 */
public class RemoveHearingImpaired {

    /**
     * Remove hearing impaired.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void remove(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        boolean foundColon = false, foundDash = false;
        Token currentToken;
        for (int i = 0; i < tokens.size(); ++i) {
            currentToken = tokens.get(i);
            if (currentToken.equals(TokenConstants.DASH)) {
                foundDash = true;
            } else if (currentToken.isAll(TokenProperty.UPPER) || currentToken.isAll(TokenProperty.DIGIT)) {
                removeIndexSet.add(i);
            } else if (currentToken.equals(TokenConstants.SPACE)) {
                if (foundDash) {
                    foundDash = false;
                } else {
                    removeIndexSet.add(i);
                }
            } else if (currentToken.equals(TokenConstants.COLON)) {
                foundColon = true;
                removeIndexSet.add(i);
                if (i + 1 < tokens.size()) {
                    Token nextToken = tokens.get(i + 1);
                    if (nextToken.equals(TokenConstants.SPACE)) {
                        removeIndexSet.add(i + 1);
                    }
                }
                break;
            } else {
                break;
            }
        }

        if (foundColon) {
            tt.removeIndices(removeIndexSet);
        }
    }

}
