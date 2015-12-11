package main.srtFixer.console;

import java.io.IOException;

import main.srtFixer.SrtFixer;
import main.srtFixer.util.SrtDirectory;
import main.subtitle.subtitleObject.SubtitleObjectException;
import main.subtitle.timeHolder.TimeHolderException;

/**
 * SrtFixer console application.
 * 
 * @author budi
 */
public class SrtFixerConsole {

    /**
     * Directory containing the srt file(s) and the media file.
     * 
     * @see SrtDirectory
     */
    private static final String DIRECTORY = "";

    /**
     * Flag to determine whether or not to resync the subtitle.
     * <p>
     * If true, the program will resync the srt file with {@link #RESYNC}.
     * <p>
     * If false, the program will try to find old srt file, calculate previous
     * resynced value, and resync the new srt file with this value.
     * <p>
     * If false and program cannot find old srt file, resync will default to
     * 0.0.
     */
    private static final boolean SHOULD_RESYNC = false;

    /**
     * Time (in seconds) used to resync subtitle.
     * <p>
     * If the subtitles appear too FAST, INCREASE resync.
     * <p>
     * If the subtitles appear too SLOW, DECREASE resync.
     */
    private static final double RESYNC = 0.0;

    /**
     * Main function. Launch the console application.
     * 
     * @param args unused
     * @throws IOException Thrown if {@link #DIRECTORY} is not a directory or
     *             program cannot find srt file.
     * @throws TimeHolderException
     * @throws SubtitleObjectException
     */
    public static void main(String[] args) throws IOException, TimeHolderException, SubtitleObjectException {
        long startTime = System.nanoTime();

        SrtFixer.run(DIRECTORY, RESYNC, SHOULD_RESYNC);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Time Taken: " + duration + " ms");
    }

}
