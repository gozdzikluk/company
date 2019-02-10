package exceptions;
/**
 *  NotFoundRecordException exception.
 *
 * @author Lukasz Gozdziewski
 */
public class NotFoundRecordException extends RuntimeException {
    public NotFoundRecordException(String message)
    {
        super(message);
    }
}
