//@@author A0162266E
package werkbook.task.logic.commands;

import java.io.IOException;

import werkbook.task.gtasks.exceptions.CredentialsException;
import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Terminates the program.
 */
public class GLoginCommand extends Command {

    public static final String COMMAND_WORD = "glogin";

    public static final String GOOGLE_LOGIN_ACKNOWLEDGEMENT = "Logged in to Google";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            gtasks.login();
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        } catch (CredentialsException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(GOOGLE_LOGIN_ACKNOWLEDGEMENT);
    }
}
