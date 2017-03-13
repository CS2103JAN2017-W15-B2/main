package werkbook.task.testutil;

import java.util.Optional;

import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.StartDateTime;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Optional<StartDateTime> startDateTime;
    private Optional<EndDateTime> endDateTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.description = taskToCopy.getDescription();
        this.startDateTime = taskToCopy.getStartDateTime();
        this.endDateTime = taskToCopy.getEndDateTime();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDateTime(Optional<StartDateTime> startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Optional<EndDateTime> endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Optional<StartDateTime> getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Optional<EndDateTime> getEndDateTime() {
        return endDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().taskName + " ");
        sb.append("d/" + this.getDescription().toString() + " ");
        sb.append("s/" + this.getStartDateTime().get().toString() + " ");
        sb.append("e/" + this.getEndDateTime().get().toString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
