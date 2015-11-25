package main.tokenizer.detect;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import main.tokenizer.Token;
import main.tokenizer.TokenConstants;
import main.tokenizer.TokenProperties;
import main.tokenizer.TokenProperty;
import main.tokenizer.TokenizedText;

/**
 * Detect contraction.
 * 
 * @author budi
 */
public class DetectContraction {

    /**
     * Common contraction endings.
     */
    public static final Set<String> CONTRACTIONS_ENDINGS;

    /**
     * Set of contractions.
     */
    public static final Set<String> CONTRACTIONS;

    /**
     * Map of contraction keys to index of "'" values.
     */
    public static final Map<String, Integer> CONTRACTIONS_ERROR_MAP;

    static {
        // TODO: passes if contraction is captialized, fails for "D'Artagnan"
        //CONTRACTIONS_ENDINGS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        CONTRACTIONS_ENDINGS = new HashSet<String>();
        CONTRACTIONS_ENDINGS.add("am");
        CONTRACTIONS_ENDINGS.add("all");
        CONTRACTIONS_ENDINGS.add("d");
        CONTRACTIONS_ENDINGS.add("ll");
        CONTRACTIONS_ENDINGS.add("m");
        CONTRACTIONS_ENDINGS.add("re");
        CONTRACTIONS_ENDINGS.add("t");
        CONTRACTIONS_ENDINGS.add("s");
        CONTRACTIONS_ENDINGS.add("ve");

        CONTRACTIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        CONTRACTIONS.add("can't");
        CONTRACTIONS.add("couldn't");
        CONTRACTIONS.add("don't");
        CONTRACTIONS.add("haven't");
        CONTRACTIONS.add("he's");
        CONTRACTIONS.add("how'd");
        CONTRACTIONS.add("how'll");
        CONTRACTIONS.add("how's");
        CONTRACTIONS.add("i'm");
        CONTRACTIONS.add("i've");
        CONTRACTIONS.add("it'll");
        CONTRACTIONS.add("it'd");
        CONTRACTIONS.add("ma'am");
        CONTRACTIONS.add("s'il");
        CONTRACTIONS.add("she's");
        CONTRACTIONS.add("shouldn't");
        CONTRACTIONS.add("that'd");
        CONTRACTIONS.add("that'll");
        CONTRACTIONS.add("that's");
        CONTRACTIONS.add("there's");
        CONTRACTIONS.add("they'd");
        CONTRACTIONS.add("they'll");
        CONTRACTIONS.add("they're");
        CONTRACTIONS.add("they've");
        CONTRACTIONS.add("we've");
        CONTRACTIONS.add("what'd");
        CONTRACTIONS.add("what'll");
        CONTRACTIONS.add("what're");
        CONTRACTIONS.add("what's");
        CONTRACTIONS.add("when'd");
        CONTRACTIONS.add("when'll");
        CONTRACTIONS.add("when's");
        CONTRACTIONS.add("where'd");
        CONTRACTIONS.add("where'll");
        CONTRACTIONS.add("where's");
        CONTRACTIONS.add("who'd");
        CONTRACTIONS.add("who'll");
        CONTRACTIONS.add("who's");
        CONTRACTIONS.add("why'd");
        CONTRACTIONS.add("why'll");
        CONTRACTIONS.add("why's");
        CONTRACTIONS.add("won't");
        CONTRACTIONS.add("y'all");
        CONTRACTIONS.add("you'd");
        CONTRACTIONS.add("you're");

        CONTRACTIONS_ERROR_MAP = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        for (String contraction : CONTRACTIONS) {
            CONTRACTIONS_ERROR_MAP.put(contraction.replace("\'", ""), contraction.indexOf("'"));
        }
    }

    /**
     * Detect time.
     * 
     * @param tt {@link TokenizedText}
     */
    public static void detect(TokenizedText tt) {
        List<Token> tokens = tt.getTokens();
        Set<Integer> removeIndexSet = new HashSet<Integer>();

        Token prevPrevToken, prevToken = null, currentToken = null;
        for (int i = 0; i < tokens.size(); ++i) {
            prevPrevToken = prevToken;
            prevToken = currentToken;
            currentToken = tokens.get(i);
            if (prevPrevToken != null && prevPrevToken.containsProperty(TokenProperty.WORD)) {
                if (prevToken.equals(TokenConstants.SINGLE_QUOTE)) {
                    if (currentToken.containsProperty(TokenProperty.WORD)) {
                        if (!CONTRACTIONS_ENDINGS.contains(currentToken.toString())) {
                            System.err.println("Warning!"); // TODO: print warning message
                        }
                        removeIndexSet.add(i - 1);
                        removeIndexSet.add(i);
                        TokenProperties properties = TokenProperties.mergeTokenProperties(prevPrevToken, currentToken);
                        properties.addProperty(TokenProperty.CONTRACTION);

                        StringBuilder builder = new StringBuilder();
                        builder.append(prevPrevToken);
                        builder.append(prevToken);
                        builder.append(currentToken);

                        tokens.set(i - 2, new Token(builder.toString(), properties));
                    }
                }
            }
        }

        tt.removeIndices(removeIndexSet);
    }

}
