package werkbook.task.logic.commands;


/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String SHOW_ALL = "all";
    public static final String SHOW_COMPLETE = "complete";
    
    
    public static final String MESSAGE_SUCCESS = "Here's all your tasks!";
    public static final String MESSAGE_SHOW_COMPLETE_SUCCESS = "These are your completed tasks!";
    public static final String MESSAGE_SHOW_INCOMPLETE_SUCCESS = "Here are all your things to do!";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks by completion status, lists incomplete tasks by default\n"
            + "Parameters: complete/all\n"
            + "Example: " + COMMAND_WORD + " complete";

    private boolean showComplete = false;
    private boolean showAll = false;
    
    
    public ListCommand(String statusToShow) {
        showComplete = statusToShow.trim().equals(SHOW_COMPLETE);
        showAll = statusToShow.trim().equals(SHOW_ALL);
    }
    
    @Override
    public CommandResult execute() {
        String message = MESSAGE_SUCCESS;
        if (showAll) {
            model.updateFilteredListToShowAll();
        } else if (showComplete) {
            model.updateFilteredTaskListToShowComplete();
            message = MESSAGE_SHOW_COMPLETE_SUCCESS;
        }  else {
            model.updateFilteredTaskListToShowIncomplete();
            message = MESSAGE_SHOW_INCOMPLETE_SUCCESS;
        }
        return new CommandResult(message);
    }
}
