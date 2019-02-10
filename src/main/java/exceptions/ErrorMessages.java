package exceptions;
/**
 * ErrorMessages ENUM is using to get error message.
 *
 * @author Lukasz Gozdziewski
 */
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    COULD_NOT_CREATE_USER_PROFILE("Could not create user profile"),
    COULD_NOT_UPDATE_USER_PROFILE("Could not update user profile"),
    COULD_NOT_DELETE_USER_PROFILE("Could not delete user profile"),
    NO_RECORD_FOUND("No record found for provided id"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Something went wrong. Please repeat this operation later."),
    COULD_NOT_CREATE_USER("Something went wrong. Cant create user, please check data and repeat this operation later."),
    COULD_NOT_CREATE_RECORD("Something went wrong. Cant create record, please check data and repeat this operation later."),
    COULD_NOT_CREATE_ADDRESS_RECORD("Something went wrong. Cant create record, please check data and repeat this operation later."),
    COULD_NOT_UPDATE_RECORD("Something went wrong. Cant create record, please check data and repeat this operation later.");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
