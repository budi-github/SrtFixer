package main.tokenizer.fix;

import java.util.List;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;
import main.tokenizer.collection.AbbreviationCollection;

/**
 * Fix abbreviation.
 * 
 * @author budi
 */
public class FixAbbreviation {

    /**
     * Fix abbreviation.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        Token current;
        for (int i = 0; i < tokens.size(); ++i) {
            current = tokens.get(i);
            if (current.containsProperty(TokenProperty.WORD)) {
                if (AbbreviationCollection.ABBREVIATIONS.contains(current.toString())) {
                    current.capitalize();
                    if (i + 1 < tokens.size()) {
                        Token nextToken = tokens.get(i + 1);
                        if (!nextToken.equals(TokenConstants.PERIOD) && !nextToken.equals(TokenConstants.ELLIPSES)) {
                            tokens.add(i + 1, TokenConstants.PERIOD);
                        }
                    } else {
                        tokens.add(i + 1, TokenConstants.PERIOD);
                    }
                }
            }
        }
    }

}
