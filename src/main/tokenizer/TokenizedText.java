package main.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Tokenize subtitle text.
 * 
 * @author budi
 */
public class TokenizedText {

    /**
     * Original text.
     */
    private final String originalText;

    /**
     * List of {@link Token}s.
     */
    private final List<Token> tokens;

    /**
     * Class constructor.
     * 
     * @param text text to tokenize
     */
    public TokenizedText(String text) {
        this.originalText = text;
        this.tokens = tokenize();
    }

    /**
     * Tokenize text.
     * 
     * @return list of {@link Token}s
     */
    private List<Token> tokenize() {
        List<Token> tokens = new ArrayList<Token>();

        Token token = new Token();
        for (char c : originalText.trim().toCharArray()) {
            if (Character.isLowerCase(c)) {
                if (token.getStartsWith() == null) {
                    token.setStartsWith(TokenProperty.LOWER);
                    token.setAll(TokenProperty.LOWER);
                } else {
                    if (token.getStartsWith() != null && !token.isAll(TokenProperty.LOWER)) {
                        token.setAll(null);
                    }
                }
                token.addProperty(TokenProperty.LOWER);
                token.append(c);
            } else if (Character.isUpperCase(c)) {
                if (token.getStartsWith() == null) {
                    token.setStartsWith(TokenProperty.UPPER);
                    token.setAll(TokenProperty.UPPER);
                } else {
                    if (token.getStartsWith() != null && !token.isAll(TokenProperty.UPPER)) {
                        token.setAll(null);
                    }
                }
                token.addProperty(TokenProperty.UPPER);
                token.append(c);
            } else if (Character.isDigit(c)) {
                if (token.getStartsWith() == null) {
                    token.setStartsWith(TokenProperty.DIGIT);
                    token.setAll(TokenProperty.DIGIT);
                } else {
                    if (token.getStartsWith() != null && !token.isAll(TokenProperty.DIGIT)) {
                        token.setAll(null);
                    }
                }
                token.addProperty(TokenProperty.DIGIT);
                token.append(c);
            } else {
                // add current token to list
                if (!token.isEmpty()) {
                    token.updateProperties();
                    tokens.add(token);
                }

                // add current character to token
                // should be either whitespace or punctuation
                if (Character.isWhitespace(c)) {
                    token = TokenFactory.getWhitespaceToken(c);
                } else {
                    token = TokenFactory.getPunctuationToken(c);
                }
                tokens.add(token);

                // reset token
                token = new Token();
            }
        }

        if (!token.isEmpty()) {
            token.getTokenProperties().analyzeProperties();
            tokens.add(token);
        }

        return tokens;
    }

    /**
     * Remove {@link Token} at each index in set.
     * 
     * @param indexCollection
     */
    public void removeIndices(Collection<Integer> indexCollection) {
        int index = 0;
        for (Iterator<Token> iterator = tokens.iterator(); iterator.hasNext();) {
            iterator.next();
            if (indexCollection.contains(index)) {
                iterator.remove();
            }
            ++index;
        }
    }

    /**
     * Print debug string.
     */
    public void printDebugString() {
        for (Token token : tokens) {
            System.out.println(token.debugString());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Token t : tokens) {
            builder.append(t);
        }
        return builder.toString();
    }

    /**
     * @return {@link #originalText}
     */
    public String getOriginalText() {
        return originalText;
    }

    /**
     * @return {@link #tokens}
     */
    public List<Token> getTokens() {
        return tokens;
    }

}
