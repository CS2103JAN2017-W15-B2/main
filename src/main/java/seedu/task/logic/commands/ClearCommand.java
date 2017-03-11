package seedu.task.logic.commands;

import seedu.task.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "taskBook has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
