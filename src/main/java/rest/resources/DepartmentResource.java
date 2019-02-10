package rest.resources;


import rest.models.Departament;
import security.filter.JWTTokenNeeded;
import services.DepartmentService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
/**
 * DepartmentResource class for JAX-RS.
 * Resource path is set to "/department"
 * This resource allow manage all departments in company.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
@Path("/department")
@Produces(MediaType.APPLICATION_JSON)
@JWTTokenNeeded
public class DepartmentResource {
    /**
     * Keeps instance of department service.
     * When this instance is used container performs injection when the component is created.
     */
    @EJB
    DepartmentService departmentService;
    /**
     * This method is used to get list of all departments
     *
     * @return list of alle departments from database.
     */
    @GET
    public List<Departament> getAll(){
        return departmentService.findAll();
    }
    /**
     * This method is used to add new department to database.
     *
     * @param departament department object to save in database
     * @return Response if save in database is succerfull it returns this object with Id of new record.
     */
    @POST
    @Path("/add")
    public Response addDepartment(Departament departament){
        return Response.ok(departmentService.addDepartment(departament)).build();
    }
}
