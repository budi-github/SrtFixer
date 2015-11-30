package main.tokenizer.collection;

import java.util.Set;
import java.util.TreeSet;

/**
 * Abbreviation collection.
 * 
 * @author budi
 */
public class AbbreviationCollection {

    /**
     * Set of all abbreviations.
     */
    public static final Set<String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.add("dr");
        ABBREVIATIONS.add("mr");
        ABBREVIATIONS.add("mrs");
        ABBREVIATIONS.add("ms");
    }

}
