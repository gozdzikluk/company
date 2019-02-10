package rest.resources;


import exceptions.ErrorMessages;
import exceptions.NotFoundRecordException;
import rest.models.Employee;
import rest.models.EmployeeDto;
import security.filter.JWTTokenNeeded;
import services.EmployeeService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * EmployeeResource class for JAX-RS.
 * Resource path is set to "/employees"
 * This resource allow manage employees in company application.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@JWTTokenNeeded
public class EmployeeResource {
    /**
     * Keeps instance of employee service.
     * When this instance is used container performs injection when the component is created.
     */
    @EJB
    private EmployeeService employeeService;

    /**
     * This method is used to get list of all employess from database.
     *
     * @return List of all employees in database.
     */
    @GET
    public List<EmployeeDto> getAll() throws SQLException, NamingException {
        return Optional.ofNullable(employeeService.findAll()).orElseThrow(() -> new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    /**
     * This method is used to get employee by Id.
     *
     * @param id employee's Id to get from database
     * @return EmployeeDto if exists it return employee object.
     */
    @GET
    @Path("/{id}")
    public EmployeeDto getEmployeeById(@PathParam("id") int id) {

        return Optional.ofNullable(employeeService.findEmployeeById(id)).orElseThrow(() -> new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    /**
     * This method is used to get list of all employees from department.
     *
     * @param id department's Id
     * @return List returns list of all employees from department.
     */
    @GET
    @Path("department/{id}")
    public List<EmployeeDto> getEmployeeByDepartmentId(@PathParam("id") int id) {
        return Optional.ofNullable(employeeService.findEmployeesByDepartment(id)).orElseThrow(() -> new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    /**
     * This method is used to add new employee to database.
     *
     * @param employee object to save in database.
     * @return Response if employee added correctly then return Id of new record in database.
     */
    @POST
    @Path("/add")
    public Response addEmployee(@Valid Employee employee) {
        return employeeService.addEmployee(employee) != null ? Response.ok(employee.getId()).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * This method is used to update employee in database.
     *
     * @param employee object to update in database
     * @return Response if update successful it returns OK if not return server error message.
     */
    @POST
    @Path("/update")
    public Response updateEmployee(@Valid Employee employee) {
        return employeeService.updateEmployee(employee) ? Response.ok("OK").build() : Response.serverError().build();
    }

    /**
     * This method is used to employee from database.
     *
     * @param id user's to delete Id
     * @return Response if delete successful it returns message.
     */
    @POST
    @Path("/delete/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        return employeeService.deleteEmployee(id) ? Response.ok("Usunięto pracownika.").build() : Response.serverError().build();
    }
}
