package main.tokenizer.fix.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Fix punctuation spacing.
 * 
 * @author budi
 */
public class FixPunctuationSpacing {

    /**
     * Set of all tokens that do not require removing a preceding space.
     */
    private static final Set<Token> DO_NOT_REMOVE_SPACE;

    static {
        DO_NOT_REMOVE_SPACE = new HashSet<Token>();
        DO_NOT_REMOVE_SPACE.add(TokenConstants.AMPERSAND);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.AT_SIGN);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.DOLLAR_SIGN);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.POUND);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.SINGLE_QUOTE);
        DO_NOT_REMOVE_SPACE.add(TokenConstants.DOUBLE_QUOTE);
    }

    /**
     * Fix punctuation spacing.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        removeExcessSpace(tt);
        removeExcessSpaceBeginningEllipses(tt);
        //addMissingSpace(tt);
    }

    /**
     * Remove excess space.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void removeExcessSpace(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (currentToken.isAll(TokenProperty.PUNCT) && !DO_NOT_REMOVE_SPACE.contains(currentToken)) {
                if (prevToken != null && prevToken.equals(TokenConstants.SPACE)) {
                    removeIndexSet.add(i - 1);
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

    /**
     * Remove excess space in cases where there is an ellipses, a space,
     * followed by the first word in a sentence.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void removeExcessSpaceBeginningEllipses(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (currentToken.containsProperty(TokenProperty.WORD)) {
                if (prevToken != null && prevToken.equals(TokenConstants.SPACE)) {
                    if (prevPrevToken != null && prevPrevToken.equals(TokenConstants.ELLIPSES)) {
                        removeIndexSet.add(i - 1);
                    }
                }
                break;
            }
        }

        tt.removeIndices(removeIndexSet);
    }

    /**
     * Add missing space.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void addMissingSpace(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (DO_NOT_REMOVE_SPACE.contains(currentToken)) {
                if (prevToken != null && !prevToken.equals(TokenConstants.SPACE)) {
                    if (i + 1 < tokens.size()) {
                        Token nextToken = tokens.get(i + 1);
                        if (nextToken.equals(TokenConstants.SPACE)) {
                            tokens.add(i, TokenConstants.SPACE);
                            currentToken = TokenConstants.SPACE;
                        }
                    }
                }
            }
        }
    }

}
