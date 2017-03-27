//@@author A0162266E
package werkbook.task.logic.commands;

import werkbook.task.gtasks.exceptions.NoCredentialsException;
import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Terminates the program.
 */
public class GLogoutCommand extends Command {

    public static final String COMMAND_WORD = "glogout";

    public static final String GOOGLE_LOGIN_ACKNOWLEDGEMENT = "Logged out of Google";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            gtasks.logout();
        } catch (NoCredentialsException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_LOGIN_ACKNOWLEDGEMENT);
    }
}
