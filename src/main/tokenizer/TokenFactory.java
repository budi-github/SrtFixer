package main.tokenizer;

/**
 * Token factory.
 * 
 * @author budi
 */
public class TokenFactory {

    /**
     * Get the {@link Token} representation of a whitespace character.
     * 
     * @param c character
     * @return {@link Token} representation of the character
     */
    public static Token getWhitespaceToken(char c) {
        switch (c) {
        case ' ':
            return TokenConstants.SPACE;
        case '\t':
            return TokenConstants.TAB;
        case '\n':
            return TokenConstants.NEWLINE;
        default:
            // TODO: generate warning message
            return new Token(String.valueOf(c), TokenProperty.W_SPACE);
        }
    }

    /**
     * Get the {@link Token} representation of a punctuation character.
     * 
     * @param c character
     * @return {@link Token} representation of the character
     */
    public static Token getPunctuationToken(char c) {
        switch (c) {
        case '.':
            return TokenConstants.PERIOD;
        case '?':
            return TokenConstants.QUESTION_MARK;
        case '!':
            return TokenConstants.EXCLAMATION_POINT;
        case ',':
            return TokenConstants.COMMA;
        case '-':
            return TokenConstants.DASH;
        case ':':
            return TokenConstants.COLON;
        case ';':
            return TokenConstants.SEMICOLON;
        case '\'':
        case '’':
            return TokenConstants.SINGLE_QUOTE;
        case '\"':
            return TokenConstants.DOUBLE_QUOTE;
        case '@':
            return TokenConstants.AT_SIGN;
        case '#':
            return TokenConstants.POUND;
        case '$':
            return TokenConstants.DOLLAR_SIGN;
        case '%':
            return TokenConstants.PERCENT;
        case '&':
            return TokenConstants.AMPERSAND;
        case '/':
            return TokenConstants.SLASH;
        default:
            // TODO: generate warning message
            return new Token(String.valueOf(c), TokenProperty.PUNCT);
        }
    }

}
