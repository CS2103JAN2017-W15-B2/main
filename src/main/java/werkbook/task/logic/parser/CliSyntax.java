package werkbook.task.logic.parser;

import java.util.regex.Pattern;

import werkbook.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    //@@author A0139903B
    /* Prefix definitions */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("(");
    public static final Prefix PREFIX_DESCRIPTIONEND = new Prefix(")");
    public static final Prefix PREFIX_STARTDATETIME = new Prefix("from", true);
    public static final Prefix PREFIX_ENDDATETIME = new Prefix("to", true);
    public static final Prefix PREFIX_DEADLINE = new Prefix("by", true);
    //@author

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
