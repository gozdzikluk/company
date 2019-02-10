package services.impl;

import db.dao.IBreakDao;
import db.dao.WorkscheduleDao;
import exceptions.AddRecordException;
import exceptions.ErrorMessages;
import exceptions.NotFoundRecordException;
import rest.models.Break;
import rest.models.WorkSchedule;
import rest.models.WorkScheduleSummary;
import services.WorkScheduleService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * WorkScheduleSericeImpl is stateless class implements WokScheduleService interface and is using to manage days of work in application.
 * This is CRUD for object workSchedule.
 * This class determinate business logic layer.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class WorkScheduleSericeImpl implements WorkScheduleService {

    @EJB
    WorkscheduleDao workscheduleDao;
    @EJB
    IBreakDao iBreakDao;

    /**
     * This method is used to get list of workSchedule from database by employee id.
     *
     * @param id employee's id to get from database
     * @return List<WorkSchedule> list of all matching records to the given employee ID.
     */
    @Override
    public List<WorkSchedule> getWorkScheduleByEmployeeId(int id) {
        try {
            return workscheduleDao.getWorkScheduleByEmployeeId(id);
        } catch (SQLException e) {
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
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
    public List<WorkSchedule> getWorkScheduleByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getWorkScheduleByEmployeeIdFromTo(id, from, to);
        } catch (SQLException e) {
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
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
    public WorkScheduleSummary getSummaryByid(int id, Timestamp from, Timestamp to) throws SQLException {
        return summary(workscheduleDao.getSummaryByid(id, from, to), from ,to , id);
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
    public WorkScheduleSummary getSummaryByDepartmentId(int id, Timestamp from, Timestamp to) throws SQLException {
        return summary(workscheduleDao.getSummaryBydepartmentid(id, from, to), from, to , id);
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
        return workscheduleDao.getOvertimeByEmployeeId(id, from, to);
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
        return workscheduleDao.getDelaysByEmployeeId(id, from, to);
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
    public List<WorkSchedule> getVacationByEmployeeId(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getVacationByEmployeeId(id, from ,to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
        }
    }

    /**
     * This method is used to calculate summary for workSchedule.
     *
     * @param list list of workSchedule used to calculate
     * @param from start date
     * @param to end date
     * @param id department's id to get
     * @return boolean true if success or false if fails.
     */
    private WorkScheduleSummary summary(List<WorkSchedule> list, Timestamp from, Timestamp to, int id){
        WorkScheduleSummary workScheduleSummary = new WorkScheduleSummary();
        int overtime = 0;
        int overtimeMinutes = 0;
        int delays = 0;
        int totalDeficit = 0;
        int plannedVacations = 0;
        int totalusedvacations = 0;
        Long totalWorkTime = 0L;

        for (WorkSchedule ws : list) {
            if(ws.isOverhours()==true){
                ++overtime;
                overtimeMinutes += ws.getOverhoursTime();
            }
            if(ws.isDelay()){
                ++delays;
                totalDeficit += ws.getDeficit();
            }
            if(ws.isVacation()){
                if(ws.getWorkday().isAfter(LocalDateTime.now().toLocalDate())){
                    ++plannedVacations;
                }else{
                    ++totalusedvacations;
                }
            }
            totalWorkTime += ws.getWorkTime();
        }

        workScheduleSummary.setTotalOvertimes(overtime);
        workScheduleSummary.setTotalOvertimeMinutes(overtimeMinutes);
        workScheduleSummary.setTotalDeficit(totalDeficit);
        workScheduleSummary.setTotalUsedVacation(totalusedvacations);
        workScheduleSummary.setTotalPlannedVacation(plannedVacations);
        workScheduleSummary.setTotalDelays(delays);
        workScheduleSummary.setTotalWorkTime(totalWorkTime);

        workScheduleSummary.setFrom(from);
        workScheduleSummary.setTo(to);

        return workScheduleSummary;
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
    public List<WorkSchedule> getSickLeaveByEmployeeId(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getSickLeaveByEmployeeId(id, from ,to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
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
    public List<WorkSchedule> getDeficitByEmployeeId(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getDeficitByEmployeeId(id, from ,to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
        }
    }

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getToAcceptByEmployeeId(int id) {
        try {
            return workscheduleDao.getToAcceptByEmployeeId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
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
    public List<WorkSchedule> getToAcceptByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getToAcceptByEmployeeIdFromTo(id, from, to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
        }
    }

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id department's id to get
     * @return List all matching records from database.
     */
    @Override
    public List<WorkSchedule> getToAcceptByDepartmentId(int id) {
        try {
            return workscheduleDao.getToAcceptByDepartmentId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
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
    public List<WorkSchedule> getAcceptedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getAcceptedByEmployeeIdFromTo(id, from, to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
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
    public List<WorkSchedule> getRejectedByEmployeeIdFromTo(int id, Timestamp from, Timestamp to) {
        try {
            return workscheduleDao.getRejectedByEmployeeIdFromTo(id, from, to);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
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
        Integer id = workscheduleDao.addWorkSchedule(workSchedule);
        System.out.println("dzien: "+workSchedule.getWorkday());

        if (id != null) return id;
        else throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.toString());
    }

    /**
     * This method is used to accept/reject delegation/sick leave/vacation.
     *
     * @param workSchedule object to save in database
     * @return WorkSchedule object from database with all parameters.
     */
    @Override
    public WorkSchedule acceptRejectWorkSchedule(WorkSchedule workSchedule) {
        return workscheduleDao.acceptRejectWorkSchedule(workSchedule);
    }

    /**
     * This method is used to set start of workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean setStart(WorkSchedule workSchedule) {
        if (workSchedule.getStartTime().isAfter(LocalTime.of(8, 10, 00))){
            workSchedule.setDelay(true);
        }
        else workSchedule.setDelay(false);

        return workscheduleDao.setStart(workSchedule);
    }

    /**
     * This method is used to set end of the workSchedule.
     *
     * @param workSchedule object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean setEnd(WorkSchedule workSchedule) {
        WorkSchedule wsFromDB = workscheduleDao.getWorkScheduleById(workSchedule.getId());
        Integer workTime = ((int) MINUTES.between(wsFromDB.getStartTime(), workSchedule.getEndTime()));

        if(workSchedule.getEndTime().isAfter(LocalTime.of(16, 0, 00)) && workTime > 480){
            workSchedule.setOverhours(true);
            workSchedule.setOverhoursTime(workTime - 480);
        }

        if (wsFromDB.isDelay() && workTime < 480){
            workSchedule.setDeficit(480 - workTime);
        }

        workSchedule.setWorkTime(workTime);
        List<Break> breaks;
        try {
            breaks = iBreakDao.getByWorkScheduleId(workSchedule.getId());
        } catch (SQLException e) {
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        if (breaks.size() > 0){
            workSchedule.setBreaks(breaks.size());
            int breaktime = 0;
            for (Break b : breaks) {
                breaktime += b.getTime();
            }
            workSchedule.setBreaktime(breaktime);
        }

        return workscheduleDao.setEnd(workSchedule);
    }
}
