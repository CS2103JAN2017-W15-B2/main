package werkbook.task.testutil;

import java.time.Clock;
import java.util.Date;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
        this.task.setLastUpdated(new Date());
    }

    public TaskBuilder(Clock clock) {
        this.task = new TestTask();
        this.task.setLastUpdated(Date.from(clock.instant()));
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }
    public TaskBuilder(TestTask taskToCopy, Clock clock) {
        this.task = new TestTask(taskToCopy);
        this.task.setLastUpdated(Date.from(clock.instant()));
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag : tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withStartDateTime(String startDateTime) throws IllegalValueException {
        this.task.setStartDateTime(new StartDateTime(startDateTime));
        return this;
    }

    public TaskBuilder withEndDateTime(String endDateTime) throws IllegalValueException {
        this.task.setEndDateTime(new EndDateTime(endDateTime));
        return this;
    }

    public TaskBuilder withLastUpdated(String lastUpdated) throws IllegalValueException {
        this.task.setLastUpdated(new Date(Long.parseLong(lastUpdated)));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
