package rest.resources;

import rest.models.Break;
import security.filter.JWTTokenNeeded;
import services.BreakService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * BreakResource class for JAX-RS.
 * Resource path is set to "/break"
 * This resource allow manage break during day of work.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
@Path("/break")
@Produces(MediaType.APPLICATION_JSON)
@JWTTokenNeeded
public class BreakResource {
    /**
     * Keeps instance of break service.
     * When this instance is used container performs injection when the component is created.
     */
    @EJB
    BreakService breakService;
    /**
     * This method is used to add new break during day of work.
     *
     * @param br break object with information about workScheduleId
     * @return Response if created successful this returns new break record Id.
     */
    @POST
    @Path("/add")
    public Response addBreak(Break br){
        return Response.ok(breakService.addBreak(br)).build();
    }
    /**
     * This method is used to end of break during day of work.
     *
     * @param br break object with Id of break.
     * @return Response if ended successful it returns break obkject with all information.
     */
    @POST
    @Path("/end")
    public Response endBreak(Break br){
        return Response.ok(breakService.endBreak(br)).build();
    }

}
