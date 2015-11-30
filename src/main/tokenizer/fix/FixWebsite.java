package main.tokenizer.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenizedText;
import main.tokenizer.collection.WebsiteCollection;

/**
 * Fix website.
 * 
 * @author budi
 */
public class FixWebsite {

    /**
     * Remove excess space in websites.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void removeExcessSpace(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevPrevToken != null) {
                if (prevPrevToken.toString().equalsIgnoreCase("www")) {
                    if (prevToken.equals(TokenConstants.PERIOD)) {
                        if (currentToken.equals(TokenConstants.SPACE)) {
                            removeIndexSet.add(i);
                        }
                    }
                } else if (prevPrevToken.equals(TokenConstants.PERIOD)) {
                    if (prevToken.equals(TokenConstants.SPACE)) {
                        if (WebsiteCollection.VALID_EXTENSIONS.contains(currentToken.toString())) {
                            removeIndexSet.add(i - 1);
                        }
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
