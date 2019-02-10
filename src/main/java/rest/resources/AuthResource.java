package rest.resources;


import rest.models.LoggedUser;
import rest.models.User;
import services.Authorization;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * AuthResource class for JAX-RS.
 * Resource path is set to "/auth"
 * This resource allow authenticate user.
 *
 * @author Lukasz Gozdziewski
 */
@Path("/auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthResource {
    /**
     * Keeps instance of authorization service.
     * When this instance is used container performs injection when the component is created.
     */
    @EJB
    Authorization authorization;
    /**
     * This method is used to logging to application.
     *
     * @param user user's credential for logging process
     * @return Response if credentials are valid it returns bearer token in response header.
     */
    @POST
    @Path("/login")
    public Response authenticateUser(User user) {

        try {
            // Authenticate the user using the credentials provided
            System.out.println("login attempt for: " + user.getLogin());
            User u = authorization.authenticate(user.getLogin(), user.getPassword());

            // Issue a token for the user
            String token = authorization.issueToken(user.getLogin());

            LoggedUser loggedUser = new LoggedUser();
            loggedUser.setLogin(u.getLogin());
            loggedUser.setUserType(u.getUsertype());
            loggedUser.setId(u.getId());

            // Return the token on the response
            return Response.ok(loggedUser).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build(); //if token is not valid
        }
    }
}
