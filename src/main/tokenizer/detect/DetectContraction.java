package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;
import main.tokenizer.collection.ContractionCollection;

/**
 * Detect contraction.
 * 
 * @author budi
 */
public class DetectContraction {

    /**
     * Detect contraction.
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
                        if (!ContractionCollection.CONTRACTIONS_ENDINGS.contains(currentToken.toString())) {
                            System.err.println(
                                    String.format("Warning! Unidentified contraction ending: %s", currentToken));
                        }
                        removeIndexSet.add(i - 1);
                        removeIndexSet.add(i);
                        TokenProperties properties = TokenProperties.mergeTokenProperties(prevPrevToken, currentToken);
                        properties.addProperty(TokenProperty.CONTRACTION);

                        StringBuilder builder = new StringBuilder();
                        builder.append(prevPrevToken);
                        builder.append(prevToken);
                        builder.append(currentToken);

                        tokens.set(i - 2, new Token(builder.toString(), properties));
                    }
                }
            } else if (prevToken != null && prevToken.toString().endsWith("in") && !prevToken.toString().equals("in")) {
                if (currentToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    removeIndexSet.add(i);
                    TokenProperties properties = TokenProperties.mergeTokenProperties(prevToken, currentToken);
                    properties.addProperty(TokenProperty.CONTRACTION);

                    StringBuilder builder = new StringBuilder();
                    builder.append(prevToken);
                    builder.append(currentToken);

                    tokens.set(i - 1, new Token(builder.toString(), properties));
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
