package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * NotFoundExceptionMapper is used to map NotFoundexception to Response by toResponse method.
 *
 * @author Lukasz Gozdziewski
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundRecordException> {

    @Override
    public Response toResponse(NotFoundRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.NO_RECORD_FOUND.name()
        );

        return Response.status(Response.Status.BAD_REQUEST).
                entity(errorMessage).
                build();
    }
}
