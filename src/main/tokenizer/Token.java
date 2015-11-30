package main.tokenizer;

import java.util.Collection;
import java.util.Set;

/**
 * Token.
 * 
 * @author budi
 */
public class Token {

    /**
     * Token string builder.
     */
    public final StringBuilder tokenStringBuilder;

    /**
     * Token properties.
     */
    private final TokenProperties tokenProperties;

    /**
     * Class constructor with string and {@link TokenProperties}.
     * 
     * @param string string
     * @param tokenProperties {@link TokenProperties}
     */
    public Token(String string, TokenProperties tokenProperties) {
        this.tokenStringBuilder = new StringBuilder();
        this.tokenStringBuilder.append(string);
        this.tokenProperties = tokenProperties;
    }

    /**
     * Class constructor, no arguments.
     */
    public Token() {
        this("", new TokenProperties());
    }

    /**
     * Class constructor with string and one property.
     * 
     * @param string string
     * @param property property
     */
    public Token(String string, TokenProperty property) {
        this(string, new TokenProperties());
        tokenProperties.addProperty(property);
        tokenProperties.setAll(property);
        tokenProperties.setStartsWith(property);
    }

    /**
     * Class constructor with string and a collection of properties.
     * 
     * @param string string
     * @param properties properties
     */
    public Token(String string, Collection<TokenProperty> properties) {
        this(string, new TokenProperties());
        tokenProperties.addProperties(properties);
    }

    /**
     * Append character to the end of {@link #tokenStringBuilder}.
     * 
     * @param c character to append
     */
    public void append(char c) {
        tokenStringBuilder.append(c);
    }

    /**
     * Append string to the end of {@link #tokenStringBuilder}.
     * 
     * @param s string to append
     */
    public void append(String s) {
        tokenStringBuilder.append(s);
    }

    /**
     * Determine if token is empty.
     * 
     * @return True if token is empty, otherwise false.
     */
    public boolean isEmpty() {
        return tokenStringBuilder.length() == 0;
    }

    /**
     * Update properties.
     */
    public void updateProperties() {
        if (containsProperty(TokenProperty.LOWER) || containsProperty(TokenProperty.UPPER)) {
            if (containsProperty(TokenProperty.DIGIT)) {
                addProperty(TokenProperty.ALPHANUMERIC);
            } else {
                addProperty(TokenProperty.WORD);
            }
        } else {
            addProperty(TokenProperty.NUMBER);
        }
    }

    /**
     * Capitalize token.
     */
    public void capitalize() {
        if (startsWith(TokenProperty.LOWER) && containsProperty(TokenProperty.WORD)) {
            tokenStringBuilder.setCharAt(0, Character.toUpperCase(tokenStringBuilder.charAt(0)));
            setStartsWith(TokenProperty.UPPER);
            if (isAll(TokenProperty.LOWER)) {
                setAll(null);
            }
            addProperty(TokenProperty.UPPER);
        }
    }

    /**
     * Make token all lowercase.
     */
    public void modifyToLowerCase() {
        if (!isAll(TokenProperty.LOWER) && containsProperty(TokenProperty.WORD)) {
            for (int i = 0; i < tokenStringBuilder.length(); ++i) {
                tokenStringBuilder.setCharAt(i, Character.toLowerCase(tokenStringBuilder.charAt(i)));
            }

            setAll(TokenProperty.LOWER);
            setStartsWith(TokenProperty.LOWER);

            addProperty(TokenProperty.LOWER);
            removeProperty(TokenProperty.UPPER);
        }
    }

    /**
     * Make token all uppercase.
     */
    public void modifyToUpperCase() {
        if (!isAll(TokenProperty.UPPER) && containsProperty(TokenProperty.WORD)) {
            for (int i = 0; i < tokenStringBuilder.length(); ++i) {
                tokenStringBuilder.setCharAt(i, Character.toUpperCase(tokenStringBuilder.charAt(i)));
            }

            setAll(TokenProperty.UPPER);
            setStartsWith(TokenProperty.UPPER);

            addProperty(TokenProperty.UPPER);
            removeProperty(TokenProperty.LOWER);
        }
    }

    /**
     * Add property.
     * 
     * @param property property
     * 
     * @see TokenProperties#addProperty(TokenProperty)
     */
    public void addProperty(TokenProperty property) {
        tokenProperties.addProperty(property);
    }

    /**
     * Add properties.
     * 
     * @param properties properties to add
     * 
     * @see TokenProperties#addProperties(Collection)
     */
    public void addProperties(Collection<TokenProperty> properties) {
        tokenProperties.addProperties(properties);
    }

    /**
     * Remove property.
     * 
     * @param property property to remove
     * 
     * @see TokenProperties#removeProperty(TokenProperty)
     */
    public void removeProperty(TokenProperty property) {
        tokenProperties.removeProperty(property);
    }

    /**
     * Remove properties.
     * 
     * @param properties properties to remove
     * 
     * @see TokenProperties#removeProperties(Collection)
     */
    public void removeProperty(Collection<TokenProperty> properties) {
        tokenProperties.removeProperties(properties);
    }

    /**
     * Determine if token contains property.
     * 
     * @param property property to check
     * @return True if token contains property, otherwise false.
     * 
     * @see TokenProperties#containsProperty(TokenProperty)
     */
    public boolean containsProperty(TokenProperty property) {
        return tokenProperties.containsProperty(property);
    }

    /**
     * Determine if token has properties.
     * 
     * @return True if token has properties, otherwise false.
     * 
     * @see TokenProperties#isEmpty()
     */
    public boolean hasProperties() {
        return !tokenProperties.isEmpty();
    }

    /**
     * Determine if token contains only this property.
     * 
     * @param property property to check
     * @return True if token only contains property, otherwise false.
     * 
     * @see TokenProperties#isAll(TokenProperty)
     */
    public boolean isAll(TokenProperty property) {
        return tokenProperties.isAll(property);
    }

    /**
     * Determine if token contains starts with this property.
     * 
     * @param property property to check
     * @return True if token starts with property, otherwise false.
     * 
     * @see TokenProperties#startsWith(TokenProperty)
     */
    public boolean startsWith(TokenProperty property) {
        return tokenProperties.startsWith(property);
    }

    /**
     * Get length of token.
     * 
     * @return length of token
     */
    public int length() {
        return tokenStringBuilder.length();
    }

    /**
     * Clear token.
     */
    public void clear() {
        tokenStringBuilder.setLength(0);
        tokenProperties.clear();
    }

    /**
     * Debug string.
     * 
     * @return debug string
     */
    public String debugString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append(toString());
        builder.append(')');

        builder.append('\t');

        builder.append(tokenProperties.toString());

        return builder.toString();
    }

    /**
     * Equals ignore case.
     * 
     * @param anotherString string to compare
     * @return True if strings are equal (case sensitive), otherwise false.
     * 
     * @see java.lang.String#equalsIgnoreCase
     */
    public boolean equalsIgnoreCase(String anotherString) {
        return toString().equalsIgnoreCase(anotherString);
    }

    /**
     * Ends with.
     * 
     * @param suffix suffix to check
     * @return True if token ends with suffix, otherwise false.
     * 
     * @see java.lang.String#endsWith
     */
    public boolean endsWith(String suffix) {
        return toString().endsWith(suffix);
    }

    @Override
    public String toString() {
        return tokenStringBuilder.toString();
    }

    /**
     * @return {@link #tokenStringBuilder}
     */
    public StringBuilder getTokenStringBuilder() {
        return tokenStringBuilder;
    }

    /**
     * @return {@link #tokenProperties}
     */
    public TokenProperties getTokenProperties() {
        return tokenProperties;
    }

    /**
     * @return properties
     * 
     * @see TokenProperties#getProperties()
     */
    public Set<TokenProperty> getProperties() {
        return tokenProperties.getProperties();
    }

    /**
     * @return all
     * 
     * @see TokenProperties#getAll()
     */
    public TokenProperty getAll() {
        return tokenProperties.getAll();
    }

    /**
     * @param all property to set
     * 
     * @see TokenProperties#setAll(TokenProperty)
     */
    public void setAll(TokenProperty all) {
        tokenProperties.setAll(all);
    }

    /**
     * @return {@link #startsWith}
     * 
     * @see TokenProperties#getStartsWith()
     */
    public TokenProperty getStartsWith() {
        return tokenProperties.getStartsWith();
    }

    /**
     * @param startsWith property to set
     * 
     * @see TokenProperties#setStartsWith(TokenProperty)
     */
    public void setStartsWith(TokenProperty startsWith) {
        tokenProperties.setStartsWith(startsWith);
    }

}
