package db.dao;

import nu.studer.sample.tables.Employees;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import rest.models.User;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.jooq.SQLDialect.POSTGRES;

/**
 * AuthDao is statless implementation of interface IAuthDao and is using to getting users from table employee in database.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class AuthDao implements IAuthDao {
    /**
     * Keeps instance contex of application.
     */
    private Context context = new InitialContext();
    /**
     * Keeps dataSource instance to connect with Payara's connection pool named jdbc/_firmaResource.
     */
    private DataSource dataSource = (DataSource)
            context.lookup("jdbc/_firmaResource");
    /**
     * Keeps logger instance.
     */
    protected final Logger log = Logger.getLogger(getClass().getName());

    /**
     * Empty constructor.
     */
    public AuthDao() throws NamingException {
    }

    /**
     * This method is used to get users's credentials from table employees in database.
     *
     * @param login user's login
     * @return User user from database which have login from method param.
     */
    @Override
    public User getUser(String login) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            Record record = create.select().from(Employees.EMPLOYEES)
                    .where(Employees.EMPLOYEES.LOGIN.eq(login))
                    .fetchOne();

            User user = record.into(User.class);
            log.info("pobrano uzytkownika o loginie=" + login);

            return user;
        } catch (SQLException e) {
            log.warning("Blad podczas ladowania uzytkownika o loginie=" + login + " error=" + e.getSQLState());
            return null;
        }

    }
}
