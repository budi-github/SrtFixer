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
 * Detect acronym.
 * 
 * @author budi
 */
public class DetectAcronym {

    /**
     * Set of acronyms that should be all lowercase.
     */
    public static final Set<String> LOWERCASE_ACRONYMS;

    static {
        LOWERCASE_ACRONYMS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        LOWERCASE_ACRONYMS.add("i.e.");
    }

    /**
     * Detect acronym.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void detect(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> potentialAcronymIndices = new HashSet<Integer>();
        Set<Integer> removeIndicesSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevToken = currentToken;
            currentToken = tokens.get(i);

            if (prevToken != null && prevToken.containsProperty(TokenProperty.WORD)
                    && prevToken.toString().length() == 1) {
                if (currentToken.equals(TokenConstants.PERIOD)) {
                    potentialAcronymIndices.add(i);
                    potentialAcronymIndices.add(i - 1);
                    removeIndicesSet.add(i);
                    removeIndicesSet.add(i - 1);
                }
            }
        }

        for (int i = 0; i < tokens.size() && !potentialAcronymIndices.isEmpty(); ++i) {
            if (potentialAcronymIndices.contains(i) && potentialAcronymIndices.contains(i + 1)) {
                TokenProperties properties = TokenProperties.copyTokenProperties(tokens.get(i));
                potentialAcronymIndices.remove(i);
                potentialAcronymIndices.remove(i + 1);
                int index = i + 1;

                while (true) {
                    if (potentialAcronymIndices.contains(index + 1) && potentialAcronymIndices.contains(index + 2)) {
                        properties = TokenProperties.mergeTokenProperties(properties, tokens.get(index + 1));
                        properties.addProperty(TokenProperty.ACRONYM);

                        potentialAcronymIndices.remove(index + 1);
                        potentialAcronymIndices.remove(index + 2);
                        removeIndicesSet.remove(i);
                        index += 2;
                    } else {
                        break;
                    }
                }

                if (properties.containsProperty(TokenProperty.ACRONYM)) {
                    StringBuilder builder = new StringBuilder();
                    for (int tokenIndex = i; tokenIndex < index + 1; ++tokenIndex) {
                        builder.append(tokens.get(tokenIndex).toString());
                    }
                    tokens.set(i, new Token(builder.toString(), properties));
                } else {
                    removeIndicesSet.remove(i);
                    removeIndicesSet.remove(i + 1);
                }

            }
        }

        tt.removeIndices(removeIndicesSet);
    }

}
