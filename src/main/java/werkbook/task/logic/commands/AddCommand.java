package werkbook.task.logic.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import werkbook.task.commons.core.EventsCenter;
import werkbook.task.commons.events.ui.JumpToListRequestEvent;
import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list.\n"
            + "A task must have a name, but you can choose to give it a description or not.\n"
            + "It can also have an end date and time "
            + "but it cannot have a start date and time without an end.\n"
            + "Parameters: Task name [(Description)] [from Start date and time] [to End date and time] \n"
            + "Example: " + COMMAND_WORD
            + " Walk the dog (Take Zelda on a walk around the park) from 10am to 12pm";

    public static final String MESSAGE_SUCCESS = "ೕ(･ㅂ･ )" + "\nNew task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String description, String startDateTime, String endDateTime)
            throws IllegalValueException {
        final Set<Tag> tagSet = new LinkedHashSet<>();

        // Starts with default "Incomplete" tag
        tagSet.add(new Tag("Incomplete"));
        this.toAdd = new Task(new Name(name), new Description(description), new StartDateTime(startDateTime),
                new EndDateTime(endDateTime), new UniqueTagList(tagSet), clock);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);

            int updatedIndex = model.getFilteredTaskList().indexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(updatedIndex));

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
