//@@author A0162266E
package werkbook.task.logic.commands;

import werkbook.task.gtasks.exceptions.CredentialsException;
import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Terminates the program.
 */
public class GLogoutCommand extends Command {

    public static final String COMMAND_WORD = "glogout";

    public static final String GOOGLE_LOGOUT_ACKNOWLEDGEMENT = "Logged out of Google";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            gtasks.logout();
        } catch (CredentialsException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_LOGOUT_ACKNOWLEDGEMENT);
    }
}
