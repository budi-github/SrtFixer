package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Detect contraction.
 * 
 * @author budi
 */
public class DetectContraction {

    /**
     * Detect time.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void detect(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevPrevToken != null && prevPrevToken.containsProperty(TokenProperty.WORD)) {
                if (prevToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    if (currentToken.containsProperty(TokenProperty.WORD)) {
                        removeIndexSet.add(i - 1);
                        removeIndexSet.add(i);
                        TokenProperties properties = TokenProperties.mergeTokenProperties(prevPrevToken.getTokenProperties(),
                                currentToken.getTokenProperties());
                        properties.addProperty(TokenProperty.CONTRACTION);
                        tokens.set(i - 2,
                                new Token(prevPrevToken.getTokenString() + prevToken.getTokenString() + currentToken.getTokenString(),
                                        properties));
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
