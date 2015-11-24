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
 * Detect time.
 * 
 * @author budi
 */
public class DetectTime {

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
            if (prevPrevToken != null && prevPrevToken.containsProperty(TokenProperty.NUMBER)) {
                if (prevToken.equals(TokenConstants.COLON)) {
                    if (currentToken.containsProperty(TokenProperty.NUMBER)) {
                        removeIndexSet.add(i - 1);
                        removeIndexSet.add(i);
                        TokenProperties properties = TokenProperties.mergeTokenProperties(
                                prevPrevToken.getTokenProperties(), prevToken.getTokenProperties());
                        properties =
                                TokenProperties.mergeTokenProperties(properties, currentToken.getTokenProperties());
                        properties.addProperty(TokenProperty.TIME);
                        properties.addProperty(TokenProperty.WORD); // TODO: does it need this?
                        tokens.set(i - 2, new Token(
                                prevPrevToken.toString() + prevToken.toString() + currentToken.toString(), properties));
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
