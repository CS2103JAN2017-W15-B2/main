package werkbook.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import werkbook.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
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
    private GridPane gridPane;


    public TaskCard(ReadOnlyTask task, int displayedIndex, int selectionIndex) {
        super(FXML);
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().toString());
        startDateTime.setText(task.getStartDateTime().getPrettyString());
        endDateTime.setText(task.getEndDateTime().getPrettyString());
        initTags(task);

        titledPane.setAnimated(true);
        titledPane.setExpanded(false);

        if (selectionIndex + 1 == displayedIndex) {
            titledPane.setExpanded(true);
            gridPane.setPrefHeight(titledPane.getPrefHeight());
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
