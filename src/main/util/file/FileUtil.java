package main.util.file;

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

/**
 * General utilities.
 * 
 * @author budi
 */
public class FileUtil {

    /**
     * Copy file at oldPath to newPath.
     * 
     * If a file already exists at newPath, file at oldPath will NOT be copied.
     * 
     * @param oldPath Old path to file. This file at this path will be copied.
     * @param newPath New path to file. This will be the location of the new
     *            copied file.
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
     * @param file file to check
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
     * Ensures file is in UTF-8.
     * 
     * @param path path of file
     * @return List of lines split by '\n' character.
     * @throws IOException If a non UTF-8 character is found, this exception is
     *             thrown.
     */
    public static List<String> readAllLines(String path) throws IOException {
        List<String> lines = new ArrayList<String>();

        CharsetEncoder encoder = Charset.forName(StandardCharsets.ISO_8859_1.toString()).newEncoder();
        boolean ignoreBOM = true; // https://en.wikipedia.org/wiki/Byte_order_mark
        BufferedReader br = null;
        String currentLine;
        int lineNumber = 1;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while ((currentLine = br.readLine()) != null) {
                if (!encoder.canEncode(currentLine) && !ignoreBOM) { // ensure line is UTF-8
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
