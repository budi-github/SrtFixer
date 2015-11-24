package main.data.cleaner;

import java.io.IOException;

/**
 * SrtFixer console application.
 * 
 * @author budi
 */
public class DataCleanerConsole {

    /**
     * Main function. Launch the console application.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DataCleaner.cleanAll();
    }

}
