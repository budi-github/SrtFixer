package main.util.stringBuilder;

/**
 * {@link StringBuilder} utilities.
 * 
 * @author budi
 */
public class StringBuilderUtil {

    /**
     * Delete space at index within {@link StringBuilder}.
     * 
     * @param builder {@link StringBuilder}
     * @param index position of space
     */
    public static void deleteSpaceAt(StringBuilder builder, int index) {
        assert builder.charAt(index) == ' ';
        builder.deleteCharAt(index);
    }

    /**
     * Delete at index only if the character is a space within
     * {@link StringBuilder}.
     * 
     * @param builder {@link StringBuilder}
     * @param index position of space
     */
    public static void deleteOnlyIfSpaceAt(StringBuilder builder, int index) {
        if (builder.charAt(index) == ' ') {
            builder.deleteCharAt(index);
        }
    }

    /**
     * Capitalize character at index.
     * 
     * @param builder {@link StringBuilder}
     * @param index position of character to be capitalized.
     */
    public static void capitalizeAt(StringBuilder builder, int index) {
        char c = builder.charAt(index);
        char capitalized = Character.toUpperCase(c);
        if (c != capitalized) {
            builder.setCharAt(index, capitalized);
        }
    }

}
