//@@author A0162266E
package werkbook.task.logic.commands;

import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Terminates the program.
 */
public class GSyncCommand extends Command {

    public static final String COMMAND_WORD = "gsync";

    public static final String GOOGLE_SYNCED_ACKNOWLEDGEMENT = "Synced TaskList with Google Tasks";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            // UniqueTaskList gTaskList = gtasks.retrieve();
            // model.sync(gTaskList);
            gtasks.update(model.getTaskList());
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_SYNCED_ACKNOWLEDGEMENT);
    }
    
    @Override
    public boolean isMutable() {
        return true;
    }
}
