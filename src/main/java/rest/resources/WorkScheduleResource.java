package rest.resources;


import exceptions.ErrorMessages;
import exceptions.NotFoundRecordException;
import rest.models.WorkSchedule;
import rest.models.WorkScheduleSummary;
import security.filter.JWTTokenNeeded;
import services.WorkScheduleService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * WorkScheduleResource class for JAX-RS.
 * Resource path is set to "/work-schedule"
 * This resource allow manage work schedules (days of work) in company application.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
@Path("/work-schedule")
@Produces(MediaType.APPLICATION_JSON)
@JWTTokenNeeded
public class WorkScheduleResource {
    /**
     * Keeps instance of workSchedule service.
     * When this instance is used container performs injection when the component is created.
     */
    @EJB
    WorkScheduleService workScheduleService;

    /**
     * This method is used to get all employee's workSchedules by employee Id.
     *
     * @return List if exists it returns list od all days of work for employee Id.
     */
    @GET
    @Path("/employee/{id}")
    public Response getWorkScheduleByEmployeeId(@PathParam("id") int id) throws SQLException {
        return Response.ok(workScheduleService.getWorkScheduleByEmployeeId(id)).build();
    }

    /**
     * This method is used to get alle workSchedules for employee by Id and from to.
     *
     * @param id employee's Id to get from database
     * @param from start time
     * @param to end time
     * @return EmployeeDto if exists it returns employee object.
     */
    @GET
    @Path("/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getWorkScheduleByEmployeeIdFromTo(@PathParam("id") int id,
                                                                @PathParam("from") Timestamp from,
                                                                @PathParam("to") Timestamp to) throws SQLException {
        return workScheduleService.getWorkScheduleByEmployeeIdFromTo(id, from, to);
    }

    /**
     * This method is used to get alle workSchedules for employee by Id and from to.
     *
     * @param id employee's Id to get from database
     * @param from start time
     * @param to end time
     * @return EmployeeDto if exists it returns employee object.
     */
    @GET
    @Path("/summary/employee/{id}/{from}/{to}")
    public WorkScheduleSummary getSummaryByEmployeeId(@PathParam("id") int id,
                                              @PathParam("from") Timestamp from,
                                              @PathParam("to") Timestamp to) throws SQLException {

        return workScheduleService.getSummaryByid(id, from, to);
    }

    /**
     * This method is used to get summary for department.
     *
     * @param id department's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/summary/department/{id}/{from}/{to}")
    public WorkScheduleSummary getSummaryByDepartmentId(@PathParam("id") int id,
                                                        @PathParam("from") Timestamp from,
                                                        @PathParam("to") Timestamp to) throws SQLException {

        return workScheduleService.getSummaryByDepartmentId(id, from, to);
    }

    /**
     * This method is used to overtimes for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/overtime/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getOvertimeByEmployeeId(@PathParam("id") int id,
                                                       @PathParam("from") Timestamp from,
                                                       @PathParam("to") Timestamp to) throws SQLException {

        return workScheduleService.getOvertimeByEmployeeId(id, from, to);
    }

    /**
     * This method is used to get delays for emloyee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/delays/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getDelaysByEmployeeId(@PathParam("id") int id,
                                                     @PathParam("from") Timestamp from,
                                                     @PathParam("to") Timestamp to) throws SQLException {

        return workScheduleService.getDelaysByEmployeeId(id, from, to);
    }

    /**
     * This method is used to get all vacations days for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/vacation/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getVacationByEmployeeId(@PathParam("id") int id,
                                                    @PathParam("from") Timestamp from,
                                                    @PathParam("to") Timestamp to) {

        return workScheduleService.getVacationByEmployeeId(id, from, to);
    }

    /**
     * This method is used to get all days with sick leave for employee.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/sick-leave/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getSickLeaveByEmployeeId(@PathParam("id") int id,
                                                      @PathParam("from") Timestamp from,
                                                      @PathParam("to") Timestamp to) {

        return workScheduleService.getSickLeaveByEmployeeId(id, from, to);
    }

    /**
     * This method is used to get all days with deficit from database by employee Id.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/deficit/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getDeficitByEmployeeId(@PathParam("id") int id,
                                                       @PathParam("from") Timestamp from,
                                                       @PathParam("to") Timestamp to) {

        return workScheduleService.getDeficitByEmployeeId(id, from, to);
    }

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching records from database.
     */
    @GET
    @Path("/to-accept/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getToAcceptByEmployeeIdFromTo(@PathParam("id") int id,
                                                            @PathParam("from") Timestamp from,
                                                            @PathParam("to") Timestamp to) {

        return workScheduleService.getToAcceptByEmployeeIdFromTo(id, from, to);
    }

    /**
     * This method is used to get workSchedule which needs accept action.
     *
     * @param id department's id to get
     * @return List all matching records from database.
     */
    @GET
    @Path("/to-accept/department/{id}")
    public List<WorkSchedule> getToAcceptByDepartmentId(@PathParam("id") int id) {

        return workScheduleService.getToAcceptByDepartmentId(id);
    }

    /**
     * This method is used to get workSchedule which needs accept action for employee Id.
     *
     * @param id employee's id to get
     * @return List all matching records from database.
     */
    @GET
    @Path("/to-accept/employee/{id}")
    public List<WorkSchedule> getToAcceptByEmployeeId(@PathParam("id") int id) {

        return workScheduleService.getToAcceptByEmployeeId(id);
    }

    /**
     * This method is used to get accepted days from database.
     *
     * @param id object to save in database
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    @GET
    @Path("/accepted/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getAcceptedByEmployeeId(@PathParam("id") int id,
                                                      @PathParam("from") Timestamp from,
                                                      @PathParam("to") Timestamp to) {

        return workScheduleService.getAcceptedByEmployeeIdFromTo(id, from ,to);
    }
    /**
     * This method is used to get rejected days from database.
     *
     * @param id employee's id to get
     * @param from start date
     * @param to end date
     * @return List all matching days from database.
     */
    @GET
    @Path("/rejected/employee/{id}/{from}/{to}")
    public List<WorkSchedule> getRejectedByEmployeeId(@PathParam("id") int id,
                                                      @PathParam("from") Timestamp from,
                                                      @PathParam("to") Timestamp to) {

        return workScheduleService.getRejectedByEmployeeIdFromTo(id, from, to);
    }

    /**
     * This method is used to add new workSchedule object to database.
     *
     * @param workSchedule object to save in database
     * @return Integer id of new record in database.
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWorkSchedule(WorkSchedule workSchedule){
        int id = workScheduleService.addWorkSchedule(workSchedule);
        workSchedule.setId(id);
        return Response.ok(workSchedule).build();
    }
    /**
     * This method is used to accept/reject delegation/sick leave/vacation.
     *
     * @param workSchedule object to save in database
     * @return WorkSchedule object from database with all parameters.
     */
    @POST
    @Path("/decision")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response acceptRejectWorkSchedule(WorkSchedule workSchedule){
        WorkSchedule ws = workScheduleService.acceptRejectWorkSchedule(workSchedule);
        return Optional.ofNullable(Response.ok(ws).build()).orElseThrow(() -> new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }
    /**
     * This method is used to set start of workSchedule.
     *
     * @param workSchedule object to save in database
     * @return Response status OK if success.
     */
    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setStart(WorkSchedule workSchedule){
            return workScheduleService.setStart(workSchedule) ? Response.ok().build() : Response.status(Response.Status.valueOf("NOT_RECOGNIZED")).build();
    }
    /**
     * This method is used to set end of the workSchedule.
     *
     * @param workSchedule object to save in database
     * @return Response status OK if success.
     */
    @POST
    @Path("/end")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setEnd(WorkSchedule workSchedule){
        return workScheduleService.setEnd(workSchedule) ? Response.ok().build() : Response.status(Response.Status.valueOf("NOT_RECOGNIZED")).build();
    }

}
