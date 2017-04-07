package werkbook.task.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import werkbook.task.commons.core.CommandTexts;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.commons.events.ui.NewResultAvailableEvent;
import werkbook.task.commons.util.FxViewUtil;
import werkbook.task.logic.Logic;
import werkbook.task.logic.commands.CommandResult;
import werkbook.task.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final int SUGGESTION_COUNT = 5;

    private final Logic logic;

    @FXML
    private ComboBox<String> commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        commandTextField.setVisibleRowCount(SUGGESTION_COUNT);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);

        //@@author A0140462R
        Platform.runLater(() -> {
            commandTextField.setEditable(true);
            if (commandTextField.getEditor() instanceof ComboBoxPopupControl.FakeFocusTextField) {
                ((ComboBoxPopupControl.FakeFocusTextField) commandTextField.getEditor()).setFakeFocus(true);
            }
        });

        commandTextField.setOnKeyReleased(event -> {
            commandTextField.show();
            String selected;
            switch(event.getCode()) {
            case ENTER:
                handleCommandInputChanged();
                break;
            case DOWN:
            case UP:
                selected = commandTextField.getSelectionModel().getSelectedItem().toString();
                commandTextField.getEditor().positionCaret(selected.length() + 1);
                break;
            default:
                handleInputMethodTextChanged();
                break;
            }
        });
    }
    //@@author
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getEditor().getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.getEditor().clear();
            commandTextField.getEditor().setText("");
            commandTextField.getSelectionModel().clearSelection();
            commandTextField.hide();
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getEditor().getText());
            commandTextField.hide();
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
    //@@author A0140462R
    /**
     * Searches through list of available commands and displays the commands containing
     * the user input in a dropdown list.
     */
    private void handleInputMethodTextChanged() {
        String userInput = new String(commandTextField.getEditor().getText());
        int initialCaretPosition = commandTextField.getEditor().getCaretPosition(); //saves the caret position

        commandTextField.getItems().clear();
        ArrayList<String> suggestions = new ArrayList<String>();
        for (String s : CommandTexts.COMMAND_TEXT_LIST) {
            if (s.contains(userInput)) {
                suggestions.add(s);
            }
        }
        commandTextField.getItems().addAll(suggestions);
        commandTextField.getEditor().setText(userInput);
        commandTextField.getEditor().positionCaret(initialCaretPosition); //set caret position back
        commandTextField.hide();       //hides the dropdown list so that the visible row count can be updated
        commandTextField.setVisibleRowCount(suggestions.size());
        if (suggestions.size() > 0) {
            commandTextField.show();
        }
    }

    //@@author
    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

    public void focusOnTextField() {
        commandTextField.requestFocus();
        setStyleToIndicateCommandSuccess();
    }
}
