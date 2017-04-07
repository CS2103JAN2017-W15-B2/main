package werkbook.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import werkbook.task.model.task.ReadOnlyTask;

//@@author A0139903B
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Text name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane tags;
    @FXML
    private TitledPane titledPane;
    @FXML
    private VBox titledPaneHeader;
    @FXML
    private Label headerStartDateTime;
    @FXML
    private Label headerEndDateTime;

    public TaskCard(ReadOnlyTask task, int displayedIndex, int selectionIndex) {
        super(FXML);
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().toString());
        initTags(task);

        setDateTime(task);
        setStrikethrough(task);
        setExpansion(displayedIndex, selectionIndex);
    }

    /**
     * Set titled pane to be expanded if it is selected
     * @param displayedIndex index shown in title pane
     * @param selectionIndex index of task to be selected
     */
    private void setExpansion(int displayedIndex, int selectionIndex) {
        // Default is not expanded unless index is the same as selection
        titledPane.setExpanded(false);
        if (selectionIndex == displayedIndex - 1) {
            titledPane.setExpanded(true);
        }
    }

    /**
     * Strikes through the name of a task if it is completed
     * @param task task to be read from
     */
    private void setStrikethrough(ReadOnlyTask task) {
        // Set strike through if task is complete
        if (task.getTags().asObservableList().get(0).tagName.equals("Complete")) {
            name.setStrikethrough(true);
        }
    }

    /**
     * Sets the date time label in header if present, otherwise remove label to conserve space
     * @param task task to be read from
     */
    private void setDateTime(ReadOnlyTask task) {
        String startDatePrefix = "From: ";
        String endDatePrefix = "To:     ";

        // Prefix check
        if (!task.getStartDateTime().isPresent()) {
            startDatePrefix = "";
            endDatePrefix = "By: ";
        }

        startDateTime.setText(startDatePrefix + task.getStartDateTime().getPrettyString());
        headerStartDateTime.setText(startDatePrefix + task.getStartDateTime().getPrettyString());

        endDateTime.setText(endDatePrefix + task.getEndDateTime().getPrettyString());
        headerEndDateTime.setText(endDatePrefix + task.getEndDateTime().getPrettyString());

        // If start date time is not present, then remove
        if (!task.getStartDateTime().isPresent()) {
            titledPaneHeader.getChildren().remove(1);

            // If end date time is not present, then remove
            if (!task.getEndDateTime().isPresent()) {
                titledPaneHeader.getChildren().remove(1);
            }
        }
    }
//@@author
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
