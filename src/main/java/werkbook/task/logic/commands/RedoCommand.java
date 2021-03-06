//@@author A0140462R
package werkbook.task.logic.commands;

import java.util.EmptyStackException;

import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Redoes the last undo
 *
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the last undo";

    public static final String MESSAGE_SUCCESS = ".+:｡(ﾉ･ω･)ﾉﾞ" + "\nLast action redone";

    public static final String MESSAGE_NO_LAST_ACTION = "(・_・ヾ" + "\nNo undos have been performed";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_NO_LAST_ACTION);
        }
    }
}
