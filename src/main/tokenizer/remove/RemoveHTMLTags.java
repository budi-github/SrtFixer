package main.tokenizer.remove;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenizedText;

/**
 * Remove HTML tags.
 * 
 * @author budi
 */
public class RemoveHTMLTags {

    /**
     * Remove HTML tags.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void remove(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        boolean remove = false;
        Token currentToken;
        for (int i = 0; i < tokens.size(); ++i) {
            currentToken = tokens.get(i);
            if (currentToken.equals(TokenConstants.LESS_THAN)) {
                remove = true;
            } else if (currentToken.equals(TokenConstants.GREATER_THAN)) {
                removeIndexSet.add(i);
                remove = false;
            }

            if (remove) {
                removeIndexSet.add(i);
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
