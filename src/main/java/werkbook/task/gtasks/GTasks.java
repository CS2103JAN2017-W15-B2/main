//@@author A0162266E
package werkbook.task.gtasks;

import java.io.IOException;
import java.util.Optional;

import werkbook.task.gtasks.exceptions.NoCredentialsException;
import werkbook.task.model.ReadOnlyTaskList;

public interface GTasks {
    /** Retrieves user credentials from Google 
     * @throws IOException */
    void login() throws IOException;
    
    /** Wipe user credentials from storage 
     * @throws NoCredentialsException */
    void logout() throws NoCredentialsException;
    
    /** Syncs current tasks with the ones stored on Google Tasks */
    Optional<ReadOnlyTaskList> sync(ReadOnlyTaskList taskList);
}
