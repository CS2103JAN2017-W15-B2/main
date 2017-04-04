package werkbook.task.model.task;

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
                && other.getEndDateTime().equals(this.getEndDateTime()));
    }

    //@@author A0139903B
    /**
     * Formats the task as text, showing task details if present
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(getDescription().toString().isEmpty() ? "" : " Description: " + getDescription())
            .append(!getStartDateTime().isPresent() ? "" : " From: " + getStartDateTime())
            .append(!getEndDateTime().isPresent() ? "" : getStartDateTime().isPresent() ? " To: " : " By: ")
            .append(!getEndDateTime().isPresent() ? "" : getEndDateTime())
            .append(" Status: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    //@@author

}
