package werkbook.task.model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.TaskList;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        // @@author A0130183U
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            return new Task[] {
                new Task(new Name("Welcome to Werkbook! Select this task by typing 'select 1'"),
                new Description("Now, add a new task by typing: 'add This is how I can add a floating task "
                        + "(Descriptions are optional, and they go inside brackets)'"),
                new StartDateTime(sdf.format(cal.getTime())), new EndDateTime(sdf.format(cal.getTime())),
                new UniqueTagList("Incomplete")),
                new Task(new Name("Now try to select this task!"),
                new Description("As you have already figured, typing `select` followed by a number lets you choose "
                        + "the specific task from the list you see here. "
                        + "You can specify start and end date times using `from` and `to`, as well as `by`. "
                        + "Try typing in: `add Learn how to create a deadlined task by today`"),
                new StartDateTime(sdf.format(cal.getTime())), new EndDateTime(sdf.format(cal.getTime())),
                new UniqueTagList("Incomplete")),
                new Task(new Name("Finally, select this task!"),
                    new Description("Now that you have learnt how to add a deadlined task, it's time to add an event "
                            + "Type in: `add Use Werkbook for a week from today to next week` "
                            + "Lastly, if you need any help, simply type in `help`!"),
                    new StartDateTime(sdf.format(cal.getTime())), new EndDateTime(sdf.format(cal.getTime())),
                    new UniqueTagList("Incomplete"))};
            // @@author
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleTaskList() {
        try {
            TaskList sampleTaskList = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskList.addTask(sampleTask);
            }
            return sampleTaskList;
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
