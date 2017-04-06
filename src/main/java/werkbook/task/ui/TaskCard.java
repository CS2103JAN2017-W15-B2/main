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
        startDateTime.setText(task.getStartDateTime().toString());
        endDateTime.setText(task.getEndDateTime().toString());
        initTags(task);

        headerStartDateTime.setText(task.getStartDateTime().getPrettyString());
        headerEndDateTime.setText(task.getEndDateTime().getPrettyString());

        // If start date time is not present, then remove
        if (!task.getStartDateTime().isPresent()) {
            titledPaneHeader.getChildren().remove(1);
            // If end date time is not present, then remove
            if (!task.getEndDateTime().isPresent()) {
                titledPaneHeader.getChildren().remove(1);
            }
        }

        // Set strikethrough if task is complete
        if (task.getTags().asObservableList().get(0).tagName.equals("Complete")) {
            name.setStrikethrough(true);
        }

        titledPane.setExpanded(false);
        if (selectionIndex == displayedIndex - 1) {
            titledPane.setExpanded(true);
        }

    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
