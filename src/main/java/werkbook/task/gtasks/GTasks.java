//@@author A0162266E
package werkbook.task.gtasks;

import java.io.IOException;
import java.util.Optional;

import werkbook.task.gtasks.exceptions.CredentialsException;
import werkbook.task.model.ReadOnlyTaskList;

public interface GTasks {
    /** Retrieves user credentials from Google
     * @throws IOException 
     * @throws CredentialsException */
    void login() throws IOException, CredentialsException;

    /** Wipe user credentials from storage
     * @throws CredentialsException */
    void logout() throws CredentialsException;

    /** Syncs current tasks with the ones stored on Google Tasks
     * @throws IOException
     * @throws CredentialsException */
    Optional<ReadOnlyTaskList> sync(ReadOnlyTaskList taskList) throws IOException, CredentialsException;
}
