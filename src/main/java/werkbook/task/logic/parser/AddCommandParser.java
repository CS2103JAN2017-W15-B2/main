package werkbook.task.logic.parser;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DESCRIPTIONEND;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import java.util.NoSuchElementException;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.AddCommand;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DESCRIPTION, PREFIX_DESCRIPTIONEND,
                PREFIX_STARTDATETIME, PREFIX_ENDDATETIME, PREFIX_DEADLINE);
        try {
            argsTokenizer.tokenize(args);

            return new AddCommand(argsTokenizer.getFullPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(""),
                    argsTokenizer.getValue(PREFIX_STARTDATETIME).orElse(""),
                    argsTokenizer.getValue(PREFIX_ENDDATETIME)
                            .orElse(argsTokenizer.getValue(PREFIX_DEADLINE).orElse("")));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
