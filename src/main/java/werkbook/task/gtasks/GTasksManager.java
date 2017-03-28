//@@author A0162266E
package werkbook.task.gtasks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.google.api.services.tasks.TasksScopes;

import werkbook.task.commons.core.ComponentManager;
import werkbook.task.gtasks.exceptions.NoCredentialsException;
import werkbook.task.model.ReadOnlyTaskList;

public class GTasksManager extends ComponentManager implements GTasks {

    /** Application name. */
    private static final String APPLICATION_NAME =
        "Werkbook";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/werkbook");

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
     * at ~/.credentials/werkbook
     */
    private static final List<String> SCOPES =
        Arrays.asList(TasksScopes.TASKS_READONLY);

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
            login();
        }
    }

    @Override
    public void login() throws IOException {
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
    public void logout() throws NoCredentialsException {
        // Delete credentials from data store directory
        File dataStoreDirectory = DATA_STORE_FACTORY.getDataDirectory();
        if (dataStoreDirectory.list().length == 0) {
            throw new NoCredentialsException("No user logged in");
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
    public Optional<ReadOnlyTaskList> sync(ReadOnlyTaskList taskList) {
        // TODO Auto-generated method stub
        return null;
    }

}
