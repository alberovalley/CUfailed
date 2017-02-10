package com.alberovalley.novedadesumbria.utils;

public class AlberoStrings {

    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////

    /**
     * This function returns the result of appending textToAppend to an appendableString
     * adding a comma if needed.
     * If appendableString equals originalString, there's no need for a comma to be added.
     * If appendableString doesn't equal originalString, something has already been appended,
     * so comma is needed.
     * <p>
     * Boolean parameter decides wether appending a white space after the comma or not
     *
     * @param originalString
     * @param appendableString
     * @param textToAppend
     * @param addWhiteSpace
     * @return
     */
    public static String appendComma(final String originalString, String appendableString, String textToAppend, boolean addWhiteSpace) {
        String result = appendableString;
        String whiteSpace = "";
        if (addWhiteSpace)
            whiteSpace = " ";
        if (originalString.equalsIgnoreCase(appendableString)) {
            // nothing has yet been added to the message, no comma needed
            result += whiteSpace + textToAppend;
        } else {
            // something has already been added to the message, comma needed
            result += "," + whiteSpace + textToAppend;
        }

        return result;
    }

    /**
     * This function returns the result of appending textToAppend to an appendableString
     * adding a comma (followed by a white space) if needed.
     * If appendableString equals originalString, there's no need for a comma to be added.
     * If appendableString doesn't equal originalString, something has already been appended,
     * so comma is needed.
     *
     * @param originalString
     * @param appendableString
     * @param textToAppend
     * @return
     */
    public static String appendComma(final String originalString, String appendableString, String textToAppend) {
        return appendComma(originalString, appendableString, textToAppend, true);
    }
}
