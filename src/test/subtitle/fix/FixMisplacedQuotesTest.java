package test.subtitle.fix;

import java.util.Collection;

import main.subtitle.fix.FixMisplacedQuotes;
import test.ParameterizedCollection;
import test.subtitle.BaseSubtitleTest;

/**
 * {@link FixMisplacedQuotes} test.
 * 
 * @author budi
 */
public class FixMisplacedQuotesTest extends BaseSubtitleTest {

    /**
     * {@link FixMisplacedQuotes} test collection.
     * 
     * @return test collection
     */
    public static Collection<Object[]> testCollection() {
        ParameterizedCollection collection = new ParameterizedCollection(FixMisplacedQuotesTest.class);

        collection.add("I'm just playin'", "I'm just playin'");

        collection.add("Big ol' Al.", "Big ol' Al.");
        collection.add("Big ol 'Al.", "Big ol' Al.");

        collection.add("But I'm talking 'bout the guys that lived.", "But I'm talking 'bout the guys that lived.");
        collection.add("But I'm talking' bout the guys that lived.", "But I'm talking 'bout the guys that lived.");
        collection.add("But I'm talking ' bout the guys that lived.", "But I'm talking 'bout the guys that lived.");

        collection.add("Go get 'em", "Go get 'em");
        collection.add("Go get 'em'", "Go get 'em'");
        collection.add("Get 'em, get 'em.", "Get 'em, get 'em.");
        collection.add("Get 'em, get 'em. Go!", "Get 'em, get 'em. Go!");

        //collection.add("It's \"its\" not \"it's\".", "It's \"its\" not \"it's\"."); // TODO: fix 4 double quotes

        collection.add("Kiss 'em");
        collection.add("Kiss' em", "Kiss 'em");

        collection.add("an' I don't care");
        collection.add("maitre d'");

        collection.add("'94", "'94");
        collection.add("My '50 Chevy Impala", "My '50 Chevy Impala");
        collection.add("Why '98?", "Why '98?");
        collection.add("'20s", "'20s");
        collection.add("The '60s was the best", "The '60s was the best");
        collection.add("In the '90s", "In the '90s");

        collection.add("5 o'clock", "5 o'clock");
        collection.add("5 o 'clock", "5 o'clock");
        collection.add("5 o' clock", "5 o'clock");
        collection.add("5 o ' clock", "5 o'clock");

        collection.add("You're not just sayin' it 'cause", "You're not just sayin' it 'cause");
        collection.add("'Cause I'm not.", "'Cause I'm not.");

        collection.add("I'm just playin'\nNot really.", "I'm just playin'\nNot really.");

        collection.add("officers' mess,");
        collection.add("Edmunds' planet.");
        collection.add("You saw that move comin'? That's correct.", "You saw that move comin'? That's correct.");
        collection.add("I learned that back in 'Nam in '68.", "I learned that back in 'Nam in '68.");

        collection.add("\"Oh, you need a donkey kickin '.\"", "\"Oh, you need a donkey kickin'.\"");

        collection.add("D'Amico");
        collection.add("D'Artagnan");
        collection.add("chasin' D'Artagnan");
        collection.add("What d'you wanna drink?");

        collection.add("O'Connor", "O'Connor");
        collection.add("and Tom O'Leary waiting for you.", "and Tom O'Leary waiting for you.");
        collection.add("and Tom O 'Leary waiting for you.", "and Tom O'Leary waiting for you.");
        collection.add("like Eugene O'Neill long,", "like Eugene O'Neill long,");
        collection.add("like Eugene O 'Neill long,", "like Eugene O'Neill long,");

        collection.add("Why are you gettin' all bent outta shape", "Why are you gettin' all bent outta shape");

        return collection.getCollection();
    }

}
