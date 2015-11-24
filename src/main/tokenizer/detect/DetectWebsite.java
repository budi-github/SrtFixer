package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;
import main.tokenizer.fix.grammar.FixWebsite;

/**
 * Detect website.
 * 
 * @author budi
 */
public class DetectWebsite {

    /**
     * Detect website.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void detect(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        boolean foundWWW = false;
        Token prevPrevPrevPrevToken, prevPrevPrevToken = null, prevPrevToken = null, prevToken = null,
                currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevPrevPrevToken = prevPrevPrevToken;
            prevPrevPrevToken = prevPrevToken;
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);

            if (prevPrevToken != null && prevPrevToken.containsProperty(TokenProperty.WORD)) {
                if (prevToken.equals(TokenConstants.PERIOD)) {
                    if (FixWebsite.VALID_EXTENSIONS.contains(currentToken.toString())) {
                        if (prevPrevPrevPrevToken != null && prevPrevPrevPrevToken.containsProperty(TokenProperty.WORD)
                                && prevPrevPrevPrevToken.toString().equals("www")) {
                            if (prevPrevPrevToken.equals(TokenConstants.PERIOD)) {
                                foundWWW = true;
                            }
                        }
                        if (foundWWW) {
                            removeIndexSet.add(i - 3);
                            removeIndexSet.add(i - 2);
                        }

                        removeIndexSet.add(i - 1);
                        removeIndexSet.add(i);

                        TokenProperties properties = null;
                        if (foundWWW) {
                            properties = TokenProperties.mergeTokenProperties(prevPrevPrevPrevToken, prevPrevPrevToken);
                        }
                        properties = TokenProperties.mergeTokenProperties(properties, prevPrevToken);
                        properties = TokenProperties.mergeTokenProperties(properties, prevToken);
                        properties = TokenProperties.mergeTokenProperties(properties, currentToken);
                        properties.addProperty(TokenProperty.WEBSITE);

                        if (foundWWW) {
                            tokens.set(i - 4,
                                    new Token(prevPrevPrevPrevToken.toString() + prevPrevPrevToken.toString()
                                            + prevPrevToken.toString() + prevToken.toString() + currentToken.toString(),
                                    properties));
                        } else {
                            tokens.set(i - 2,
                                    new Token(prevPrevToken.toString() + prevToken.toString() + currentToken.toString(),
                                            properties));
                        }
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
