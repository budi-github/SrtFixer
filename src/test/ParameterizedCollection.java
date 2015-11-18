package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import test.subtitle.BaseSubtitleTest;

/**
 * Parameterized tests collection.
 * <p>
 * This class abstracts away the otherwise complex steps to add a test to a
 * collection.
 * 
 * @author budi
 */
public class ParameterizedCollection {

    /**
     * Collection of tests.
     */
    private Collection<Object[]> collection;

    /**
     * Describes which {@link BaseSubtitleTest} class these tests come from.
     * <p>
     * This value is used to exclude certain tests.
     */
    private final ClassContainer classContainer;

    /**
     * @param clazz Describes which {@link BaseSubtitleTest} class these tests
     *            come from.
     */
    public ParameterizedCollection(Class<? extends BaseSubtitleTest> clazz) {
        this.collection = new ArrayList<Object[]>();
        this.classContainer = new ClassContainer(clazz);
    }

    /**
     * Add test to collection.
     * 
     * @param actual actual value
     * @param expected expected value
     */
    public void add(String actual, String expected) {
        add(actual, expected, TestProperty.ALL);
    }

    /**
     * Add test to collection with {@link TestProperty}.
     * 
     * @param actual actual value
     * @param expected expected value
     * @param property {@link TestProperty} value
     */
    public void add(String actual, String expected, TestProperty property) {
        add(actual, expected, new TestProperty[] { property });
    }

    /**
     * Add test to collection with list of {@link TestProperty}.
     * 
     * @param actual actual value
     * @param expected expected value
     * @param properties list of {@link TestProperty} values
     */
    public void add(String actual, String expected, TestProperty[] properties) {
        collection.add(new Object[] { expected.replace("\n", "\\n"), actual.replace("\n", "\\n"),
                new ArrayList<TestProperty>(Arrays.asList(properties)), classContainer });
    }

    /**
     * Add test to collection where actual equals expected.
     * 
     * @param actual actual value
     */
    public void add(String actual) {
        add(actual, actual);
    }

    /**
     * Add test to collection with {@link TestProperty} where actual equals
     * expected.
     * 
     * @param actual actual value
     * @param property {@link TestProperty} value
     */
    public void add(String actual, TestProperty property) {
        add(actual, actual, property);
    }

    /**
     * Add test to collection with list of {@link TestProperty} where actual
     * equals expected.
     * 
     * @param actual actual value
     * @param properties list of {@link TestProperty} values
     */
    public void add(String actual, TestProperty[] properties) {
        add(actual, actual, properties);
    }

    /**
     * @return {@link #collection}
     */
    public Collection<Object[]> getCollection() {
        return collection;
    }

}
