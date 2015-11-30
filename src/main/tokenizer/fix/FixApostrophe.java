package main.tokenizer.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenUtil;
import main.tokenizer.TokenizedText;
import main.tokenizer.collection.ContractionCollection;
import main.util.string.StringUtil;

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

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);

            if (prevPrevToken != null) {
                if (prevToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    if (ContractionCollection.PREPEND_QUOTE.contains(currentToken.toString())
                            || StringUtil.isNumeric(currentToken.toString().replace("s", ""))) {
                        if (i - 2 >= 0 && !tokens.get(i - 2).equals(TokenConstants.SPACE)) {
                            tokens.add(i - 1, TokenConstants.SPACE);
                            ++i;
                        }
                        removeIndexSet.addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, prevToken));
                    }
                }
            }
            if (currentToken.equals(TokenConstants.SINGLE_QUOTE)) {
                if (prevToken != null) {
                    if (prevToken.equalsIgnoreCase("o") || prevToken.equalsIgnoreCase("d")) {
                        removeIndexSet
                                .addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, currentToken));
                    } else if (prevToken.endsWith("in") && !prevToken.equalsIgnoreCase("in")) {
                        removeIndexSet
                                .addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, currentToken));
                    } else if (ContractionCollection.APPEND_QUOTE.contains(prevToken.toString())) {
                        removeIndexSet
                                .addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, currentToken));
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
