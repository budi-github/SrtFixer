package main.tokenizer;

/**
 * Enum of all token properties.
 * 
 * @author budi
 */
public enum TokenProperty {

    /**
     * Word.
     */
    WORD,

    /**
     * Lowercase letters.
     */
    LOWER,

    /**
     * Uppercase letters.
     */
    UPPER,

    /**
     * Digit.
     */
    DIGIT,

    /**
     * Number.
     */
    NUMBER,

    /**
     * Alphanumeric. Contains both letter(s) and number(s).
     */
    ALPHANUMERIC,

    /**
     * Punctuation.
     */
    PUNCT,

    /**
     * Whitespace.
     */
    W_SPACE,

    /**
     * Acronym.
     */
    ACRONYM,

    /**
     * Contraction.
     */
    CONTRACTION,

    /**
     * Time.
     */
    TIME,

    /**
     * Website.
     */
    WEBSITE,

}
