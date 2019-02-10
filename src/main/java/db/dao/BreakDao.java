package db.dao;

import nu.studer.sample.tables.records.BreakRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import rest.models.Break;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jooq.SQLDialect.POSTGRES;

/**
 * BreakDao is Stateless implementation of interface IBreakDao and is using to connect with table break in database.
 * It allow to add breaks in workdays.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class BreakDao implements IBreakDao {
    /**
     * Keeps logger instance.
     */
    protected final Logger log = Logger.getLogger(getClass().getName());
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
     * Empty constructor.
     */
    public BreakDao() throws NamingException {
    }
    /**
     * This method is used to add new break to database and set start break time.
     *
     * @param br object to save in database
     * @return int it returns id of new record
     */
    @Override
    public int addBreak(rest.models.Break br) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            BreakRecord breakRecord = create.newRecord(nu.studer.sample.tables.Break.BREAK, br);
            breakRecord.store();

            log.log(Level.INFO, "Poprawnie dodano przerwę o Id= " + breakRecord.getId());
            return breakRecord.getId();
        }
    }
    /**
     * This method is used to set end of break.
     *
     * @param br object to update in database
     * @return Break it return break object from database with all parameters.
     */
    @Override
    public Break endBreak(Break br) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(nu.studer.sample.tables.Break.BREAK)
                    .set(nu.studer.sample.tables.Break.BREAK.END, Time.valueOf(br.getEnd()))
                    .set(nu.studer.sample.tables.Break.BREAK.TIME, br.getTime())
                    .where(nu.studer.sample.tables.Break.BREAK.ID.eq(br.getId()))
                    .execute();
        }
        return br;
    }
    /**
     * This method is used to get break object from database.
     *
     * @param id to get from database
     * @return Break it returns Break object from database with all parameters.
     */
    @Override
    public Break getById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            Break brDB = create.select()
                    .from(nu.studer.sample.tables.Break.BREAK)
                    .where(nu.studer.sample.tables.Break.BREAK.ID.eq(id))
                    .fetchOne()
                    .into(Break.class);
            return brDB;
        }
    }
    /**
     * This method is used to get break from database by work schedule id.
     *
     * @param id work schedule id to filter from database.
     * @return List<Break> it returns list of break object.
     */
    @Override
    public List<Break> getByWorkScheduleId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<Break> breaks = create.select()
                    .from(nu.studer.sample.tables.Break.BREAK)
                    .where(nu.studer.sample.tables.Break.BREAK.WORKSCHEDULEID.eq(id))
                    .fetchInto(Break.class);

            return breaks;
        }
    }
}
