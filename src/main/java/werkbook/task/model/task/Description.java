package werkbook.task.model.task;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task description in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description can be empty and "
            + "can contain any characters";
    public static final String DESCRIPTION_VALIDATION_REGEX = "(?s).*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.value.equals(((Description) other).value)); // state
                                                                            // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
