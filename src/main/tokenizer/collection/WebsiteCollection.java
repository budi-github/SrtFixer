package main.tokenizer.collection;

import java.util.Set;
import java.util.TreeSet;

/**
 * Website collection.
 * 
 * @author budi
 */
public class WebsiteCollection {

    /**
     * Set of valid url extensions.
     */
    public static final Set<String> VALID_EXTENSIONS;

    static {
        VALID_EXTENSIONS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        VALID_EXTENSIONS.add("com");
        VALID_EXTENSIONS.add("net");
        VALID_EXTENSIONS.add("org");
    }

}
