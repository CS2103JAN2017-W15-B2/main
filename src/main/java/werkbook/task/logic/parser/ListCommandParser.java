package werkbook.task.logic.parser;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.IncorrectCommand;
import werkbook.task.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SelectCommand and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        if (args.trim().toLowerCase().equals(ListCommand.SHOW_COMPLETE) 
                || args.trim().toLowerCase().equals(ListCommand.SHOW_ALL)
                || args.isEmpty()) {
            return new ListCommand(args);
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
