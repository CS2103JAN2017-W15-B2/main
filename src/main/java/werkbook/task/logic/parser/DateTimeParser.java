package werkbook.task.logic.parser;

import java.util.Date;

import werkbook.task.model.util.DateTimeUtil;

//@@author A0139903B
/**
 * Parser that uses PrettyTime to parse a string and convert it to follow the date time format
 */
public class DateTimeParser {
    /**
     * Parses any string to check if any dates can be found
     * @param date Any string to be parsed
     * @return Returns formatted date time if a string was found, an empty string otherwise
     */
    public static String parse(String dateToParse) {
        Date date = DateTimeUtil.parse(dateToParse);
        return date == null ? "" : DateTimeUtil.DATETIME_FORMATTER.format(date);
    }

    /**
     * Parses any string to check if any dates can be found
     * @param date Any string to be parsed
     * @return Returns a Date if a date was found, an empty object otherwise
     */
    public static Date parseAsDate(String dateToParse) {
        return DateTimeUtil.parse(dateToParse);
    }

    /**
     * Checks if the string is a valid date
     * @param date String to be checked
     * @return Returns true if the date is valid
     */
    public static boolean isValidDate(String date) {
        return DateTimeUtil.parse(date) != null;
    }
}
