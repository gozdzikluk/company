package services;

import rest.models.WorkSchedule;
import rest.models.WorkScheduleSummary;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * WorkScheduleService interface is using to manage workSchedule in application.
 * This is CRUD for object workSchedule.
 *
 * @author Lukasz Gozdziewski
 */
public interface WorkScheduleService {
    /**
     * This method is used to get list of workSchedule from database by employee id.
     *
     * @param id employee's id to get from database
     * @return List<WorkSchedule> list of all matching records to the given employee ID.
     */
    List<WorkSchedule> getWorkScheduleByEmployeeId(int id) throws SQLException;

    /**
     * This method is used to add new Address to database.
     *
     * @param id object to save in database
     * @param from object to save in database
     * @param to object to save in database
     * @return boolean true if success or false if fails.
     */
    List<WorkSchedule> getWorkScheduleByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) throws SQLException;

    /**
     * This method is used to add new Address to database.
     *
     * @param id object to save in database
     * @param from object to save in database
     * @param to object to save in database
     * @return boolean true if success or false if fails.
     */
    WorkScheduleSummary getSummaryByid(int id, Timestamp from, Timestamp to) throws SQLException;

    /**
     * This method is used to get summary for department.
     *
     * @param id department's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    WorkScheduleSummary getSummaryByDepartmentId(int id, Timestamp from, Timestamp to) throws SQLException;

    /**
     * This method is used to overtimes for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getOvertimeByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException;

    /**
     * This method is used to get delays for emloyee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getDelaysByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException;

    /**
     * This method is used to get all vacations days for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getVacationByEmployeeId(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to get all days with sick leave for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getSickLeaveByEmployeeId(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to get all days with deficit from database by employee Id.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getDeficitByEmployeeId(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @return List all matching records from database.
     */
    List<WorkSchedule> getToAcceptByEmployeeId(int id);

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    List<WorkSchedule> getToAcceptByEmployeeIdFromTo(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id department's id to get
     * @return List all matching records from database.
     */
    List<WorkSchedule> getToAcceptByDepartmentId(int id);

    /**
     * This method is used to get accepted days from database.
     *
     * @param id object to save in database
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    List<WorkSchedule> getAcceptedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to get rejected days from database.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    List<WorkSchedule> getRejectedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to);

    /**
     * This method is used to add new workSchedule object to database.
     *
     * @param workSchedule object to save in database
     * @return Integer id of new record in database.
     */
    Integer addWorkSchedule(WorkSchedule workSchedule);

    /**
     * This method is used to accept/reject delegation/sick leave/vacation.
     *
     * @param workSchedule object to save in database
     * @return WorkSchedule object from database with all parameters.
     */
    WorkSchedule acceptRejectWorkSchedule(WorkSchedule workSchedule);

    /**
     * This method is used to set start of workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    boolean setStart(WorkSchedule workSchedule);

    /**
     * This method is used to set end of the workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    boolean setEnd(WorkSchedule workSchedule);
}
