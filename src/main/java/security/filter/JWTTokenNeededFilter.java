package security.filter;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
/**
 * JWTTokenNeededFilter is implementation of JWTTokenNeeded.
 * This is a REST method filter and it is using to check if request have Bearer token in header.
 *
 * @author Lukasz Gozdziewski
 */
@Provider //Marks an implementation of an extension interface that should be discoverable by JAX-RS runtime during a provider scanning phase
@JWTTokenNeeded // name binding
@Priority(Priorities.AUTHENTICATION) //The Priority annotation can be applied to classes to indicate in what order the classes should be used. The effect of using the Priority annotation in any particular instance is defined by other specifications that define the use of a specific class.
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException { //Container request filter context. A mutable class that provides request-specific information for the filter, such as request URI, message headers, message entity or request-scoped properties. The exposed setters allow modification of the exposed request-specific information.

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION); // get request header from requestContext

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided"); //if not throw NotAuthorizedException
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            String keyString = "dafadf3wropkjd1398dj2390idm23098jc012293k20";
            Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES"); //generate new key base on keyString
            Jwts.parser().setSigningKey(key).parseClaimsJws(token); //validate token
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());  //return Response with status UNAUTHORIZED if authorization fails
        }
    }
}
