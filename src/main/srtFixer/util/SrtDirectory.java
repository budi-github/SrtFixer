package main.srtFixer.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.util.file.FileUtil;

/**
 * Directory containing srt file.
 * <p>
 * Contains file information and path locations of srt file(s) and media file.
 * 
 * @author budi
 */
public class SrtDirectory {

    /**
     * Valid media file extensions.
     */
    private final static Set<String> MEDIA_FILE_EXTENSIONS;

    /**
     * If only one srt file is found in directory, the copy will be copied and
     * will end in this.
     */
    private static final String OLD_ENDING = " (Old).srt";

    static {
        MEDIA_FILE_EXTENSIONS = new HashSet<String>();
        MEDIA_FILE_EXTENSIONS.add("avi");
        MEDIA_FILE_EXTENSIONS.add("flv");
        MEDIA_FILE_EXTENSIONS.add("mkv");
        MEDIA_FILE_EXTENSIONS.add("mp4");
        MEDIA_FILE_EXTENSIONS.add("wmv");
    }

    /**
     * Path to directory containing srt file and media file.
     */
    private String path;

    /**
     * List of files in directory.
     */
    private List<File> fileList;

    /**
     * File containing media.
     * <p>
     * This will be used to rename srt file if necessary.
     * <p>
     * For example:
     * <p>
     * Given the files <code>[A-media-file.mp4, ABC.srt]</code>, the media
     * file's name will be used to name the resulting srt:
     * <code>A-media-file.srt</code>.
     */
    private File mediaFile;

    /**
     * Path containing new (or resulting) srt file.
     */
    private String newSrtPath;

    /**
     * File containing original srt.
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
     * @throws IOException Only thrown if path is not directory.
     */
    public SrtDirectory(String path) throws IOException {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IOException(String.format("%s is not directory", path));
        }

        this.path = path;
        File[] fileArray = directory.listFiles();
        this.fileList = Arrays.asList(fileArray);

        // sort and reverse fileList so that when iterating through this list,
        // the first srt file found will be the correctly identified.
        Collections.sort(fileList);
        Collections.reverse(fileList);

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
        for (File f : fileList) {
            String extension = FileUtil.getFileExtension(f);
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
        if (mediaFile != null) {
            String srtPath = mediaFile.getPath();
            newSrtPath = srtPath.substring(0, srtPath.lastIndexOf('.')) + ".srt";
        }
    }

    /**
     * Find original srt file in directory.
     * <p>
     * Original srt will first default to an srt file that is NOT the current
     * srt used for the current media.
     * <p>
     * For example:
     * <p>
     * Given the files
     * <code>[A-media-file.mp4, A-media-file.srt, ABC.srt]</code>, the original
     * srt file will be <code>ABC.srt</code>.
     * <p>
     * Given the files <code>[A-media-file.mp4, A-media-file.srt]</code>, the
     * original srt file will be <code>A-media-file.srt</code>.
     * 
     * @throws IOException Only thrown if srt file is not found.
     */
    public void findOriginalSrtFile() throws IOException {
        if (mediaFile != null) {
            String srtPath = mediaFile.getPath();
            srtPath = srtPath.substring(0, srtPath.lastIndexOf('.')) + ".srt";
            for (File f : fileList) {
                if (!f.getPath().equals(srtPath) && FileUtil.getFileExtension(f).equalsIgnoreCase("srt")) {
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
        } else {
            for (File f : fileList) {
                if (FileUtil.getFileExtension(f).equalsIgnoreCase("srt")) {
                    originalSrtFile = f;
                    onlyContainsOriginalSrt = true;
                    break;
                }
            }
        }

        if (originalSrtFile == null) {
            throw new IOException(String.format("srt file not found in %s", path));
        }
    }

    /**
     * Generate path where srt file should be copied to.
     * <p>
     * If {@link #newSrtPath} is not null, generate copy path with this file
     * name. Else if {@link #originalSrtFile} is not null, generate copy path
     * with this file name. Otherwise generate a default file name.
     * 
     * @return path where srt file should be copied to.
     */
    public String generateCopySrtPath() {
        String copyPath;
        if (newSrtPath != null) {
            copyPath = newSrtPath.substring(0, newSrtPath.lastIndexOf('.'));
        } else if (originalSrtFile != null) {
            copyPath = originalSrtFile.getPath();
            copyPath = copyPath.substring(0, copyPath.lastIndexOf('.'));
        } else {
            copyPath = "Default";
        }

        return String.format("%s%s", copyPath, OLD_ENDING);
    }

    /**
     * @return {@link #path}
     */
    public String getPath() {
        return path;
    }

    /**
     * @return {@link #fileList}
     */
    public List<File> getFileList() {
        return fileList;
    }

    /**
     * @return {@link #mediaFile}
     */
    public File getMediaFile() {
        return mediaFile;
    }

    /**
     * @return {@link #newSrtPath} if not null, otherwise
     *         {@link #originalSrtFile}'s path.
     */
    public String getNewSrtPath() {
        return newSrtPath != null ? newSrtPath : originalSrtFile.getPath();
    }

    /**
     * @return {@link #originalSrtFile}
     */
    public File getOriginalSrtFile() {
        return originalSrtFile;
    }

    /**
     * @return {@link #onlyContainsOriginalSrt}
     */
    public boolean isOnlyContainsOriginalSrt() {
        return onlyContainsOriginalSrt;
    }

}
