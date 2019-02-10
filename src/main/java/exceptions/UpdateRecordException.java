package exceptions;
/**
 * UpdateRecord exceptiona, it throws when update record in database fails.
 *
 * @author Lukasz Gozdziewski
 */
public class UpdateRecordException extends RuntimeException {
    public UpdateRecordException(String message)
    {
        super(message);
    }
}