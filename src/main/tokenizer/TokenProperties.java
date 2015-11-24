package main.tokenizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Token properties container.
 * 
 * @author budi
 */
public class TokenProperties {

    /**
     * Set of all {@link TokenProperty}.
     */
    public final Set<TokenProperty> properties;

    /**
     * Denotes the {@link TokenProperty} that the token only contains.
     */
    public TokenProperty all;

    /**
     * Denotes the {@link TokenProperty} that the token starts with.
     */
    public TokenProperty startsWith;

    /**
     * Class constructor.
     */
    public TokenProperties() {
        this.properties = new HashSet<TokenProperty>();
    }

    /**
     * Class constructor with arguments.
     * 
     * @param properties properties
     * @param all all
     * @param startsWith starts with
     */
    public TokenProperties(Collection<TokenProperty> properties, TokenProperty all, TokenProperty startsWith) {
        this();
        this.properties.addAll(properties);
        this.all = all;
        this.startsWith = startsWith;
    }

    /**
     * Analyze properties.
     */
    public void analyzeProperties() {
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
     * Merge two token properties together.
     * 
     * @param t1 Token properties 1. This is the token's properties that will be
     *            at the beginning of the new token.
     * @param t2 Token properties 2. This is the token's properties that will be
     *            appended to the first token.
     * @return merged properties
     */
    public static TokenProperties mergeTokenProperties(TokenProperties t1, TokenProperties t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }

        Set<TokenProperty> properties = new HashSet<TokenProperty>();

        properties.addAll(t1.getProperties());
        properties.addAll(t2.getProperties());

        TokenProperty all = null;
        TokenProperty t1All = t1.getAll();
        TokenProperty t2All = t2.getAll();

        if (t1All != null && t2 != null && t1All.equals(t2All)) {
            all = t1All;
        }

        return new TokenProperties(properties, all, t1.getStartsWith());
    }

    /**
     * Merge two token properties together.
     * 
     * @param t1 Token properties 1. This is the token's properties that will be
     *            at the beginning of the new token.
     * @param t2 Token 2. This is the token's properties that will be appended
     *            to the first token.
     * @return merged properties
     */
    public static TokenProperties mergeTokenProperties(TokenProperties t1, Token t2) {
        return mergeTokenProperties(t1, t2.getTokenProperties());
    }

    /**
     * Merge two token properties together.
     * 
     * @param t1 Token 1. This is the token's properties that will be at the
     *            beginning of the new token.
     * @param t2 Token properties 2. This is the token's properties that will be
     *            appended to the first token.
     * @return merged properties
     */
    public static TokenProperties mergeTokenProperties(Token t1, TokenProperties t2) {
        return mergeTokenProperties(t1.getTokenProperties(), t2);
    }

    /**
     * Merge two token properties together.
     * 
     * @param t1 Token 1. This is the token's properties that will be at the
     *            beginning of the new token.
     * @param t2 Token 2. This is the token's properties that will be appended
     *            to the first token.
     * @return merged properties
     */
    public static TokenProperties mergeTokenProperties(Token t1, Token t2) {
        return mergeTokenProperties(t1.getTokenProperties(), t2.getTokenProperties());
    }

    /**
     * Copy token properties.
     * 
     * @param token token to copy
     * @return copied token properties
     */
    public static TokenProperties copyTokenProperties(Token token) {
        return mergeTokenProperties(token.getTokenProperties(), new Token());
    }

    /**
     * Add {@link TokenProperty} to {@link #properties}.
     * 
     * @param property property to add
     */
    public void addProperty(TokenProperty property) {
        properties.add(property);
    }

    /**
     * Add {@link TokenProperty}s to {@link #properties}.
     * 
     * @param properties properties to add
     */
    public void addProperties(Collection<TokenProperty> properties) {
        properties.addAll(properties);
    }

    /**
     * Remove property.
     * 
     * @param property property to remove
     */
    public void removeProperty(TokenProperty property) {
        properties.remove(property);
    }

    /**
     * Remove properties.
     * 
     * @param properties properties to remove
     */
    public void removeProperties(Collection<TokenProperty> properties) {
        properties.removeAll(properties);
    }

    /**
     * Determine if {@link #properties} contains property.
     * 
     * @param property property to check
     * @return True if {@link #properties} contains property, otherwise false.
     */
    public boolean containsProperty(TokenProperty property) {
        return properties.contains(property);
    }

    /**
     * Determine if {@link #properties} is empty.
     * 
     * @return True if token is empty, otherwise false.
     */
    public boolean isEmpty() {
        return !properties.isEmpty();
    }

    /**
     * Determine if token contains only this property.
     * 
     * @param property property to check
     * @return True if token only contains property, otherwise false.
     */
    public boolean isAll(TokenProperty property) {
        return all != null && all.equals(property);
    }

    /**
     * Determine if token contains starts with this property.
     * 
     * @param property property to check
     * @return True if token starts with property, otherwise false.
     */
    public boolean startsWith(TokenProperty property) {
        return startsWith != null && startsWith.equals(property);
    }

    /**
     * Clear token properties.
     */
    public void clear() {
        properties.clear();
        all = null;
        startsWith = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        builder.append("StartsWith: [");
        builder.append(startsWith);
        builder.append(']');

        builder.append('\t');

        builder.append("All: [");
        builder.append(all);
        builder.append(']');

        builder.append('\t');

        builder.append("Properties: ");
        builder.append(properties);
        builder.append(']');

        return builder.toString();
    }

    /**
     * @return {@link #properties}
     */
    public Set<TokenProperty> getProperties() {
        return properties;
    }

    /**
     * @return {@link #all}
     */
    public TokenProperty getAll() {
        return all;
    }

    /**
     * @param all property to set
     */
    public void setAll(TokenProperty all) {
        this.all = all;
    }

    /**
     * @return {@link #startsWith}
     */
    public TokenProperty getStartsWith() {
        return startsWith;
    }

    /**
     * @param startsWith property to set
     */
    public void setStartsWith(TokenProperty startsWith) {
        this.startsWith = startsWith;
    }

}
