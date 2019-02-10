package services.impl;

import db.dao.AddressDao;
import db.dao.EmployeeDao;
import exceptions.*;
import nu.studer.sample.tables.Employees;
import org.jooq.Record;
import rest.models.Departament;
import rest.models.Employee;
import rest.models.EmployeeDto;
import services.Authorization;
import services.EmployeeService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * EmployeeServiceImpl is stateless implementation of EmployeeService interface adn is using to manage employees in application.
 * This is a CRUD interface for object Employee.
 * It determines business logic layer.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class EmployeeServiceImpl implements EmployeeService {
    @EJB
    EmployeeDao employeeDao;
    @EJB
    AddressDao addressDao;
    @EJB
    Authorization authorization;

    protected final Logger log = Logger.getLogger(getClass().getName());

    /**
     * This method is used to get list of all employees from database.
     *
     * @return List all employees from table employees in database.
     */
    @Override
    public List<EmployeeDto> findAll() {
        try {
            return covnertRecordsToEmployee(employeeDao.findAll());
        } catch (NamingException | SQLException e) {
            log.warning("Blad podczas pobierania listy wszytkich pracownikow =" + e.getMessage());
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }

    /**
     * This method is used to get employee from database by Id.
     *
     * @param id employee's id to get
     * @return EmployeeDto employee object from database with correspond id.
     */
    @Override
    public EmployeeDto findEmployeeById(int id) {
        try {
            return employeeDao.findById(id);
        } catch (SQLException e) {
            log.warning("Blad podczas pobierania poracownika o id=" + id + " error=" + e.getSQLState());
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
        }
    }

    /**
     * This method is used to get list of employees from database by correspond department.
     *
     * @param id department's id to get
     * @return List all employees from department.
     */
    @Override
    public List<EmployeeDto> findEmployeesByDepartment(int id) {
        try {
            return covnertRecordsToEmployee(employeeDao.findEmployeesByDepartment(id));
        } catch (SQLException e) {
            log.warning("Blad podczas pobierania listy pracownikow dla dzialu o id=" + id + " error=" + e.getSQLState());
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString());
        }
    }
    /**
     * This method is used to add new Employee to database.
     *
     * @param employee object to save in database
     * @return Integer id of new record in database.
     */

    @Override
    public Integer addEmployee(Employee employee) {
        String pass;
        try {
            pass = authorization.generateStrongPasswordHash(employee.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.warning("Bad authorization! " + e.getMessage());
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_USER_PROFILE.getErrorMessage());
        }

        employee.setPassword(pass);
        Integer id;
        try {
            id = employeeDao.addEmployee(employee);
        } catch (Exception e) {
            log.warning("Blad podczas zapisywania pracownika! " + e.getMessage());
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_USER.getErrorMessage());
        }
        if (id != null) {
            employee.setId(id);
        } else {
            log.warning("Blad podczas zapisywania pracownika! ");
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_USER.getErrorMessage());
        }
        if (!addressDao.addAddress(employee)) {
            log.warning("Blad podczas dodawania adresu dla pracownika o Id= " + id);
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_ADDRESS_RECORD.getErrorMessage());
        }

        return id;
    }

    /**
     * This method is used to update employee record in database.
     *
     * @param employee object to update in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            return (employeeDao.updateEmployee(employee) &
                    addressDao.updateAddress(employee.getAddress(), employee.getId())) ? true : false;
        } catch (SQLException e) {
            log.warning("Blad podczas aktualizacji poracownika o id=" + employee.getId() + " error=" + e.getSQLState());
            throw new UpdateRecordException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
        }
    }

    /**
     * This method is used to delete employee from database.
     *
     * @param id employee's to delete id
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean deleteEmployee(int id) {
        try {
            return addressDao.deleteAddress(id) & employeeDao.deleteEmployee(id) ? true : false;
        } catch (SQLException e) {
            log.warning("Blad podczas usuwania poracownika o id=" + id + " error=" + e.getSQLState());
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }

    public List<EmployeeDto> covnertRecordsToEmployee(List<Record> record) {
        List<EmployeeDto> employees = new ArrayList<>();
        for (Record rec : record) {
            EmployeeDto employee = rec.into(EmployeeDto.class);
            employee.setId(rec.get(Employees.EMPLOYEES.ID));
            employee.setAddress(rec.into(rest.models.Address.class));
            employee.setDepartament(rec.into(Departament.class));
            employees.add(employee);

        }
        return employees;
    }
}
