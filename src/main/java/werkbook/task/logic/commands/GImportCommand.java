//@@author A0162266E
package werkbook.task.logic.commands;

import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.task.UniqueTaskList;

/**
 * Terminates the program.
 */
public class GImportCommand extends Command {

    public static final String COMMAND_WORD = "gimport";

    public static final String GOOGLE_IMPORTED_ACKNOWLEDGEMENT = "ヽ(｡･ω･｡)ﾉ"
            + "\nImported Tasks from Google Tasks";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            UniqueTaskList gTaskList = gtasks.retrieve();
            model.importTaskList(gTaskList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_IMPORTED_ACKNOWLEDGEMENT);
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
