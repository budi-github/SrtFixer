package main.srtFixer.console;

import java.io.IOException;

import main.srtFixer.SrtFixer;

/**
 * SrtFixer console application.
 * 
 * @author budi
 */
public class SrtFixerConsole {

    /**
     * Directory containing the media file and the srt file(s).
     */
    private static final String DIRECTORY = "";

    /**
     * Flag to determine whether or not to resync the subtitle.
     * 
     * If true, program will try to find old srt file, calculate previous
     * resynced value, and resync the new srt file with this value.
     * 
     * If true and program cannot find old srt file, resync will default to 0.0.
     */
    private static final boolean SHOULD_RESYNC = false;

    /**
     * Time (in seconds) used to resync subtitle.
     * 
     * If the subtitles appear too FAST, INCREASE resync. If the subtitles
     * appear too SLOW, DECREASE resync.
     */
    private static final double RESYNC = 0.0;

    /**
     * Main function. Launch the console application.
     * 
     * @param args unused
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        SrtFixer.run(DIRECTORY, RESYNC, SHOULD_RESYNC);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Time Taken: " + duration + " ms");
    }

}
