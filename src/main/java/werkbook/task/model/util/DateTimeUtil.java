package werkbook.task.model.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

//@@author A0139903B
/**
 * A utility class that formats dates into more human readable dates.
 * Uses PrettyTime API to do the formatting
 */
public class DateTimeUtil {
    private static final int NUM_OF_DAYS_LIMIT = 14;
    public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");

    private static PrettyTime prettyTime = new PrettyTime(Locale.UK);
    private static PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
    private static Date currentDate = new Date();

    /**
     * Parses a string and extracts any values with resemblance to a date using PrettyTimeNLP
     * @param dateToParse a string to be parsed
     * @return a list of dates found in the string
     */
    public static List<Date> parse(String dateToParse) {
        return prettyTimeParser.parse(dateToParse);
    }

    /**
     * Returns prettified date time if the difference between {@code date} and
     * the current date is within 14 days
     * @param date date to be formatted
     * @return ~A pretty string~
     */
    public static String getPrettyDateTime(Date date) {
        return getDifferenceInDays(date, currentDate) > NUM_OF_DAYS_LIMIT ? DATETIME_FORMATTER.format(date)
                : prettyTime.format(date);
    }

    public static Date getVerboseDateTime(String dateToParse) {
        return prettyTimeParser.parse(dateToParse).get(0);
    }

    /**
     * Returns the absolute difference in days between two dates
     * @param firstDate first date to compare
     * @param secondDate second date to compare
     * @return absolute number of days between the two dates
     */
    public static int getDifferenceInDays(Date firstDate, Date secondDate) {
        return Math.abs((int) (firstDate.getTime() - secondDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }

    /**
     * Returns the difference between two dates
     * @param firstDate first date to compare
     * @param secondDate second date to compare
     * @return 0 if dates are equal, -ve if firstDate is earlier than secondDate,
     *           +ve if firstDate is later than secondDate
     */
    public static int getDifference(Date firstDate, Date secondDate) {
        return firstDate.compareTo(secondDate);
    }
}
