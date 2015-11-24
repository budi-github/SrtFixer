package main.data;

/**
 * Class containing paths for all data.
 * 
 * @author budi
 */
public class DataPath {

    /**
     * Data directory.
     */
    private static final String DATA_DIRECTORY = "data/";

    /**
     * Path to file containing all proper nouns.
     */
    private static final String PROPER_NOUNS_PATH = DATA_DIRECTORY + "ProperNouns.txt";

    /**
     * Path to file containing all potential proper nouns.
     */
    private static final String POTENTIAL_PROPER_NOUNS_PATH = DATA_DIRECTORY + "PotentialProperNouns.txt";

    /**
     * Path to file containing words that are all uppercase.
     */
    private static final String ALL_UPPERCASE_PATH = DATA_DIRECTORY + "AllUppercase.txt";

    /**
     * Path to file containing potential words that are all uppercase.
     */
    private static final String POTENTIAL_ALL_UPPERCASE_PATH = DATA_DIRECTORY + "PotentialAllUppercase.txt";

    /**
     * Path to file containing all titles.
     */
    private static final String TITLES_PATH = DATA_DIRECTORY + "Titles.txt";

    /**
     * Path to file containing all potential titles.
     */
    private static final String POTENTIAL_TITLES_PATH = DATA_DIRECTORY + "PotentialTitles.txt";

    /**
     * @return {@link #PROPER_NOUNS_PATH}
     */
    public static String getProperNounsPath() {
        return PROPER_NOUNS_PATH;
    }

    /**
     * @return {@link #POTENTIAL_PROPER_NOUNS_PATH}
     */
    public static String getPotentialProperNounsPath() {
        return POTENTIAL_PROPER_NOUNS_PATH;
    }

    /**
     * @return {@link #ALL_UPPERCASE_PATH}
     */
    public static String getAllUppercasePath() {
        return ALL_UPPERCASE_PATH;
    }

    /**
     * @return {@link #POTENTIAL_ALL_UPPERCASE_PATH}
     */
    public static String getPotentialAllUppercasePath() {
        return POTENTIAL_ALL_UPPERCASE_PATH;
    }

    /**
     * @return {@link #TITLES_PATH}
     */
    public static String getTitlesPath() {
        return TITLES_PATH;
    }

    /**
     * @return {@link #POTENTIAL_TITLES_PATH}
     */
    public static String getPotentialTitlesPath() {
        return POTENTIAL_TITLES_PATH;
    }

}
