package exceptions;
/**
 * AddRecordException is exception which is throws when add new record to database fails.
 *
 * @author Lukasz Gozdziewski
 */
public class AddRecordException extends RuntimeException {
    public AddRecordException(String message)
    {
        super(message);
    }
}
