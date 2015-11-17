package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixAcronym;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixAcronym} test.
 * 
 * @author budi
 */
public class FixAcronymTest extends BaseSubtitleTest {

    /**
     * {@link FixAcronym} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixAbbreviationsTest.class);

        collection.add(" A .M .", "A.M.");
        collection.add("P.M .", "P.M.");

        collection.add("3:00 A.M.");
        collection.add("3:00 A. M.", "3:00 A.M.");
        collection.add("3:00 a.m.", "3:00 A.M.");
        collection.add("3:00 a. m.", "3:00 A.M.");

        collection.add("5:00 p. m.,", "5:00 P.M.,");
        collection.add("Tomorrow, 8:59 a.m., sharp.", "Tomorrow, 8:59 A.M., sharp.");
        collection.add("8:23 p.m...", "8:23 P.M...");

        collection.add("I'm gonna go to the D.A.,");

        collection.add("if I told you I had an I. V.", "if I told you I had an I.V.");

        collection.add("like i.e., this or that");
        collection.add("like I.e., this or that", "like i.e., this or that");
        collection.add("like i.E., this or that", "like i.e., this or that");
        collection.add("like I.E., this or that", "like i.e., this or that");

        collection.add("AWOL U.S. Navy.");
        collection.add("Shame on us");
        collection.add("Why don't you come down to the V.A.");

        collection.add("A.k.a. Bobby T, a.k.a. Bobby B, a.k.a.", "A.K.A. Bobby T, A.K.A. Bobby B, A.K.A.");
        collection.add("- Negative. You know the R.O.E. Your call.");

        collection.add("Technically, I'm a thug for S.H.I.E.L.D.");
        collection.add("Technically, I'm a thug for S. H. I. E. L. D.", "Technically, I'm a thug for S.H.I.E.L.D.");
        collection.add("Technically, I'm a thug for S. H. I.E. L. D.", "Technically, I'm a thug for S.H.I.E.L.D.");
        collection.add("Technically, I'm a thug for S. H. i.e. L. D.", "Technically, I'm a thug for S.H.I.E.L.D.");

        return collection.getCollection();
    }

}
