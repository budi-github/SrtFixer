package main.tokenizer.collection;

import java.util.Set;
import java.util.TreeSet;

/**
 * Acronym collection.
 * 
 * @author budi
 */
public class AcronymCollection {

    /**
     * Set of acronyms that should be all lowercase.
     */
    public static final Set<String> LOWERCASE_ACRONYMS;

    static {
        LOWERCASE_ACRONYMS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        LOWERCASE_ACRONYMS.add("i.e.");
    }

}
