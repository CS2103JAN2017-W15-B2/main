//@@author A0140462R
package werkbook.task.logic.commands;

import java.util.EmptyStackException;

import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Undoes the previous mutable command executed
 *
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last action performed";

    public static final String MESSAGE_SUCCESS = "╰(・∇・╰)" + "\nLast action undone";

    public static final String MESSAGE_NO_LAST_ACTION = "(・_・ヾ" + "\nNo previous action has been performed";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_NO_LAST_ACTION);
        }
    }
}
