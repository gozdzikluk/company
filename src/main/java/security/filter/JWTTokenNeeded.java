package security.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * JWTTokenNeeded is interface to filter REST endpoints.
 *
 * @author Lukasz Gozdziewski
 */
@NameBinding //Meta-annotation used to create name binding annotations for filters and interceptors.
@Retention(RUNTIME) //Indicates how long annotations with the annotated type are to be retained. If no Retention annotation is present on an annotation type declaration, the retention policy defaults to RetentionPolicy.CLASS
@Target({TYPE, METHOD}) // Indicates the contexts in which an annotation type is applicable.
public @interface JWTTokenNeeded { //interface declaration
}
