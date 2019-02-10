package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * AddRecordExceptionMapper is used to map exception to Response by toResponse method.
 *
 * @author Lukasz Gozdziewski
 */
@Provider
public class AddRecordExceptionMapper implements ExceptionMapper<AddRecordException> {

    @Override
    public Response toResponse(AddRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.COULD_NOT_CREATE_RECORD.name()
        );

        return Response.status(Response.Status.BAD_REQUEST).
                entity(errorMessage).
                build();
    }
}
