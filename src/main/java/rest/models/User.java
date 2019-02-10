package rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * User REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    /**
     * Keeps user's id.
     */
    private int id;
    /**
     * Keeps user's login.
     */
    private String login;
    /**
     * Keeps user's password.
     */
    private String password;
    /**
     * Keeps user's type.
     */
    private String usertype;
}
