package main.subtitle.subtitleObject;

import main.srtFixer.config.SrtFixerConfig;
import main.subtitle.fix.ChangeLsToIs;
import main.subtitle.fix.FixAbbreviations;
import main.subtitle.fix.FixAcronym;
import main.subtitle.fix.FixAmpersand;
import main.subtitle.fix.FixCapitalization;
import main.subtitle.fix.FixCommonErrors;
import main.subtitle.fix.FixContractions;
import main.subtitle.fix.FixDashes;
import main.subtitle.fix.FixEllipses;
import main.subtitle.fix.FixEnding;
import main.subtitle.fix.FixHeight;
import main.subtitle.fix.FixLetterS;
import main.subtitle.fix.FixMisplacedQuotes;
import main.subtitle.fix.FixMultilineDashes;
import main.subtitle.fix.FixMultilineQuotes;
import main.subtitle.fix.FixNonTraditionalStrings;
import main.subtitle.fix.FixNumbers;
import main.subtitle.fix.FixSingleDoubleQuotes;
import main.subtitle.fix.FixSpacing;
import main.subtitle.fix.FixSpelling;
import main.subtitle.fix.FixTime;
import main.subtitle.fix.FixToUppercase;
import main.subtitle.fix.FixWebsites;
import main.subtitle.fix.PrepareLine;
import main.subtitle.fix.fixMultipleLines.FixOneLine;
import main.subtitle.fix.fixMultipleLines.FixThreeLines;
import main.subtitle.fix.fixMultipleLines.FixTwoLines;
import main.subtitle.fix.fixMultipleLines.FixUnbalancedDashes;
import main.subtitle.fix.remove.RemoveCharacterName;
import main.subtitle.fix.remove.RemoveEmpty;
import main.subtitle.fix.remove.RemoveEndingCharacter;
import main.subtitle.map.SplitMap;
import main.subtitle.specialCases.DoNotFix;
import main.subtitle.specialCases.ManuelFix;
import main.subtitle.timeHolder.TimeHolder;
import main.subtitle.util.SubtitleUtil;
import main.util.regex.RegexEnum;
import main.util.regex.RegexUtil;
import main.util.string.StringUtil;

/**
 * Container for all necessary components of a subtitle.
 * 
 * @author budi
 */
public class SubtitleObject {

    /**
     * Unique id.
     */
    private int id;

    /**
     * Start {@link TimeHolder}.
     */
    private TimeHolder timeStart;

    /**
     * End {@link TimeHolder}.
     */
    private TimeHolder timeEnd;

    /**
     * Subtitle text.
     */
    private String text;

    /**
     * Original text.
     */
    private String originalText;

    /**
     * {@link SplitMap}.
     */
    private SplitMap splitMap;

    /**
     * Class constructor.
     * 
     * @param id current id
     * @param timeStart starting time
     * @param timeEnd ending time
     * @param text text
     * @throws SubtitleObjectException Thrown if timeStart is greater than
     *             timeEnd.
     */
    public SubtitleObject(int id, TimeHolder timeStart, TimeHolder timeEnd, String text)
            throws SubtitleObjectException {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.text = text;
        this.originalText = RemoveEndingCharacter.fix(text, '<', '>', null);
        this.splitMap = new SplitMap();

        if (timeStart != null && timeStart.calcTotalTimeMs() > timeEnd.calcTotalTimeMs()) {
            throw new SubtitleObjectException(
                    String.format("[ID: %s] Start time is greater than end time. (%s > %s)", id, timeStart, timeEnd));
        }
    }

    /**
     * Class constructor using only text.
     * 
     * @param text text
     * @throws SubtitleObjectException
     */
    public SubtitleObject(String text) throws SubtitleObjectException {
        this(-1, null, null, text);
    }

    /**
     * Apply fixes to text.
     */
    public void fix() {
        if (text == null || text.isEmpty()) {
            return;
        }
        text = text.trim();
        text = RemoveEmpty.fix(text, this);
        if (text.isEmpty()) {
            return;
        }

        // TODO: comment this

        text = RemoveEndingCharacter.fix(text, '<', '>', this);
        text = RemoveEndingCharacter.fix(text, '{', '}', this);
        text = RemoveEndingCharacter.fix(text, '[', ']', this);
        if (ManuelFix.MANUEL_FIX.containsKey(text)) {
            text = ManuelFix.MANUEL_FIX.get(text);
            return;
        } else if (DoNotFix.DO_NOT_FIX.contains(text)) {
            return;
        }

        if (text.startsWith("#") && !text.endsWith("#")) {
            text += " #";
        }
        text = RemoveEndingCharacter.fix(text, '(', ')', this);
        text = FixNonTraditionalStrings.fix(text, this);

        // TODO: messy (see "...with the .22?")
        if (text.startsWith("-") && !text.startsWith("- ")) {
            text = "- " + text.substring(1);
            splitMap.clear(); // TODO: clean up
        }
        text = FixEllipses.fix(text, this);
        text = FixSingleDoubleQuotes.fix(text, this);

        String result = "";
        int removedNames = 0;
        String originalText = text;

        text = FixUnbalancedDashes.fix(text);
        boolean fixedUnbalancedDashes = !originalText.equals(text);

        if (!text.startsWith("-")) {
            //text = text.replace("\n", " "); // TODO: explain this
        }

        String temp;
        for (String line : RegexUtil.split(RegexEnum.NEWLINE, text)) { // TODO: if contains punctuation
            line = line.trim();
            splitMap.clear();
            line = PrepareLine.fix(line, this);
            split(RegexEnum.SPACE, line);
            temp = line;
            line = RemoveCharacterName.fix(line, this);
            if (!temp.equals(line)) {
                ++removedNames;
            }
            line = FixSpelling.fix(line, this);
            if (!SrtFixerConfig.isToggleCorrectCapitalization()) {
                line = FixToUppercase.fix(line, this); // TODO: remove this once all bugs in FixCapitalization is fixed
            }
            line = ChangeLsToIs.fix(line, this);
            line = FixDashes.fix(line, this);
            line = RemoveEmpty.fix(line, this);
            if (SrtFixerConfig.isToggleCorrectCapitalization()) {
                line = FixCapitalization.fix(line, this);
            }
            line = FixAmpersand.fix(line, this);
            line = FixHeight.fix(line, this);
            line = FixWebsites.fix(line, this);
            line = FixTime.fix(line, this);
            line = FixNumbers.fix(line, this);
            line = FixAbbreviations.fix(line, this);
            line = FixContractions.fix(line, this);
            line = FixAcronym.fix(line, this);
            line = FixLetterS.fix(line, this);
            line = FixMisplacedQuotes.fix(line, this);
            line = FixSpacing.fix(line, this);
            line = FixCommonErrors.fix(line, this);
            line = FixEnding.fix(line, this);

            if (!line.isEmpty()) {
                line = FixMultilineDashes.fix(result, line); // TODO: why doesn't this work???
                if (!result.isEmpty()) {
                    result = FixMultilineDashes.fix(line, result);
                }
                StringBuilder builder = new StringBuilder();
                if (removedNames == 2) {
                    builder.append("- ");
                    builder.append(result);
                    builder.append("- ");
                    builder.append(line);
                    result = builder.toString();
                    continue;
                }
                builder.append(result);
                builder.append(line);
                builder.append('\n');
                result = builder.toString();
            }
        }

        result = FixEllipses.fix(result, this);
        result = result.trim();

        result = FixMultilineQuotes.fix(result, this);

        result = FixThreeLines.fix(result);
        result = FixTwoLines.fix(result);
        result = FixOneLine.fix(result);

        if (StringUtil.count(originalText, '\n') <= 1 && SubtitleUtil.isApproximatelyEqual(result, originalText)
                && !fixedUnbalancedDashes) {
            result = originalText;
        }

        text = result;
    }

    /*
    public void fix() {
        TokenizedText tt = new TokenizedText(text);
    
        RemoveHTMLTags.remove(tt);
    
        FixConsecutive.fix(tt);
        FixPunctuationSpacing.fix(tt);
    
        FixAbbreviation.fix(tt);
        DetectAbbreviation.detect(tt);
    
        FixLToI.fix(tt);
    
        FixTime.fix(tt);
        DetectTime.detect(tt);
        RemoveHearingImpaired.remove(tt);
    
        FixApostrophe.fix(tt);
        FixContraction.fix(tt);
        DetectContraction.detect(tt);
    
        FixWebsite.removeExcessSpace(tt);
        DetectWebsite.detect(tt);
    
        FixAcronym.removeExcessSpace(tt);
        DetectAcronym.detect(tt);
        FixAcronym.fixCapitalization(tt);
    
        FixCapitalization.fix(tt);
        FixDash.fix(tt);
        text = tt.toString();
    }
    */

    /**
     * Split line by regex.
     * 
     * @param re {@link RegexEnum}
     * @param line line to split
     * @return list of strings split by regex
     */
    public String[] split(RegexEnum re, String line) {
        return splitMap.getSplit(re, line);
    }

    /**
     * Replace all using pattern.
     * 
     * @param re {@link RegexEnum}
     * @param split list to split
     * @param index index of word in split
     * @param replace string to replace
     * @return fixed string
     */
    public String patternReplaceAll(RegexEnum re, String[] split, int index, String replace) {
        return splitMap.getPatternReplaceAll(re, split, index, replace);
    }

    /**
     * Clear {@link #splitMap}.
     */
    public void clearSplitMap() {
        splitMap.clear();
    }

    /**
     * Clear {@link SubtitleObject} splitMap only if temp does not equal line.
     * 
     * @param so {@link SubtitleObject}
     * @param temp original string to compare against line
     * @param line new line
     * @return line
     */
    public static String clearMapIfChanged(SubtitleObject so, String temp, String line) {
        if (so == null) {
            return line;
        }

        if (!temp.equals(line)) {
            so.getSplitMap().clear();
        }
        return line;
    }

    /**
     * Clear {@link #text}.
     */
    public void clearText() {
        text = "";
    }

    /**
     * Trim {@link #text}
     */
    public void trim() {
        text = text.trim();
    }

    /**
     * Add time to {@link #timeStart} and {@link #timeEnd}.
     * 
     * @param time time to add (in seconds)
     */
    public void addTime(double time) {
        timeStart.addTime(time);
        timeEnd.addTime(time);
    }

    /**
     * Generate formatted time text using {@link #timeStart} and
     * {@link #timeEnd}.
     * 
     * @return formatted time text.
     */
    public String generateTimeText() {
        StringBuilder builder = new StringBuilder();
        builder.append(timeStart.toString());
        builder.append(" --> ");
        builder.append(timeEnd.toString());
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toString(id));
        builder.append('\n');
        builder.append(generateTimeText());
        builder.append('\n');
        builder.append(text);
        builder.append('\n');
        builder.append('\n');
        return builder.toString();
    }

    /**
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * {@link #id}
     * 
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return {@link #timeStart}
     */
    public TimeHolder getTimeStart() {
        return timeStart;
    }

    /**
     * @return {@link #timeEnd}
     */
    public TimeHolder getTimeEnd() {
        return timeEnd;
    }

    /**
     * @return {@link #text}
     */
    public String getText() {
        return text;
    }

    /**
     * @return {@link #originalText}
     */
    public String getOriginalText() {
        return originalText;
    }

    /**
     * @return {@link #splitMap}
     */
    public SplitMap getSplitMap() {
        return splitMap;
    }

}
