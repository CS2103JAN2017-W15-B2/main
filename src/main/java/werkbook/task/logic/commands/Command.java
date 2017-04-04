package werkbook.task.logic.commands;

import java.time.Clock;

import werkbook.task.commons.core.Messages;
import werkbook.task.gtasks.GTasks;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.Model;
import werkbook.task.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;
    protected GTasks gtasks;
    protected static Clock clock;

    /**
     * Constructs a feedback message to summarise an operation that displayed a
     * listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return displaySize == 0 ? Messages.MESSAGE_NO_TASKS_LISTED
                : String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command. Commands making use of
     * any of these should override this method to gain access to the
     * dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setGTasks(GTasks gtasks) {
        this.gtasks = gtasks;
    }

    //@@author A0162266E
    public static void setClock(Clock clock) {
        Command.clock = clock;
    }
    //@@author

    /**
     * Checks if the command is mutable or not
     *
     * @return true if command is mutable, false if not
     */
    public boolean isMutable() {
        return false;
    }
}
