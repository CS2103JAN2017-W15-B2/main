package werkbook.task;

import java.io.IOException;
import java.time.Clock;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import werkbook.task.commons.core.Config;
import werkbook.task.commons.core.EventsCenter;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.commons.core.Version;
import werkbook.task.commons.events.ui.ExitAppRequestEvent;
import werkbook.task.commons.exceptions.DataConversionException;
import werkbook.task.commons.util.ConfigUtil;
import werkbook.task.commons.util.StringUtil;
import werkbook.task.gtasks.GTasks;
import werkbook.task.gtasks.GTasksManager;
import werkbook.task.logic.Logic;
import werkbook.task.logic.LogicManager;
import werkbook.task.model.Model;
import werkbook.task.model.ModelManager;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.TaskList;
import werkbook.task.model.UserPrefs;
import werkbook.task.model.util.SampleDataUtil;
import werkbook.task.storage.Storage;
import werkbook.task.storage.StorageManager;
import werkbook.task.ui.Ui;
import werkbook.task.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(5, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected GTasks gtasks;
    protected Config config;
    protected UserPrefs userPrefs;
    protected Clock clock;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Werkbook ]===========================");
        super.init();

        if (clock == null) {
            clock = Clock.systemDefaultZone();
        }

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config);

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        gtasks = new GTasksManager();

        logic = new LogicManager(model, storage, gtasks, clock);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskList> taskListOptional;
        ReadOnlyTaskList initialData;
        try {
            taskListOptional = storage.readTaskList();
            if (!taskListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskList");
            }
            initialData = taskListOptional.orElseGet(SampleDataUtil::getSampleTaskList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskList");
            initialData = new TaskList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskList");
            initialData = new TaskList();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Werkbook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Werkbook ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
