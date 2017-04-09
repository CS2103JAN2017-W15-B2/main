//@@author A0140462R
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import werkbook.task.commons.core.CommandTexts;
import werkbook.task.logic.commands.ListCommand;
import werkbook.task.testutil.TestTask;

public class ListCompleteCommandTest extends TaskListGuiTest {

    public static final String LIST_COMPLETE_COMMAND_WORD = "list complete";

    @Test
    public void list_completedTasksSuccess() {

        assertListCompleteResult(LIST_COMPLETE_COMMAND_WORD, td.elle, td.fiona, td.george);

        //delete one then try to list again
        commandBox.runCommand(CommandTexts.DELETE_COMMAND_WORD + " 1");
        assertListCompleteResult(LIST_COMPLETE_COMMAND_WORD, td.fiona, td.george);
    }

    @Test
    public void list_completedTasksWithEmptyList() {
        commandBox.runCommand(CommandTexts.CLEAR_COMMAND_WORD);
        assertListCompleteResult(LIST_COMPLETE_COMMAND_WORD);
    }

    private void assertListCompleteResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);

        assertResultMessage(ListCommand.MESSAGE_SHOW_COMPLETE_SUCCESS);

        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
