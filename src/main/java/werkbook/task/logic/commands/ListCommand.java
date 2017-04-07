package werkbook.task.logic.commands;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String SHOW_INCOMPLETE = "incomplete";
    public static final String SHOW_COMPLETE = "complete";

    public static final String MESSAGE_SUCCESS = "Here are all your tasks!";
    public static final String MESSAGE_SHOW_COMPLETE_SUCCESS = "These are your completed tasks!";
    public static final String MESSAGE_SHOW_INCOMPLETE_SUCCESS = "These are your uncompleted tasks!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks by completion status, lists all tasks by default\n"
            + "Parameters: complete/incomplete \n" + "Example: " + COMMAND_WORD + " complete";

    private boolean showComplete = false;
    private boolean showIncomplete = false;

    public ListCommand(String statusToShow) {
        showComplete = statusToShow.trim().equals(SHOW_COMPLETE);
        showIncomplete = statusToShow.trim().equals(SHOW_INCOMPLETE);
    }

    @Override
    public CommandResult execute() {
        String message = MESSAGE_SUCCESS;
        if (showIncomplete) {
            model.updateFilteredTaskListToShowIncomplete();
            message = MESSAGE_SHOW_INCOMPLETE_SUCCESS;
        } else if (showComplete) {
            model.updateFilteredTaskListToShowComplete();
            message = MESSAGE_SHOW_COMPLETE_SUCCESS;
        } else {
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(message);
    }
}
