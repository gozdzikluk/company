package db.dao;

import exceptions.AddRecordException;
import exceptions.ErrorMessages;
import nu.studer.sample.tables.Department;
import nu.studer.sample.tables.Employees;
import nu.studer.sample.tables.records.WorkScheduleRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import rest.models.WorkSchedule;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import static org.jooq.SQLDialect.POSTGRES;
/**
 * WorkScheduleImplDao is Stateless implementation of interface WorkScheduleDao and is using to connect with table workSchedule in database.
 * This is a CRUD interface for object workSchedule - day of work.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class WorkScheduleImplDao implements WorkscheduleDao {
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
    public WorkScheduleImplDao() throws NamingException {
    }
    /**
     * This method is used to get list of workSchedule from database by employee id.
     *
     * @param id employee's id to get from database
     * @return List<WorkSchedule> list of all matching records to the given employee ID.
     */
    @Override
    public List<WorkSchedule> getWorkScheduleByEmployeeId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .fetch()
                    .into(WorkSchedule.class);

            System.out.println("Work schedule size: " + workSchedules.size());

            return workSchedules;
        }
    }
    /**
     * This method is used to add new Address to database.
     *
     * @param id object to save in database
     * @param from object to save in database
     * @param to object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public List<WorkSchedule> getWorkScheduleByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .fetchInto(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to add new Address to database.
     *
     * @param id object to save in database
     * @param from object to save in database
     * @param to object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public List<WorkSchedule> getSummaryByid(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get summary for department.
     *
     * @param id department's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getSummaryBydepartmentid(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create
                    .select(Employees.EMPLOYEES.fields())
                    .select(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.fields())
                    .select(Department.DEPARTMENT.fields())
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .join(Employees.EMPLOYEES)
                    .on(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(Employees.EMPLOYEES.ID))
                    .join(Department.DEPARTMENT)
                    .on(Employees.EMPLOYEES.DEPARTMENT.eq(Department.DEPARTMENT.ID))
                    .where(Employees.EMPLOYEES.DEPARTMENT.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to overtimes for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getOvertimeByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.OVERHOURS.eq(true))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get delays for emloyee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getDelaysByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELAY.eq(true))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get all vacations days for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getVacationByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get all days with sick leave for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getSickLeaveByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.SICK_LEAVE.eq(true))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get all days with deficit from database by employee Id.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getDeficitByEmployeeId(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DEFICIT.gt(0))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getToAcceptByEmployeeId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true).or(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELEGATION.eq(true)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED.eq(false).and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED.eq(false)))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getToAcceptByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true).or(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELEGATION.eq(true)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED.eq(false).and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED.eq(false)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id department's id to get
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getToAcceptByDepartmentId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create
                    .select(Employees.EMPLOYEES.fields())
                    .select(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.fields())
                    .select(Department.DEPARTMENT.fields())
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .join(Employees.EMPLOYEES)
                    .on(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(Employees.EMPLOYEES.ID))
                    .join(Department.DEPARTMENT)
                    .on(Employees.EMPLOYEES.DEPARTMENT.eq(Department.DEPARTMENT.ID))
                    .where(Employees.EMPLOYEES.DEPARTMENT.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true).or(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELEGATION.eq(true)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED.eq(false).and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED.eq(false)))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get accepted days from database.
     *
     * @param id object to save in database
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    @Override
    public List<WorkSchedule> getAcceptedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true).or(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELEGATION.eq(true)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED.eq(true))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED.eq(false))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to get rejected days from database.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    @Override
    public List<WorkSchedule> getRejectedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<WorkSchedule> workSchedules = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.EMPLOYEEID.eq(id))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.VACATION.eq(true).or(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELEGATION.eq(true)))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED.eq(false))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED.eq(true))
                    .and(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY.between(from, to))
                    .orderBy(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORKDAY)
                    .fetch()
                    .into(WorkSchedule.class);

            return workSchedules;
        }
    }
    /**
     * This method is used to add new workSchedule object to database.
     *
     * @param workSchedule object to save in database
     * @return Integer id of new record in database.
     */
    @Override
    public Integer addWorkSchedule(WorkSchedule workSchedule) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            WorkScheduleRecord workScheduleRecord = create.newRecord(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE, workSchedule);
            workScheduleRecord.store();

            System.out.println("utworzono nowy rekord work_schedule id: "+workScheduleRecord.getValue("id"));

            return workScheduleRecord.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.toString());
        }
    }
    /**
     * This method is used to accept/reject delegation/sic leave/vacation.
     *
     * @param workSchedule object to save in database
     * @return WorkSchedule object from database with all parameters.
     */
    @Override
    public WorkSchedule acceptRejectWorkSchedule(WorkSchedule workSchedule){
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ACCEPTED, workSchedule.isAccepted())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.REJECTED, workSchedule.isRejected())
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ID.eq(workSchedule.getId()))
                    .execute();

            return workSchedule;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * This method is used to set start of workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean setStart(WorkSchedule workSchedule) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.START_TIME, Time.valueOf(workSchedule.getStartTime()))
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DELAY, workSchedule.isDelay())
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ID.eq(workSchedule.getId()))
                    .execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.toString());
        }
    }
    /**
     * This method is used to set end of the workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean setEnd(WorkSchedule workSchedule) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.END_TIME, Time.valueOf(workSchedule.getEndTime()))
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.OVERHOURS, workSchedule.isOverhours())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.OVERHOURS_TIME, workSchedule.getOverhoursTime())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.WORK_TIME, workSchedule.getWorkTime())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.DEFICIT, workSchedule.getDeficit())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.BREAKS, workSchedule.getBreaks())
                    .set(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.BREAKTIME, workSchedule.getBreaktime())
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ID.eq(workSchedule.getId()))
                    .execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.toString());
        }
    }
    /**
     * This method is used to work schedule from database by Id.
     *
     * @param id workSchedule's id to get from database
     * @return WorkSchedule matching object to the given workschedule Id.
     */
    @Override
    public WorkSchedule getWorkScheduleById(int id){
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            WorkSchedule ws = create.select()
                    .from(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE)
                    .where(nu.studer.sample.tables.WorkSchedule.WORK_SCHEDULE.ID.eq(id))
                    .fetchOne()
                    .into(WorkSchedule.class);

            return ws;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
