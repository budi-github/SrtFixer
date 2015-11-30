package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;
import main.tokenizer.collection.AbbreviationCollection;

/**
 * Detect abbreviation.
 * 
 * @author budi
 */
public class DetectAbbreviation {

    /**
     * Detect abbreviation.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void detect(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevToken != null && AbbreviationCollection.ABBREVIATIONS.contains(prevToken.toString())) {
                if (currentToken.equals(TokenConstants.PERIOD)) {
                    removeIndexSet.add(i);
                    TokenProperties properties = TokenProperties.copyTokenProperties(prevToken);
                    properties.addProperty(TokenProperty.ABBREVIATION);

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
