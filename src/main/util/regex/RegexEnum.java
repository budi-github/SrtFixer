package main.util.regex;

import java.util.regex.Pattern;

/**
 * Enum of all regex. Works as a caching mechinism.
 * 
 * @author budi
 */
public enum RegexEnum {

    /**
     * All lowercase and uppercase letters.
     */
    ALPHABET("[a-zA-Z]"),

    /**
     * All lowercase and uppercase letters, digits.
     */
    ALPHANUMERIC("[^\\p{P}]"),

    /**
     * Colon.
     */
    COLON(":"),

    /**
     * Comma.
     */
    COMMA(","),

    /**
     * Two or more consecutive periods.
     */
    ELLIPSES("[.]{2,}"),

    /**
     * Ends with punctuation.
     */
    ENDS_WITH_PUNCTUATION("[\\p{P}]+$"),

    /**
     * Matches exactly 1 or more digits followed by an s.
     */
    MATCH_DIGIT_AND_S("^[\\d]+s$"),

    /**
     * Matches sequence of punctuation and/or space.
     */
    MATCH_PUNCTUATION_AND_SPACE("[\\p{P} ]+"),

    /**
     * Newline.
     */
    NEWLINE("\n"),

    /**
     * All punctuation.
     */
    PUNCTUATION("\\p{P}"),

    /**
     * Two or more consecutive single quotes.
     */
    QUOTES("([']{2,})|(' ')"),

    /**
     * Space.
     */
    SPACE(" "),

    /**
     * Spaces between all punctuation.
     */
    SPACES_BETWEEN_PUNCTUATION("(?<=\\S)(?:(?<=\\p{P})|(?=\\p{P}))(?=\\S)"),

    /**
     * Strips all leading and trailing punctuation in a String.
     */
    STRIP_PUNCTUATION("^[\\p{P}]+|[\\p{P}]+$"),

    /**
     * Strips all leading punctuation and trailing punctuation except for
     * periods in a String.
     */
    STRIP_PUNCTUATION_ABBREVIATIONS("^[\\p{P}]+|(?![.])[\\p{P}]+$"),

    /**
     * Time arrow.
     */
    TIME_ARROW("-->");

    /**
     * Pattern object.
     */
    private final Pattern pattern;

    /**
     * Class constructor.
     * 
     * @param regex regex to compile
     */
    private RegexEnum(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * @return {@link #pattern}
     */
    public Pattern getPattern() {
        return pattern;
    }

}
