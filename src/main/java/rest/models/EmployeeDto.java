package rest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * EmployeeDto REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
public class EmployeeDto {
    /**
     * Keeps employee's id.
     */
    private Integer id;
    /**
     * Keeps employee's first name.
     */
    private String firstName;
    /**
     * Keeps employee's last name.
     */
    private String lastName;
    /**
     * Keeps employee's email.
     */
    private String email;
    /**
     * Keeps employee's tel.
     */
    private String tel;
    /**
     * Keeps employee's department.
     */
    private Departament departament;
    /**
     * Keeps employee's address.
     */
    private Address address;
    /**
     * Keeps employee's hire date.
     */
    private String hireDate;
    /**
     * Keeps employee's position.
     */
    private String position;
    /**
     * Keeps employee's login.
     */
    private String login;
    /**
     * Keeps employee's user type.
     */
    private String usertype;
}
