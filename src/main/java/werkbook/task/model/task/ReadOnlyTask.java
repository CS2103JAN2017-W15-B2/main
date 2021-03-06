package werkbook.task.model.task;

import java.util.Date;

import werkbook.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Description getDescription();
    StartDateTime getStartDateTime();
    EndDateTime getEndDateTime();
    Date getUpdated();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime()))
                && other.getUpdated().equals(this.getUpdated());
    }

    //@@author A0139903B
    /**
     * Formats the task as text, showing task details if present
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + " ");
        getTags().forEach(builder::append);
        builder.append(getDescription().toString().isEmpty() ? "" : "\nDescription: " + getDescription())
            .append(!getStartDateTime().isPresent() ? "" : "\nFrom: " + getStartDateTime())
            .append(!getEndDateTime().isPresent() ? "" : getStartDateTime().isPresent() ? " To: " : " By: ")
            .append(!getEndDateTime().isPresent() ? "" : getEndDateTime())
            .append("\nLast Updated: " + getUpdated());
        return builder.toString();
    }
    //@@author

}
