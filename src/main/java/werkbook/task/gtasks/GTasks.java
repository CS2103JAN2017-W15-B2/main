package werkbook.task.gtasks;

import java.io.IOException;

import werkbook.task.gtasks.exceptions.CredentialsException;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.task.UniqueTaskList;

//@@author A0162266E
public interface GTasks {
    /** Retrieves user credentials from Google
     * @throws IOException
     * @throws CredentialsException */
    void login() throws IOException, CredentialsException;

    /** Wipe user credentials from storage
     * @throws CredentialsException */
    void logout() throws CredentialsException;

    /** Retrieves current tasks with the ones stored on Google Tasks
     * @throws IOException
     * @throws CredentialsException */
    UniqueTaskList retrieve() throws IOException, CredentialsException;

    /** Updates tasks stored on Google Tasks with current tasks
     * @throws IOException
     * @throws CredentialsException */
    void update(ReadOnlyTaskList taskList) throws IOException, CredentialsException;
}
