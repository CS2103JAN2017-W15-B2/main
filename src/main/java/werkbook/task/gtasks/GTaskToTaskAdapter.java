//@@author A0162266E
package werkbook.task.gtasks;

import java.util.Date;

import com.google.api.services.tasks.model.Task;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.util.DateTimeUtil;

public class GTaskToTaskAdapter implements ReadOnlyTask {
    private Name name;
    private Description description;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    private Date lastUpdated;
    private UniqueTagList tags;

    public GTaskToTaskAdapter(Task gTask) throws IllegalValueException {
        this.name = new Name(gTask.getTitle() == "" ? "No Title" : gTask.getTitle());
        this.description = new Description(gTask.getNotes() == null ? "" : gTask.getNotes());
        this.startDateTime = new StartDateTime("");
        this.endDateTime = new EndDateTime(gTask.getDue() == null ? "" : DateTimeUtil.DATETIME_FORMATTER.format(
                gTask.getDue().getValue()));
        this.lastUpdated = new Date(gTask.getUpdated().getValue());
        this.tags = new UniqueTagList();
        this.tags.add(new Tag(gTask.getCompleted() == null ? "Incomplete" : "Complete"));
    }

    @Override
    public Name getName() {
        return this.name;
    }

    @Override
    public Description getDescription() {
        return this.description;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return this.startDateTime;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public Date getUpdated() {
        return this.lastUpdated;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }
}
