package test;

import test.subtitle.BaseSubtitleTest;

/**
 * Class container.
 * 
 * This is used to extract the name of the class.
 * 
 * @author budi
 */
public class ClassContainer {

    /**
     * Class object.
     */
    private final Class<? extends BaseSubtitleTest> clazz;

    /**
     * Class constructor.
     * 
     * @param clazz class object
     */
    public ClassContainer(Class<? extends BaseSubtitleTest> clazz) {
        this.clazz = clazz;
    }

    /**
     * @return {@link #clazz}
     */
    public Class<? extends BaseSubtitleTest> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        String string = clazz.toString();
        return string.substring(string.lastIndexOf('.') + 1, string.length());
    }

}
