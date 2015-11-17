package main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * General utilities.
 * 
 * @author budi
 */
public class Util {

    /**
     * List of month names.
     */
    private static final Set<String> MONTHS;

    static {
        MONTHS = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        MONTHS.add("january");
        MONTHS.add("february");
        MONTHS.add("march");
        MONTHS.add("april");
        MONTHS.add("may");
        MONTHS.add("june");
        MONTHS.add("july");
        MONTHS.add("august");
        MONTHS.add("september");
        MONTHS.add("october");
        MONTHS.add("november");
        MONTHS.add("december");
    }

    /**
     * Determines if string is a month.
     * 
     * @param string string to check
     * @return True if string is month, otherwise false.
     */
    public static boolean isMonth(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        return MONTHS.contains(string);
    }

    /**
     * Copy file at oldPath to newPath.
     * 
     * @param oldPath Old path to file. This file at this path will be copied.
     * @param newPath New path to file. This is the location of the copied file.
     * @throws IOException Occurs if any exceptions are thrown while reading the
     *             file or writing to the file.
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (newFile.exists()) {
            return;
        }

        System.out.println(String.format("Copying file at %s to %s", oldPath, newPath));

        inStream = new FileInputStream(oldFile);
        outStream = new FileOutputStream(newFile);

        byte[] buffer = new byte[1024];

        int length;
        while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        inStream.close();
        outStream.close();
    }

    /**
     * Get file extension of a specific file.
     * 
     * @param file file
     * @return extension of file
     */
    public static String getFileExtension(File file) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        if (index > -1) {
            return name.substring(index + 1);
        } else {
            return null;
        }
    }

    /**
     * Read file at path. Returns list of lines split by '\n' character.
     * 
     * @param path path of file
     * @return List of lines split by '\n' character.
     * @throws IOException If a non UTF-8 character is found, this exception is
     *             thrown.
     */
    public static List<String> readAllLines(String path) throws IOException {
        List<String> lines = new ArrayList<String>();

        CharsetEncoder encoder = Charset.forName(StandardCharsets.ISO_8859_1.toString()).newEncoder();
        boolean ignoreBOM = true;
        BufferedReader br = null;
        String currentLine;
        int lineNumber = 1;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while ((currentLine = br.readLine()) != null) {
                if (!encoder.canEncode(currentLine) && !ignoreBOM) {
                    throw new IOException(String.format("Line %d: \"%s\" not UTF-8", lineNumber, currentLine));
                }
                lines.add(currentLine.trim());
                ignoreBOM = false;
                ++lineNumber;
            }
        } finally {
            br.close();
        }

        return lines;
    }

}
