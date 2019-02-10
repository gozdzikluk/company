package rest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Department REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
public class Departament {
    /**
     * Keeps department id.
     */
    private Integer id;
    /**
     * Keeps department name.
     */
    private String name;
    /**
     * Keeps department manager name.
     */
    private String manager;
}
