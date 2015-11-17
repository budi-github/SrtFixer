package main.srtFixer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.subtitle.SubtitleObject;
import main.subtitle.TimeHolder;
import main.util.Util;
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
     * 
     * @param path path to srt file
     * @return list of parsed {@link SubtitleObject}
     * @throws IOException
     */
    public static List<SubtitleObject> parseSubtitleList(String path) throws IOException {
        List<SubtitleObject> subtitleList = new ArrayList<SubtitleObject>();

        boolean findNumber = true, findTime = false, findText = false, done = false;
        int count = 0;
        TimeHolder timeStart = null, timeEnd = null;
        String[] array;

        StringBuilder builder = new StringBuilder();
        List<String> lines = Util.readAllLines(path);

        for (String currentLine : lines) {
            if (findNumber) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                ++count;
                findNumber = false;
                findTime = true;
            } else if (findTime) {
                array = RegexUtil.split(RegexEnum.TIME_ARROW, currentLine);
                timeStart = new TimeHolder(array[0]);
                timeEnd = new TimeHolder(array[1]);
                findTime = false;
                findText = true;
            } else if (findText) {
                if (currentLine.isEmpty()) {
                    findText = false;
                    findNumber = true;
                    done = true;
                } else {
                    builder.append(currentLine);
                    builder.append('\n');
                }
            }
            if (done) {
                subtitleList.add(new SubtitleObject(count, timeStart, timeEnd, builder.toString()));
                builder.setLength(0);
                done = false;
            }
        }

        // rare case when srt is malformed, any trailing text will be kept. 
        if (builder.length() > 0) {
            subtitleList.add(new SubtitleObject(count, timeStart, timeEnd, builder.toString()));
        }

        return subtitleList;
    }

}
