package main.srtFixer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.srtFixer.config.SrtFixerConfig;
import main.srtFixer.util.SrtDirectory;
import main.srtFixer.util.SrtFixerUtil;
import main.subtitle.SubtitleObject;
import main.subtitle.SubtitleUtil;
import main.util.file.FileUtil;

/**
 * SrtFixer program.
 * 
 * @author budi
 */
public class SrtFixer {

    /**
     * Set of all invalid words.
     * <p>
     * If any invalid word is found within any line, the line is removed.
     */
    private static final Set<String> INVALID_WORDS;

    /**
     * Set of all invalid words, only occuring in the beginning or end of the
     * srt file.
     * <p>
     * If any invalid word is found within these lines, the line is removed.
     */
    private static final Set<String> INVALID_FIRST_LAST_WORDS;

    static {
        INVALID_WORDS = new HashSet<String>();
        INVALID_WORDS.add("aorion");
        INVALID_WORDS.add("bozxphd");
        INVALID_WORDS.add("goldenbeard");
        INVALID_WORDS.add("pacifer");
        INVALID_WORDS.add("subscene");

        INVALID_FIRST_LAST_WORDS = new HashSet<String>();
        INVALID_FIRST_LAST_WORDS.add("corrected");
        INVALID_FIRST_LAST_WORDS.add("psdh");
        INVALID_FIRST_LAST_WORDS.add("resync");
        INVALID_FIRST_LAST_WORDS.add("sub");
        INVALID_FIRST_LAST_WORDS.add("subs");
        INVALID_FIRST_LAST_WORDS.add("subtitle");
        INVALID_FIRST_LAST_WORDS.add("translate");
        INVALID_FIRST_LAST_WORDS.add("translation");
    }

    /**
     * String to print to indicate text was deleted.
     */
    private static final String STRING_DELETED =
            "------------------------------ Deleted -------------------------------";

    /**
     * String to print to indicate original text.
     */
    private static final String STRING_ORIGINAL =
            "------------------------------ Original ------------------------------";

    /**
     * String to print to indicate new text.
     */
    private static final String STRING_CORRECTED =
            "------------------------------ Corrected -----------------------------";

    /**
     * Run SrtFixer program.
     * 
     * @param directory Directory containing of the media file and srt file.
     * @param resync time (in seconds) used to resync subtitle
     * @param shouldResync If true, resync subtitle, otherwise do nothing.
     * @return list of {@link SubtitleObject}
     * @throws IOException
     */
    public static List<SubtitleObject> run(String directory, double resync, boolean shouldResync) throws IOException {
        SrtDirectory srtDirectory = new SrtDirectory(directory);
        // in the case where directory only contains the original srt file,
        // copy this file into a new file and recreate the SrtDirectory.
        if (srtDirectory.isOnlyContainsOriginalSrt()) {
            if (srtDirectory.getNewSrtPath() != null) {
                FileUtil.copyFile(srtDirectory.getNewSrtPath(), srtDirectory.generateCopySrtPath());
            }
        }

        List<SubtitleObject> subtitleList = SrtFixerUtil.parseSubtitleList(srtDirectory.getOriginalSrtFile().getPath());

        for (SubtitleObject so : subtitleList) {
            so.fix();
        }

        removeInvalidFirstAndLastWords(subtitleList);
        removeInvalidWords(subtitleList);

        updateIds(subtitleList);

        int changes = countAndPrintChanges(subtitleList);

        if (!shouldResync) {
            resync = recalculateResync(subtitleList, srtDirectory.getNewSrtPath(), resync);
        }

        for (SubtitleObject so : subtitleList) {
            so.addTime(resync);
        }

        // warning and fix for overlapping subtitles
        SubtitleObject prev = null;
        for (SubtitleObject so : subtitleList) {
            if (prev != null && prev.getTimeEnd().calcTotalTimeMs() > so.getTimeStart().calcTotalTimeMs()) {
                System.err.println(
                        String.format("Warning! Overlaping subtitles: (ID=%s, TimeEnd=%s), (ID=%s, TimeStart=%s)",
                                prev.getId(), prev.getTimeEnd(), so.getId(), so.getTimeStart()));
                so.getTimeStart().setTime(prev.getTimeEnd().calcTotalTimeMs() + 1);
            }
            prev = so;
        }

        // warning for quick subtitles
        for (SubtitleObject so : subtitleList) {
            int duration = so.getTimeEnd().calcTotalTimeMs() - so.getTimeStart().calcTotalTimeMs();
            if (duration < SrtFixerConfig.getMinDuration()) {
                System.err.println(
                        String.format("Warning! Quick subtitles: (ID=%s, TimeStart=%s, TimeEnd=%s, Duration=%s)",
                                so.getId(), so.getTimeStart(), so.getTimeEnd(), duration));
            }
        }

        writeListToFile(subtitleList, srtDirectory.getNewSrtPath());

        System.out.println("Resync:     " + resync + " seconds");
        System.out.println("Changes:    " + changes);

        return subtitleList;
    }

    /**
     * Recalculate resync based on previous resync value found in path.
     * <p>
     * This functions to keep previously calculated resynced value, so the user
     * does not have to manually input a new resync value.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     * @param path path to new subtitle file
     * @param resync original time (in seconds) used to resync subtitle
     * @return new time (in seconds) used to resync subtitle
     * @throws IOException
     */
    private static double recalculateResync(List<SubtitleObject> subtitleList, String path, double resync)
            throws IOException {
        if (new File(path).exists()) {
            int index;

            SubtitleObject newSo = SrtFixerUtil.parseSubtitleList(path).get(0);
            SubtitleObject oldSo;
            index = 0;
            do {
                oldSo = subtitleList.get(index);
                ++index;
            } while (oldSo.getText().isEmpty());
            resync = (newSo.getTimeStart().calcTotalTimeMs() - oldSo.getTimeStart().calcTotalTimeMs()) / 1000.0;
        }
        return resync;
    }

    /**
     * Handles clearing of all invalid words found within the first and last
     * lines of the subtitle file.
     * <p>
     * {@link SubtitleObject}s where invalid words are found will be cleared,
     * and will not deleted from list.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     */
    private static void removeInvalidFirstAndLastWords(List<SubtitleObject> subtitleList) {
        int index = 0;
        while (subtitleList.size() > index) {
            SubtitleObject so = subtitleList.get(index);
            String firstText = so.getText().toLowerCase();
            if (SubtitleUtil.sentenceContainsWords(firstText, INVALID_FIRST_LAST_WORDS)) {
                so.clearText();
                ++index;
            } else {
                break;
            }
        }

        index = subtitleList.size() - 1;
        while (index >= 0) {
            SubtitleObject so = subtitleList.get(index);
            String lastText = so.getText().toLowerCase();
            if (SubtitleUtil.sentenceContainsWords(lastText, INVALID_FIRST_LAST_WORDS)) {
                so.clearText();
                --index;
            } else {
                break;
            }
        }
    }

    /**
     * Handles clearing of all invalid words found within the entire subtitle
     * file.
     * <p>
     * {@link SubtitleObject}s where invalid words are found will be cleared,
     * and will not deleted from list.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     */
    private static void removeInvalidWords(List<SubtitleObject> subtitleList) {
        for (SubtitleObject so : subtitleList) {
            if (SubtitleUtil.sentenceContainsWords(so.getText(), INVALID_WORDS)) {
                so.clearText();
            }
        }
    }

    /**
     * Updates ids.
     * <p>
     * If the {@link SubtitleObject}'s text is empty, the id will be set to -1.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     */
    private static void updateIds(List<SubtitleObject> subtitleList) {
        int id = 0;
        for (SubtitleObject so : subtitleList) {
            String originalText = so.getOriginalText();
            String newText = so.getText();
            if (!originalText.trim().equals(newText.trim())) {
                if (newText.trim().isEmpty()) {
                    so.setId(-1);
                }
            }

            if (so.getId() != -1) {
                so.setId(++id);
            }
        }
    }

    /**
     * Counts numbers of changes compared to the original srt file and prints
     * changes to console.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     * @return number of changes compared to the original srt file
     */
    private static int countAndPrintChanges(List<SubtitleObject> subtitleList) {
        int changes = 0;
        for (SubtitleObject so : subtitleList) {
            String originalText = so.getOriginalText();
            String newText = so.getText();
            if (!originalText.trim().equals(newText.trim())) {
                System.out.println(String.format("%d (%s)", so.getId(), so.generateTimeText()));
                if (newText.trim().isEmpty()) {
                    System.out.println(STRING_DELETED);
                    System.out.println(originalText.trim());
                } else {
                    System.out.println(STRING_ORIGINAL);
                    System.out.println(originalText.trim());
                    System.out.println(STRING_CORRECTED);
                    System.out.println(newText.trim());
                }
                System.out.println("\n");
                ++changes;
            }
        }

        return changes;
    }

    /**
     * Write list of {@link SubtitleObject} to file.
     * 
     * @param subtitleList list of {@link SubtitleObject}
     * @param path path to file
     * @throws IOException
     */
    private static void writeListToFile(List<SubtitleObject> subtitleList, String path) throws IOException {
        System.out.println("Writing to: " + path);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            for (SubtitleObject so : subtitleList) {
                if (so.getId() > 0) {
                    out.write(so.toString());
                }
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
