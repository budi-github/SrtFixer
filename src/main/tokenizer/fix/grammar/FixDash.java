package main.tokenizer.fix.grammar;

import java.util.List;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenizedText;

/**
 * Fix dash.
 * 
 * @author budi
 */
public class FixDash {

    /**
     * Fix dash.
     * 
     * @param tt {@link TokenizedText}
     * 
     * @see #fixBeginningDash(TokenizedText)
     */
    public static void fix(TokenizedText tt) {
        fixBeginningDash(tt);
    }

    /**
     * Fix dash found at the beginning of the line.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixBeginningDash(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        if (tokens.size() >= 2) {
            Token first = tokens.get(0);
            Token second = tokens.get(1);
            if (first.equals(TokenConstants.DASH) && !second.equals(TokenConstants.SPACE)) {
                tokens.add(1, TokenConstants.SPACE);
            }
        }
    }

}
