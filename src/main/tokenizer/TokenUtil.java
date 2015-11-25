package main.tokenizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Token utilities.
 * 
 * @author budi
 */
public class TokenUtil {

    /**
     * Get tokens indices of tokens to be removed, starting at from a specified
     * index up to a specified token.
     * 
     * @param tokens list of tokens
     * @param startIndex starting index
     * @param removeToken token to remove
     * @param lastToken look backwards up until this token
     * @return set of indices to remove
     */
    public static Collection<Integer> lookBackAndRemove(List<Token> tokens, int startIndex, Token removeToken,
            Token lastToken) {
        Set<Integer> removeIndexSet = new HashSet<Integer>();
        int remove = startIndex - 1; // assumes token at startIndex is not equal to removeToken
        Token lookBackToken;

        do {
            lookBackToken = tokens.get(remove);
            if (lookBackToken.equals(removeToken)) {
                removeIndexSet.add(remove);
            }
            --remove;
        } while (remove >= 0 && !lookBackToken.equals(lastToken));

        return removeIndexSet;
    }

}
