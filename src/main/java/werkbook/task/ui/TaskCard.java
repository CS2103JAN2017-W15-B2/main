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
    @FXML
    private Label prefixStartDateTime;
    @FXML
    private Label prefixEndDateTime;

    public TaskCard(ReadOnlyTask task, int displayedIndex, int selectionIndex) {
        super(FXML);
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().toString());
        initTags(task);

        setStrikethrough(task);
        setExpansion(displayedIndex, selectionIndex);
        setDateTime(task);
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

        // If start date time is not present, then remove
        if (!task.getStartDateTime().isPresent()) {
            startDatePrefix = "";
            endDatePrefix = "By: ";
            titledPaneHeader.getChildren().remove(headerStartDateTime);
            startDateTime.setText("");

            // If end date time is not present, then remove
            if (!task.getEndDateTime().isPresent()) {
                endDatePrefix = "";
                titledPaneHeader.getChildren().remove(headerEndDateTime);
                endDateTime.setText("");
            }
        }

        // Add prefix
        prefixStartDateTime.setText(startDatePrefix);
        prefixEndDateTime.setText(endDatePrefix);

        // Only set date time if present
        if (task.getStartDateTime().isPresent()) {
            startDateTime.setText(task.getStartDateTime().toString());
            headerStartDateTime.setText(startDatePrefix + task.getStartDateTime().getPrettyString());
        }

        if (task.getEndDateTime().isPresent()) {
            endDateTime.setText(task.getEndDateTime().toString());
            headerEndDateTime.setText(endDatePrefix + task.getEndDateTime().getPrettyString());
        }

        // If expanded, remove header to save space
        if (titledPane.isExpanded()) {
            titledPaneHeader.getChildren().remove(headerStartDateTime);
            titledPaneHeader.getChildren().remove(headerEndDateTime);
        }
    }
//@@author
    private void initTags(ReadOnlyTask task) {
        String tagName = task.getTags().asObservableList().get(0).tagName;
        Label tag = new Label(tagName);

        if (tagName.equals("Complete")) {
            tag.getStyleClass().add("completeLabel");
        } else {
            tag.getStyleClass().remove("completeLabel");
        }
        tags.getChildren().add(tag);
        //task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
