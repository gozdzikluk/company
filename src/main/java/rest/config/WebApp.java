package rest.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * WebApp class for JAX-RS.
 * Application path is set to "/"
 *
 * @author Lukasz Gozdziewski
 */
@ApplicationPath("/*") //Use the javax.ws.rs.ApplicationPath annotation to defined the base URI pattern that gets mapped to the servlet
public class WebApp extends Application {

}
