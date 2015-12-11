package main.data.cleaner;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.data.DataPath;
import main.util.file.FileUtil;

/**
 * Data cleaner.
 * <p>
 * Rewrite data files, sorted alphabetically and ensuring no duplicates.
 * 
 * @author budi
 */
public class DataCleaner {

    /**
     * Clean all data files.
     * 
     * @throws IOException
     */
    public static void cleanAll() throws IOException {
        cleanFile(DataPath.getAllUppercasePath());
        cleanFile(DataPath.getPotentialAllUppercasePath());
        cleanFile(DataPath.getPotentialProperNounsPath());
        cleanFile(DataPath.getProperNounsPath());
        cleanFile(DataPath.getTitlesPath());
        cleanFile(DataPath.getPotentialTitlesPath());
    }

    /**
     * Clean file at path.
     * 
     * Will rewrite contents in file, sorted alphabetically and ensuring no
     * duplicates.
     * 
     * @param path path to file
     * @throws IOException
     */
    private static void cleanFile(String path) throws IOException {
        List<String> list = FileUtil.readAllLines(path);
        Collections.sort(list);

        Set<String> set = new TreeSet<String>();
        set.addAll(list);

        writeSetToFile(path, set);
    }

    /**
     * Write set to file, each element in its own line.
     * 
     * @param path path to file
     * @param set set to write
     * @throws IOException
     */
    private static void writeSetToFile(String path, Set<String> set) throws IOException {
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            boolean first = true;
            for (String s : set) {
                if (!first) {
                    out.write('\n');
                } else {
                    first = false;
                }
                out.write(s);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
