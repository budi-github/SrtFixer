package main.tokenizer.fix.spelling;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix l's to I's.
 * 
 * @author budi
 */
public class FixLToI {

    /**
     * Set of words to be corrected.
     */
    private static final Set<String> L_WORDS;

    static {
        L_WORDS = new HashSet<String>();
        L_WORDS.add("l");
        L_WORDS.add("ld");
        L_WORDS.add("lf");
        L_WORDS.add("lll");
        L_WORDS.add("lm");
        L_WORDS.add("ln");
        L_WORDS.add("lt");
        L_WORDS.add("lts");
        L_WORDS.add("ls");
        L_WORDS.add("lve");
    }

    /**
     * Fix.
     * 
     * @param tt {@link TokenizedText}
     * 
     * @see #fixSingleLToI(TokenizedText)
     * @see #fixCommonLSpelling(TokenizedText)
     */
    public static void fix(TokenizedText tt) {
        fixSingleLToI(tt);
        fixCommonLSpelling(tt);
    }

    /**
     * Fix single l to I.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixSingleLToI(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        for (Token token : tokens) {
            if (token.containsProperty(TokenProperty.WORD)) {
                if (token.toString().equals("l")) {
                    token.getTokenStringBuilder().setCharAt(0, 'I');
                    token.setAll(TokenProperty.UPPER);
                    token.setStartsWith(TokenProperty.UPPER);
                    token.addProperty(TokenProperty.UPPER);
                    token.removeProperty(TokenProperty.LOWER);
                }
            }
        }
    }

    /**
     * Fix common l to i spelling.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixCommonLSpelling(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        for (Token token : tokens) {
            if (token.containsProperty(TokenProperty.WORD)) {
                if (L_WORDS.contains(token.toString())) {
                    token.getTokenStringBuilder().setCharAt(0, 'I');
                    token.setStartsWith(TokenProperty.UPPER);
                    token.addProperty(TokenProperty.UPPER);
                }
            }
        }
    }

}
