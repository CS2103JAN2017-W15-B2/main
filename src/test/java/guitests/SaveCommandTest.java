//@@author A0162266E
package guitests;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import werkbook.task.logic.commands.SaveCommand;

public class SaveCommandTest extends TaskListGuiTest {


    @Test
    public void save_invalidCommand_failure() {
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    // Checks invalidPath only for Windows system
    public void save_invalidPath_failure() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            commandBox.runCommand("save #ayy*");
            assertResultMessage(SaveCommand.MESSAGE_INVALID_PATH);
        } else {
            assert(true);
        }
    }

    @Test
    public void save_nonExistentFolder_failure() {
        commandBox.runCommand("save src\\test\\data\\sandbox\\some_folder");
        assertResultMessage(SaveCommand.MESSAGE_FOLDER_NOT_EXIST);
    }


    @Test
    public void save_notDirectory_failure() {
        File newFile = new File("src\\test\\data\\sandbox\\newFile");
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandBox.runCommand("save src\\test\\data\\sandbox\\newFile");
        assertResultMessage(SaveCommand.MESSAGE_NOT_A_DIRECTORY);
    }

    @Test
    public void save_validDirectory_success() {
        File newFolder = new File("src\\test\\data\\sandbox\\newFolder");
        newFolder.mkdir();
        commandBox.runCommand("save src\\test\\data\\sandbox\\newFolder");
        assertResultMessage(SaveCommand.MESSAGE_SUCCESS);
    }

}
