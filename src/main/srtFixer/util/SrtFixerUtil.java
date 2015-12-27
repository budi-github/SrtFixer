package main.srtFixer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.subtitle.subtitleObject.SubtitleObject;
import main.subtitle.subtitleObject.SubtitleObjectException;
import main.subtitle.timeHolder.TimeHolder;
import main.subtitle.timeHolder.TimeHolderException;
import main.util.file.FileUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;

/**
 * SrtFixer utilities.
 * 
 * @author budi
 */
public class SrtFixerUtil {

    /**
     * Parse srt file at path into list of {@link SubtitleObject}.
     * <p>
     * Format:
     * <p>
     * <code>id</code><br>
     * <code>hr:min:sec:ms --> hr:min:sec:ms</code><br>
     * <code>text</code><br>
     * <code>[optional] text</code><br>
     * <code>newline</code>
     * <p>
     * Example:
     * <p>
     * <code>1</code><br>
     * <code>00:01:23,456 --> 00:01:26,654</code><br>
     * <code>text text text</code>
     * <p>
     * <code>2</code><br>
     * <code>00:02:00,100 --> 00:02:03,900</code><br>
     * <code>more text text</code><br>
     * <code>such text text</code>
     * 
     * @param path path to srt file
     * @return list of parsed {@link SubtitleObject}
     * @throws IOException
     * @throws TimeHolderException
     * @throws SubtitleObjectException
     */
    public static List<SubtitleObject> parseSubtitleList(String path)
            throws IOException, TimeHolderException, SubtitleObjectException {
        List<SubtitleObject> subtitleList = new ArrayList<SubtitleObject>();

        boolean expectNumber = true, expectTime = false, expectText = false, done = false;
        int count = 0;
        TimeHolder timeStart = null, timeEnd = null;
        String[] array;

        StringBuilder builder = new StringBuilder();
        List<String> lines = FileUtil.readAllLines(path);

        for (String currentLine : lines) {
            if (expectNumber) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                ++count;
                expectNumber = false;
                expectTime = true;
            } else if (expectTime) {
                array = RegexUtil.split(RegexEnum.TIME_ARROW, currentLine);
                timeStart = new TimeHolder(array[0]);
                timeEnd = new TimeHolder(array[1]);
                expectTime = false;
                expectText = true;
            } else if (expectText) {
                if (currentLine.isEmpty()) {
                    expectText = false;
                    expectNumber = true;
                    done = true;
                } else {
                    builder.append(currentLine);
                    builder.append('\n');
                }
            }
            if (done) {
                try {
                    subtitleList.add(new SubtitleObject(count, timeStart, timeEnd, builder.toString()));
                } catch (SubtitleObjectException e) {
                    // TODO: determine how to handle this exception
                }
                builder.setLength(0);
                done = false;
            }
        }

        // rare case when last line in srt is malformed, any trailing text will be kept.
        if (builder.length() > 0) {
            subtitleList.add(new SubtitleObject(count, timeStart, timeEnd, builder.toString()));
        }

        return subtitleList;
    }

}
