//@@author A0162266E
package werkbook.task.gtasks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;

import werkbook.task.commons.core.ComponentManager;
import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.gtasks.exceptions.CredentialsException;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.UniqueTaskList;

public class GTasksManager extends ComponentManager implements GTasks {

    /** Application name. */
    private static final String APPLICATION_NAME =
        "Werkbook";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        "./credentials");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by werkbook.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at /credentials
     */
    private static final List<String> SCOPES =
        Arrays.asList(TasksScopes.TASKS);

    /** Global instance of client secrets */
    private static GoogleClientSecrets clientSecrets;

    /** Credentials for interacting with Google API */
    private Credential credential;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public GTasksManager() throws IOException {
        // Load client secrets.
        InputStream in =
            GTasksManager.class.getResourceAsStream("/client_secret.json");
        clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        if (DATA_STORE_FACTORY.getDataDirectory().list().length != 0) {
            try {
                login();
            } catch (CredentialsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void login() throws IOException, CredentialsException {
        if (credential != null) {
            throw new CredentialsException("You are already logged in.");
        }
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
    }

    @Override
    public void logout() throws CredentialsException {
        // Delete credentials from data store directory
        File dataStoreDirectory = DATA_STORE_FACTORY.getDataDirectory();
        if (dataStoreDirectory.list().length == 0) {
            throw new CredentialsException("You are not logged in");
        }
        for (File file : dataStoreDirectory.listFiles()) {
            file.delete();
        }
        credential = null;
        try {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UniqueTaskList retrieve() throws IOException, CredentialsException {
        if (credential == null) {
            throw new CredentialsException("You are not logged in");
        }

        // Retrieve user's tasklists
        Tasks service = (new Tasks.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
        TaskLists taskLists = service.tasklists().list()
                .execute();

        // Get the tasklist with the name werkbook
        // Creates one if it does not exist
        TaskList gTaskList;
        exist: {
            for (TaskList tl : taskLists.getItems()) {
                if (tl.getTitle().equals("Werkbook")) {
                    gTaskList = tl;
                    break exist;
                }
            }
            gTaskList = service.tasklists().insert(
                    (new TaskList()).setTitle("Werkbook"))
                .execute();
        }

        // Retrieve tasks from the tasklist
        List<Task> gTasks = service.tasks().list(gTaskList.getId())
                .execute()
                .getItems();

        UniqueTaskList gTaskAdaptedTaskList = new UniqueTaskList();
        if (gTasks == null) {
        	return gTaskAdaptedTaskList;
        }
        for (Task t : gTasks) {
            try {
                gTaskAdaptedTaskList.add(new werkbook.task.model.task.Task(new GTaskToTaskAdapter(t)));
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return gTaskAdaptedTaskList;
    }

    @Override
    public void update(ReadOnlyTaskList taskList) throws IOException, CredentialsException {
        if (credential == null) {
            throw new CredentialsException("You are not logged in");
        }

        // Retrieve user's tasklists
        Tasks service = (new Tasks.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
        TaskLists taskLists = service.tasklists().list()
                .execute();

        // Get the tasklist with the name werkbook
        // Deletes it and create a new one if it exist
        // Creates one if it does not exist
        TaskList gTaskList;
        for (TaskList tl : taskLists.getItems()) {
            if (tl.getTitle().equals("Werkbook")) {
                service.tasklists().delete(tl.getId())
                    .execute();
            }
        }
        gTaskList = service.tasklists().insert(
                (new TaskList()).setTitle("Werkbook"))
                .execute();

        // Add tasks from Werkbook to Google Tasks
        for (ReadOnlyTask t : taskList.getTaskList()) {
            try {
                System.out.println(t.toString());
                service.tasks().insert(gTaskList.getId(), TaskToGTaskAdapter.getGTask(t))
                .execute();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
    }
}
