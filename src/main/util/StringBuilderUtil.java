package main.util;

/**
 * {@link StringBuilder} utilities.
 * 
 * @author budi
 */
public class StringBuilderUtil {

    /**
     * Delete space at index within StringBuilder
     * 
     * @param builder StringBuilder object
     * @param index position of space within StringBuilder
     */
    public static void deleteSpaceAt(StringBuilder builder, int index) {
        assert builder.charAt(index) == ' ';
        builder.deleteCharAt(index);
    }

    /**
     * Delete only if space at index within StringBuilder
     * 
     * @param builder StringBuilder object
     * @param index position of space within StringBuilder
     */
    public static void deleteOnlyIfSpaceAt(StringBuilder builder, int index) {
        if (builder.charAt(index) == ' ') {
            builder.deleteCharAt(index);
        }
    }

    /**
     * Capitalize character at index
     * 
     * @param builder StringBuilder object
     * @param index position within StringBuilder
     */
    public static void capitalizeAt(StringBuilder builder, int index) {
        char c = builder.charAt(index);
        char capitalized = Character.toUpperCase(c);
        if (c != capitalized) {
            builder.setCharAt(index, capitalized);
        }
    }

}
