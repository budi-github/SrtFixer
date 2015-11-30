package main.tokenizer.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenUtil;
import main.tokenizer.TokenizedText;
import main.tokenizer.detect.DetectContraction;

/**
 * Fix contraction.
 * 
 * @author budi
 */
public class FixContraction {

    /**
     * Fix contraction.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fix(TokenizedText tt) {
        removeExcessSpace(tt);
        fixSpelling(tt);
    }

    /**
     * Remove excess space in contractions.
     * 
     * @param tt {@link TokenizedText}
     */
    private static void removeExcessSpace(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);

            if (prevPrevToken != null && prevPrevToken.containsProperty(TokenProperty.WORD)) {
                if (prevToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    if (DetectContraction.CONTRACTIONS_ENDINGS.contains(currentToken.toString())) {
                        removeIndexSet
                                .addAll(TokenUtil.lookBackAndRemove(tokens, i, TokenConstants.SPACE, prevPrevToken));
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

    /**
     * Fix common contraction spelling errors.
     * 
     * @param tt {@link TokenizedText}
     */
    private static void fixSpelling(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token currentToken;
        for (int i = 0; i < tokens.size(); ++i) {
            currentToken = tokens.get(i);
            String tokenString = currentToken.toString();
            if (DetectContraction.CONTRACTIONS_ERROR_MAP.containsKey(tokenString)) {
                int contractionIndex = DetectContraction.CONTRACTIONS_ERROR_MAP.get(tokenString);
                int index = 0;
                char c = tokenString.charAt(0);
                while (tokenString.length() > index + 1) {
                    if (Character.toLowerCase(tokenString.charAt(index)) == Character.toLowerCase(c)) {
                        break;
                    }
                    ++index;
                }
                TokenizedText first = new TokenizedText(tokenString.substring(0, contractionIndex + index));
                TokenizedText second = new TokenizedText(tokenString.substring(contractionIndex + index));

                tokens.add(i + 1, first.getTokens().get(0));
                tokens.add(i + 2, TokenConstants.SINGLE_QUOTE);
                tokens.add(i + 3, second.getTokens().get(0));

                removeIndexSet.add(i);
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
