package werkbook.task.logic.parser;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DESCRIPTIONEND;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import java.util.List;
import java.util.Optional;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.EditCommand;
import werkbook.task.logic.commands.EditCommand.EditTaskDescriptor;
import werkbook.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DESCRIPTION, PREFIX_DESCRIPTIONEND,
                PREFIX_STARTDATETIME, PREFIX_ENDDATETIME, PREFIX_DEADLINE);
        try {
            argsTokenizer.tokenize(args);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        List<Optional<String>> preambleFields = ParserUtil
                .splitPreamble(argsTokenizer.getFullPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        try {
            //@@author A0139903B
            Optional<String> endDatePrefix = argsTokenizer.getValue(PREFIX_ENDDATETIME).isPresent()
                    ? argsTokenizer.getValue(PREFIX_ENDDATETIME) : argsTokenizer.getValue(PREFIX_DEADLINE);

            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor
                    .setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setStartDateTime(
                    ParserUtil.createStartDateTime(argsTokenizer.getValue(PREFIX_STARTDATETIME)));
            editTaskDescriptor.setEndDateTime(ParserUtil.createEndDateTime(endDatePrefix));
            //@@author
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }
}
