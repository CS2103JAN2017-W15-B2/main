package werkbook.task.gtasks.exceptions;

/**
 * Represents an error which occurs when there is a lack of credentials to google oauth2 api.
 */
public class NoCredentialsException extends Exception {
   /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoCredentialsException(String message) {
           super(message);
    }
}
