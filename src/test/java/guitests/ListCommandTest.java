//@@author A0140462R
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import werkbook.task.commons.core.CommandTexts;
import werkbook.task.commons.core.Messages;
import werkbook.task.logic.commands.ListCommand;
import werkbook.task.testutil.TestTask;

public class ListCommandTest extends TaskListGuiTest {

    public static final String LIST_COMPLETE_COMMAND_WORD = "list complete";
    public static final String LIST_INCOMPLETE_COMMAND_WORD = "list incomplete";

    @Test
    public void list_completedTasksSuccess() {
        assertListCommandResult(LIST_COMPLETE_COMMAND_WORD, td.elle, td.fiona, td.george);

        //delete one then try to list again
        commandBox.runCommand(CommandTexts.DELETE_COMMAND_WORD + " 1");
        assertListCommandResult(LIST_COMPLETE_COMMAND_WORD, td.fiona, td.george);
    }

    @Test
    public void list_completedTasksWithEmptyList() {
        commandBox.runCommand(CommandTexts.CLEAR_COMMAND_WORD);
        assertListCommandResult(LIST_COMPLETE_COMMAND_WORD);
    }

    @Test
    public void list_incompleteTasksSuccess() {
        assertListCommandResult(LIST_INCOMPLETE_COMMAND_WORD, td.alice, td.benson,
                                td.carl, td.daniel);

        //delete one then try to list again
        commandBox.runCommand(CommandTexts.DELETE_COMMAND_WORD + " 1");
        assertListCommandResult(LIST_INCOMPLETE_COMMAND_WORD, td.benson,
                                td.carl, td.daniel);
    }

    @Test
    public void list_incompleteTasksWithEmptyList() {
        commandBox.runCommand(CommandTexts.CLEAR_COMMAND_WORD);
        assertListCommandResult(LIST_INCOMPLETE_COMMAND_WORD);
    }

    @Test
    public void list_allTasksSuccess() {
        assertListCommandResult(CommandTexts.LIST_COMMAND_WORD, td.alice, td.benson, td.carl,
                                td.daniel, td.elle, td.fiona, td.george);

        //delete one then try to list again
        commandBox.runCommand(CommandTexts.DELETE_COMMAND_WORD + " 1");
        assertListCommandResult(CommandTexts.LIST_COMMAND_WORD, td.benson, td.carl,
                                td.daniel, td.elle, td.fiona, td.george);
    }

    @Test
    public void list_allTasksWithEmptyList() {
        commandBox.runCommand(CommandTexts.CLEAR_COMMAND_WORD);
        assertListCommandResult(CommandTexts.LIST_COMMAND_WORD);
    }

    private void assertListCommandResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);

        switch(command) {

        case LIST_COMPLETE_COMMAND_WORD:
            assertResultMessage(ListCommand.MESSAGE_SHOW_COMPLETE_SUCCESS);
            break;

        case LIST_INCOMPLETE_COMMAND_WORD:
            assertResultMessage(ListCommand.MESSAGE_SHOW_INCOMPLETE_SUCCESS);
            break;

        case CommandTexts.LIST_COMMAND_WORD:
            assertResultMessage(ListCommand.MESSAGE_SUCCESS);
            break;

        default:
            assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
