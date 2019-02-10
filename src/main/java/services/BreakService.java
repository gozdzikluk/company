package services;

import rest.models.Break;
/**
 * BreakService interface is using to connect with BreakDao class to manage break in database.
 * It allow to add breaks in workdays.
 *
 * @author Lukasz Gozdziewski
 */
public interface BreakService {
    /**
     * This method is used to add new break to database and set start break time.
     *
     * @param br break object to save in database
     * @return int it returns id of new record from database.
     */
    int addBreak(Break br);

    /**
     * This method is used to set end of break.
     * Method calculate and set time of break and save end time.
     *
     * @param br object to update in database
     * @return Break it return break object from database with all parameters (start, end, time, workScheduleId).
     */
    Break endBreak(Break br);

}
