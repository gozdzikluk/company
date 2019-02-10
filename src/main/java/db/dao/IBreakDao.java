package db.dao;

import rest.models.Break;

import java.sql.SQLException;
import java.util.List;
/**
 * IBreakDao interface is using to connect with table break in database.
 * It allow to add breaks in workdays.
 *
 * @author Lukasz Gozdziewski
 */
public interface IBreakDao {
    /**
     * This method is used to add new break to database and set start break time.
     *
     * @param br object to save in database
     * @return int it returns id of new record
     */
    int addBreak(Break br) throws SQLException;

    /**
     * This method is used to set end of break.
     *
     * @param br object to update in database
     * @return Break it return break object from database with all parameters.
     */
    Break endBreak(Break br) throws SQLException;
    /**
     * This method is used to get break object from database.
     *
     * @param id to get from database
     * @return Break it returns Break object from database with all parameters.
     */
    Break getById(int id) throws SQLException;
    /**
     * This method is used to get break from database by work schedule id.
     *
     * @param id work schedule id to filter from database.
     * @return List<Break> it returns list of break object.
     */
    List<Break> getByWorkScheduleId(int id) throws SQLException;
}
