package services;

import rest.models.Employee;
import rest.models.EmployeeDto;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * EmployeeService is  using to manage employees in application.
 * This is a CRUD interface for object Employee.
 * It determines business logic layer.
 *
 * @author Lukasz Gozdziewski
 */
public interface EmployeeService {
    /**
     * This method is used to get list of all employees from database.
     *
     * @return List all employees from table employees in database.
     */
    List<EmployeeDto> findAll() throws SQLException, NamingException;

    /**
     * This method is used to get employee from database by Id.
     *
     * @param id employee's id to get
     * @return EmployeeDto employee object from database with correspond id.
     */
    EmployeeDto findEmployeeById(int id);

    /**
     * This method is used to get list of employees from database by correspond department.
     *
     * @param id department's id to get
     * @return List all employees from department.
     */
    List<EmployeeDto> findEmployeesByDepartment(int id);

    /**
     * This method is used to add new Employee to database.
     *
     * @param employee object to save in database
     * @return Integer id of new record in database.
     */
    Integer addEmployee(Employee employee);

    /**
     * This method is used to update employee record in database.
     *
     * @param employee object to update in database
     * @return boolean true if success or false if fails.
     */
    boolean updateEmployee(Employee employee);

    /**
     * This method is used to delete employee from database.
     *
     * @param id employee's to delete id
     * @return boolean true if success or false if fails.
     */
    boolean deleteEmployee(int id);

}
