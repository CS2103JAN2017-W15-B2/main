//@@author A0162266E
package werkbook.task.gtasks;

import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.task.ReadOnlyTask;

public class TaskToGTaskAdapter {
    private Task gTask;
    
    public TaskToGTaskAdapter(ReadOnlyTask task) throws IllegalValueException {
        this.gTask = new Task();
        this.gTask.setTitle(task.getName().taskName);
        this.gTask.setNotes(task.getDescription().value);
        if (task.getEndDateTime().isPresent()) {
            this.gTask.setDue(new DateTime(task.getEndDateTime().value.get()));
        }
        if(task.getTags().contains(new Tag("Complete"))) {
            this.gTask.setCompleted(new DateTime(task.getUpdated()));
        }
    }

    public Task getGTask() {
        return gTask;
    }

    public static Task getGTask(ReadOnlyTask task) throws IllegalValueException {
        Task gTask = new Task();
        gTask.setTitle(task.getName().taskName);
        gTask.setNotes(task.getDescription().value);
        if (task.getEndDateTime().isPresent()) {
            gTask.setDue(new DateTime(task.getEndDateTime().value.get()));
        }
        if(task.getTags().contains(new Tag("Complete"))) {
            gTask.setStatus("completed");
        }
        return gTask;
    }
}
