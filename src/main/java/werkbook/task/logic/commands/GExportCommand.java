package werkbook.task.logic.commands;

import werkbook.task.logic.commands.exceptions.CommandException;

//@@author A0162266E
/**
 * Export tasks to Google Tasks
 */
public class GExportCommand extends Command {

    public static final String COMMAND_WORD = "gexport";

    public static final String GOOGLE_EXPORTED_ACKNOWLEDGEMENT = "ヽ(｡･ω･｡)ﾉ"
            + "\nExported Tasks to Google Tasks";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            gtasks.update(model.getTaskList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_EXPORTED_ACKNOWLEDGEMENT);
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
