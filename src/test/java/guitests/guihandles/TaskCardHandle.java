package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String ENDDATETIME_FIELD_ID = "#endDateTime";
    private static final String STARTDATETIME_FIELD_ID = "#startDateTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        System.out.println("Creating handle");
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartDateTime() {
        return getTextFromLabel(STARTDATETIME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getEndDateTime() {
        return getTextFromLabel(ENDDATETIME_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags.asObservableList().stream().map(tag -> tag.tagName).collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        System.out.println("Comparing");
        System.out.println("Name: " + getFullName() + " and " + task.getName().toString());
        System.out.println("Desc: " + getDescription() + " and " + task.getDescription().toString());
        System.out.println("End: " + getEndDateTime() + " and " + task.getEndDateTime().getPrettyString());
        System.out.println("Start: " + getStartDateTime() + " and " + task.getStartDateTime().getPrettyString());

        return getFullName().equals(task.getName().toString())
                && getDescription().equals(task.getDescription().toString())
                && getEndDateTime().equals(task.getEndDateTime().getPrettyString())
                && getStartDateTime().equals(task.getStartDateTime().getPrettyString())
                && getTags().equals(getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getDescription().equals(handle.getDescription())
                    && getEndDateTime().equals(handle.getEndDateTime())
                    && getStartDateTime().equals(handle.getStartDateTime())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStartDateTime();
    }
}
