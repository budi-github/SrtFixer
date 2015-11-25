package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Detect abbreviation.
 * 
 * @author budi
 */
public class DetectAbbreviation {

    /**
     * Set of all abbreviations.
     */
    public static final Set<String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.add("dr");
        ABBREVIATIONS.add("mr");
        ABBREVIATIONS.add("mrs");
        ABBREVIATIONS.add("ms");
    }

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
            if (prevToken != null && ABBREVIATIONS.contains(prevToken.toString())) {
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
