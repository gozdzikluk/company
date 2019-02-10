package db.dao;

import rest.models.User;
/**
 * IAuthDao interface is using to get user's credentials from database.
 *
 * @author Lukasz Gozdziewski
 */
public interface IAuthDao {
    /**
     * This method is used to get users's credentials from database.
     *
     * @param login user's login
     * @return User user from database which have login from method param.
     */
    User getUser(String login);
}
