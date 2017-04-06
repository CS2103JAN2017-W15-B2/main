package werkbook.task.model.util;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            return new Task[] {
                new Task(new Name("Welcome to werkbook! Start by adding a task."),
                        new Description("Try: add <name of task> (description)"),
                        new StartDateTime("03/01/2016 0900"), new EndDateTime(sdf.format(cal.getTime())),
                        new UniqueTagList("Incomplete")),
                };
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
