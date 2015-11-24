package main.tokenizer;

/**
 * Token constants.
 * 
 * @author budi
 */
public class TokenConstants {

    /**
     * Ellipses.
     */
    public static final Token ELLIPSES = new Token("...", TokenProperty.PUNCT);

    /**
     * Period.
     */
    public static final Token PERIOD = new Token(".", TokenProperty.PUNCT);

    /**
     * Question mark.
     */
    public static final Token QUESTION_MARK = new Token("?", TokenProperty.PUNCT);

    /**
     * Exclamation point.
     */
    public static final Token EXCLAMATION_POINT = new Token("!", TokenProperty.PUNCT);

    /**
     * Comma.
     */
    public static final Token COMMA = new Token(",", TokenProperty.PUNCT);

    /**
     * Dash.
     */
    public static final Token DASH = new Token("-", TokenProperty.PUNCT);

    /**
     * Colon.
     */
    public static final Token COLON = new Token(":", TokenProperty.PUNCT);

    /**
     * Semicolon.
     */
    public static final Token SEMICOLON = new Token(";", TokenProperty.PUNCT);

    /**
     * Single quote.
     */
    public static final Token SINGLE_QUOTE = new Token("'", TokenProperty.PUNCT);

    /**
     * Double quote.
     */
    public static final Token DOUBLE_QUOTE = new Token("\"", TokenProperty.PUNCT);

    /**
     * Dollar sign.
     */
    public static final Token DOLLAR_SIGN = new Token("$", TokenProperty.PUNCT);

    /**
     * At sign.
     */
    public static final Token AT_SIGN = new Token("@", TokenProperty.PUNCT);

    /**
     * Pound.
     */
    public static final Token POUND = new Token("#", TokenProperty.PUNCT);

    /**
     * Percent.
     */
    public static final Token PERCENT = new Token("%", TokenProperty.PUNCT);

    /**
     * Ampersand.
     */
    public static final Token AMPERSAND = new Token("&", TokenProperty.PUNCT);

    /**
     * Slash.
     */
    public static final Token SLASH = new Token("/", TokenProperty.PUNCT);

    /**
     * Space.
     */
    public static final Token SPACE = new Token(" ", TokenProperty.W_SPACE);

    /**
     * Tab.
     */
    public static final Token TAB = new Token("\t", TokenProperty.W_SPACE);

    /**
     * Newline.
     */
    public static final Token NEWLINE = new Token("\n", TokenProperty.W_SPACE);

}
