package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import werkbook.task.commons.core.CommandTexts;
import werkbook.task.ui.CommandBox;

public class CommandBoxTest extends TaskListGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private ArrayList<String> successStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.remove(CommandBox.DEFAULT_STYLE_CLASS);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);

        // build style class for success
        successStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        successStyleOfCommandBox.remove(CommandBox.DEFAULT_STYLE_CLASS);
        successStyleOfCommandBox.add(CommandBox.SUCCESS_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBox.getCommandInput());
        assertEquals(successStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(successStyleOfCommandBox, commandBox.getStyleClass());
    }

    //@@author A0140462R
    @Test
    public void commandBox_checkAutocompleteSuccess() {
        commandBox.pressAKey();
        List<String> expectedItemList = new ArrayList<String>(Arrays.asList(CommandTexts.ADD_COMMAND_WORD,
                                                              CommandTexts.CLEAR_COMMAND_WORD,
                                                              CommandTexts.MARK_COMMAND_WORD,
                                                              CommandTexts.SAVE_COMMAND_WORD));

        assertEquals(expectedItemList , commandBox.getItemList());
    }

    @Test
    public void commandBox_checkAutocompleteWithNoMatchingCommands() {
        commandBox.pressZKey();
        List<String> expectedItemList = new ArrayList<String>();

        assertEquals(expectedItemList, commandBox.getItemList());
    }
}
