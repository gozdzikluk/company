package rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * LoggedUser REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUser {
    /**
     * Keeps logged user's id.
     */
    private Integer id;
    /**
     * Keeps logged user's login.
     */
    private String login;
    /**
     * Keeps logged user's type.
     */
    private String userType;
}
