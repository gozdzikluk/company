package db.dao;

import rest.models.Departament;

import java.sql.SQLException;
import java.util.List;
/**
 * IDepartmentDao interface is using to connect with table department in database.
 * It allows get and create departments.
 *
 * @author Lukasz Gozdziewski
 */
public interface IDepartmentDao {
    /**
     * This method is used to get List of all departments from database.
     *
     * @return List it return list of all departments from department table in database.
     */
    List<Departament> findAll() throws SQLException;
    /**
     * This method is used to add new Depertment to database.
     *
     * @param departament object to save in database
     * @return integer id of new record in database.
     */
    Integer addDepartment(Departament departament) throws SQLException;
}
