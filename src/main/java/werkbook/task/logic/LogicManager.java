package werkbook.task.logic;

import java.time.Clock;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import werkbook.task.commons.core.ComponentManager;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.gtasks.GTasks;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.CommandResult;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.logic.parser.Parser;
import werkbook.task.model.Model;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Storage storage;
    private final GTasks gtasks;
    private final Clock clock;

    public LogicManager(Model model, Storage storage, GTasks gtasks, Clock clock) {
        this.model = model;
        this.storage = storage;
        this.gtasks = gtasks;
        this.clock = clock;
        Command.setClock(clock);
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setStorage(storage);
        command.setGTasks(gtasks);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
