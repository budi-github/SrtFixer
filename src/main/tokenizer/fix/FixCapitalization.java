package main.tokenizer.fix;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.data.DataPath;
import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;
import main.util.file.FileUtil;

/**
 * Fix capitalization.
 * 
 * @author budi
 */
public class FixCapitalization {

    /**
     * A string preceding any token in this set should be capitalized.
     */
    private static final Set<Token> PRECEDING_SHOULD_CAPITALIZE;

    /**
     * Set of all proper nouns (should not be lowercase).
     */
    private static final Set<String> PROPER_NOUNS;

    /**
     * Set of all potential proper nouns (should not be set to lowercase).
     */
    private static final Set<String> POTENTIAL_PROPER_NOUNS;

    /**
     * Set of all words that must be all uppercase (should not be set to
     * lowercase).
     */
    private static final Set<String> ALL_UPPERCASE;

    /**
     * Set of all potential words that must be all uppercase (should not be set
     * to lowercase).
     */
    private static final Set<String> POTENTIAL_ALL_UPPERCASE;

    /**
     * Set of all titles.
     */
    private static final Set<String> TITLES;

    /**
     * Set of all potential titles.
     */
    private static final Set<String> POTENTIAL_TITLES;

    static {
        PRECEDING_SHOULD_CAPITALIZE = new HashSet<Token>();
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.PERIOD);
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.QUESTION_MARK);
        PRECEDING_SHOULD_CAPITALIZE.add(TokenConstants.EXCLAMATION_POINT);

        PROPER_NOUNS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(PROPER_NOUNS, DataPath.getProperNounsPath());

        POTENTIAL_PROPER_NOUNS = new HashSet<String>();
        initSet(POTENTIAL_PROPER_NOUNS, DataPath.getPotentialProperNounsPath());

        ALL_UPPERCASE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(ALL_UPPERCASE, DataPath.getAllUppercasePath());

        POTENTIAL_ALL_UPPERCASE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        initSet(POTENTIAL_ALL_UPPERCASE, DataPath.getPotentialAllUppercasePath());

        TITLES = new HashSet<String>();
        initSet(TITLES, DataPath.getTitlesPath());

        POTENTIAL_TITLES = new HashSet<String>();
        initSet(POTENTIAL_TITLES, DataPath.getPotentialTitlesPath());
    }

    /**
     * Fix capitalization.
     * 
     * @param tt {@link TokenizedText}
     * 
     * @see #fixFollowingPunctuation(TokenizedText)
     * @see #fixFollowingSpecial(TokenizedText)
     * @see #fixCorrectUpperCase(TokenizedText)
     */
    public static void fix(TokenizedText tt) {
        // TODO: first word in line optional capitalize
        fixFollowingPunctuation(tt);
        fixFollowingSpecial(tt);
        fixCorrectUpperCase(tt);
    }

    /**
     * Fix capitalization on words following punctuation.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixFollowingPunctuation(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevToken != null && PRECEDING_SHOULD_CAPITALIZE.contains(prevToken)) {
                if (currentToken.containsProperty(TokenProperty.WORD) && currentToken.startsWith(TokenProperty.LOWER)) {
                    currentToken.capitalize();
                }
            }
        }
    }

    /**
     * Fix capitalization on words following special words.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixFollowingSpecial(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        Token prevToken, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i).equals(TokenConstants.SPACE)) {
                continue;
            }
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevToken != null) {
                if (prevToken.containsProperty(TokenProperty.ABBREVIATION)) {
                    if (!currentToken.toString().equals("and")) { // Ex: "Mr. and Mrs. Smith"
                        currentToken.capitalize();
                    }
                }
            }
        }
    }

    /**
     * Fix all uppercase.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void fixCorrectUpperCase(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();

        for (Token token : tokens) {
            if (token.isAll(TokenProperty.UPPER) && !token.containsProperty(TokenProperty.ACRONYM)) {
                if (token.length() > 1 && !ALL_UPPERCASE.contains(token.toString())
                        && !POTENTIAL_ALL_UPPERCASE.contains(token.toString())) {
                    token.modifyToLowerCase();
                }
            } else {
                if (ALL_UPPERCASE.contains(token.toString())) {
                    token.modifyToUpperCase();
                }
            }
        }
    }

    /**
     * Initialize set with data from path.
     * 
     * @param set set to initialize
     * @param path path to file containing data
     */
    private static void initSet(Set<String> set, String path) {
        List<String> list = null;
        try {
            list = FileUtil.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            set.addAll(list);
        }
    }

}
