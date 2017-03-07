package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.StartDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.Description;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(TestPerson personToCopy) {
        this.person = new TestPerson(personToCopy);
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new EndDateTime(address));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Description(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new StartDateTime(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
