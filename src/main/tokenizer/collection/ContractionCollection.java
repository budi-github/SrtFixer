package main.tokenizer.collection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Contraction collection.
 * 
 * @author budi
 */
public class ContractionCollection {

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

    /**
     * Strings that should have quote appended to.
     */
    public static final Set<String> APPEND_QUOTE;

    /**
     * Strings that should have quote prepended to.
     */
    public static final Set<String> PREPEND_QUOTE;

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

        APPEND_QUOTE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        APPEND_QUOTE.add("an");
        APPEND_QUOTE.add("d");
        APPEND_QUOTE.add("ol");
        APPEND_QUOTE.add("po");
        APPEND_QUOTE.add("sho");

        PREPEND_QUOTE = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        PREPEND_QUOTE.add("bout");
        PREPEND_QUOTE.add("cause");
        PREPEND_QUOTE.add("clock");
        PREPEND_QUOTE.add("em");
        PREPEND_QUOTE.add("im");
        PREPEND_QUOTE.add("Nam");
    }

}
