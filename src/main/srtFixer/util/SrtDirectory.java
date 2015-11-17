package main.srtFixer.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import main.util.Util;

/**
 * Directory containing srt file.
 * 
 * @author budi
 */
public class SrtDirectory {

    /**
     * Valid media file extensions.
     */
    private final static Set<String> MEDIA_FILE_EXTENSIONS;

    static {
        MEDIA_FILE_EXTENSIONS = new HashSet<String>();
        MEDIA_FILE_EXTENSIONS.add("avi");
        MEDIA_FILE_EXTENSIONS.add("flv");
        MEDIA_FILE_EXTENSIONS.add("mkv");
        MEDIA_FILE_EXTENSIONS.add("mp4");
    }

    /**
     * Path to directory.
     */
    private String path;

    /**
     * List of files in directory.
     */
    private File[] fileArray;

    /**
     * File containing media.
     */
    private File mediaFile;

    /**
     * Path containing new srt file.
     */
    private String newSrtPath;

    /**
     * Path containing original srt file.
     */
    private File originalSrtFile;

    /**
     * Flag to determine if original srt file is the only srt file in the
     * directory.
     */
    private boolean onlyContainsOriginalSrt;

    /**
     * Class constructor.
     * 
     * @param path path to directory
     * @throws IOException only thrown if path is not directory
     */
    public SrtDirectory(String path) throws IOException {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IOException(String.format("%s is not directory", path));
        }

        this.path = path;
        this.fileArray = directory.listFiles();
        analyze();
    }

    /**
     * Analyze directory contents.
     * 
     * @throws IOException
     */
    public void analyze() throws IOException {
        findMediaFile();
        findNewSrtPath();
        findOriginalSrtFile();
    }

    /**
     * Find media file in directory.
     */
    public void findMediaFile() {
        for (File f : fileArray) {
            String extension = Util.getFileExtension(f);
            if (MEDIA_FILE_EXTENSIONS.contains(extension)) {
                mediaFile = f;
                break;
            }
        }
    }

    /**
     * Find new srt file path in directory.
     */
    public void findNewSrtPath() {
        String srtPath = mediaFile.getPath();
        newSrtPath = srtPath.substring(0, srtPath.lastIndexOf('.')) + ".srt";
    }

    /**
     * Find original srt file in directory.
     * 
     * Original srt will first default to an srt file that is NOT the current
     * srt used for the current media.
     * 
     * For example:
     * 
     * Given the files [A-media-file.mp4, A-media-file.srt, ABC.srt], the
     * original srt file will be "ABC.srt".
     * 
     * Given the files [A-media-file.mp4, A-media-file.srt], the original srt
     * file will be "A-media-file.srt".
     * 
     * @throws IOException
     */
    public void findOriginalSrtFile() throws IOException {
        if (mediaFile != null) {
            String srtPath = mediaFile.getPath();
            srtPath = srtPath.substring(0, srtPath.lastIndexOf('.')) + ".srt";
            for (File f : fileArray) {
                if (!f.getPath().equals(srtPath) && Util.getFileExtension(f).equalsIgnoreCase("srt")) {
                    originalSrtFile = f;
                    break;
                }
            }
            if (originalSrtFile == null) {
                File f = new File(srtPath);
                if (f.exists()) {
                    originalSrtFile = f;
                    onlyContainsOriginalSrt = true;
                } else {
                    throw new IOException(String.format("srt file not found in %s", path));
                }
            }
        }
    }

    /**
     * Generate path where srt file should be copied to.
     * 
     * @return path where srt file should be copited to.
     */
    public String generateCopySrtPath() {
        String copyPath = newSrtPath.substring(0, newSrtPath.lastIndexOf('.'));
        return String.format("%s (Old).srt", copyPath);
    }

    /**
     * @return {@link #path}.
     */
    public String getPath() {
        return path;
    }

    /**
     * @return {@link #fileArray}.
     */
    public File[] getFileArray() {
        return fileArray;
    }

    /**
     * @return {@link #mediaFile}.
     */
    public File getMediaFile() {
        return mediaFile;
    }

    /**
     * @return {@link #newSrtPath}.
     */
    public String getNewSrtPath() {
        return newSrtPath;
    }

    /**
     * @return {@link #originalSrtFile}.
     */
    public File getOriginalSrtFile() {
        return originalSrtFile;
    }

    /**
     * @return {@link #onlyContainsOriginalSrt}.
     */
    public boolean isOnlyContainsOriginalSrt() {
        return onlyContainsOriginalSrt;
    }

}
