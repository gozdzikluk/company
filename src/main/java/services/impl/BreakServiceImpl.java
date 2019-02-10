package services.impl;

import db.dao.IBreakDao;
import exceptions.AddRecordException;
import exceptions.ErrorMessages;
import exceptions.NotFoundRecordException;
import exceptions.UpdateRecordException;
import rest.models.Break;
import services.BreakService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;
/**
 * BreakServiceImpl is stateless implementation of BreakService interface and is using to connect with BreakDao class to manage break in database.
 * It allow to add breaks in workdays.
 *
 * @author Lukasz Gozdziewski
 */

@Stateless
public class BreakServiceImpl implements BreakService {

    @EJB
    IBreakDao iBreakDao;

    /**
     * This method is used to add new break to database and set start break time.
     *
     * @param br break object to save in database
     * @return int it returns id of new record from database.
     */
    @Override
    public int addBreak(Break br) {
        br.setStart(LocalTime.now());
        try {
            return iBreakDao.addBreak(br);
        } catch (SQLException e) {
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
        }
    }

    /**
     * This method is used to set end of break.
     * Method calculate and set time of break and save end time.
     *
     * @param br object to update in database
     * @return Break it return break object from database with all parameters (start, end, time, workScheduleId).
     */
    @Override
    public Break endBreak(Break br) {
        br.setEnd(LocalTime.now());
        Break breakFromDB;
        try {
            breakFromDB = iBreakDao.getById(br.getId());
        } catch (SQLException e) {
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        if (breakFromDB !=null){
            Integer time = ((int) MINUTES.between(breakFromDB.getStart(), br.getEnd()));
            br.setTime(time);
        }

        try {
            return iBreakDao.endBreak(br);
        } catch (SQLException e) {
            throw new UpdateRecordException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
        }
    }
}
