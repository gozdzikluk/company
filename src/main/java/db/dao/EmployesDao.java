package db.dao;

import nu.studer.sample.tables.Address;
import nu.studer.sample.tables.Department;
import nu.studer.sample.tables.Employees;
import nu.studer.sample.tables.records.EmployeesRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import rest.models.Departament;
import rest.models.Employee;
import rest.models.EmployeeDto;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import static org.jooq.SQLDialect.POSTGRES;

/**
 * EmployesDao is Stateless implementation of interface EmployeeDao and is using to connect with table employee in database.
 * This is a CRUD interface for object Employee.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class EmployesDao implements EmployeeDao {
    /**
     * Keeps logger instance.
     */
    protected final Logger log = Logger.getLogger(getClass().getName());
    /**
     * Keeps instance contex of application.
     */
    private Context context = new InitialContext();
    /**
     * Keeps dataSource instance to connect with Payara's connection pool named jdbc/_firmaResource.
     */
    private DataSource dataSource = (DataSource)
            context.lookup("jdbc/_firmaResource");
    /**
     * Empty constructor.
     */
    public EmployesDao() throws NamingException {
    }
    /**
     * This method is used to get list of all employees from database.
     *
     * @return List all employees from table employees in database.
     */
    @Override
    public List<Record> findAll() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<Record> records = create.select(Employees.EMPLOYEES.fields())
                    .select(Address.ADDRESS.fields())
                    .select(Department.DEPARTMENT.ID, Department.DEPARTMENT.MANAGER, Department.DEPARTMENT.NAME)
                    .from(Employees.EMPLOYEES)
                    .join(Address.ADDRESS)
                    .on(Employees.EMPLOYEES.ID.eq(Address.ADDRESS.EMPLOYEEID))
                    .join(Department.DEPARTMENT)
                    .on(Employees.EMPLOYEES.DEPARTMENT.eq(Department.DEPARTMENT.ID))
                    .orderBy(Employees.EMPLOYEES.ID)
                    .fetch();

            log.log(Level.INFO, "Pobrano liste wszystkich pracownikow.");
            return records;
        }
    }
    /**
     * This method is used to get employee from database by Id.
     *
     * @param id employee's id to get
     * @return EmployeeDto employee object from database with correspond id.
     */
    @Override
    public EmployeeDto findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            System.out.println("Pobrano informacje o pracowniki id= " + id);
            Record record = create.select(Employees.EMPLOYEES.fields())
                    .select(Address.ADDRESS.fields())
                    .select(Department.DEPARTMENT.ID, Department.DEPARTMENT.MANAGER, Department.DEPARTMENT.NAME)
                    .from(Employees.EMPLOYEES)
                    .join(Address.ADDRESS)
                    .on(Employees.EMPLOYEES.ID.eq(Address.ADDRESS.EMPLOYEEID))
                    .join(Department.DEPARTMENT)
                    .on(Employees.EMPLOYEES.DEPARTMENT.eq(Department.DEPARTMENT.ID))
                    .where(Employees.EMPLOYEES.ID.eq(id))
                    .fetchOne();

            if (record != null) {
                EmployeeDto employee = record.into(EmployeeDto.class);
                employee.setAddress(record.into(rest.models.Address.class));
                employee.setDepartament(record.into(Departament.class));
                employee.setId(id);
                log.log(Level.INFO, "Pobrano pracownika o id= " + id);
                return employee;
            } else {
                log.log(Level.WARNING, "Błąd podczas pobierania pracownika o id: " + id + " Nie ma takiego Id w bazie! ");
                return null;
            }
        }
    }
    /**
     * This method is used to get list of employees from database by correspond department.
     *
     * @param id department's id to get
     * @return List all employees from department.
     */
    @Override
    public List<Record> findEmployeesByDepartment(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            List<Record> record = create.select(Employees.EMPLOYEES.fields())
                    .select(Address.ADDRESS.fields())
                    .select(Department.DEPARTMENT.ID, Department.DEPARTMENT.MANAGER, Department.DEPARTMENT.NAME)
                    .from(Employees.EMPLOYEES)
                    .join(Address.ADDRESS)
                    .on(Employees.EMPLOYEES.ID.eq(Address.ADDRESS.EMPLOYEEID))
                    .join(Department.DEPARTMENT)
                    .on(Employees.EMPLOYEES.DEPARTMENT.eq(Department.DEPARTMENT.ID))
                    .where(Department.DEPARTMENT.ID.eq(id))
                    .fetch();

            log.log(Level.INFO, "Pobrano liste pracownikow dla departametu Id= " + id);
            return record;
        }
    }
    /**
     * This method is used to add new Employee to database.
     *
     * @param employee object to save in database
     * @return Integer id of new record in database.
     */
    @Override
    public Integer addEmployee(Employee employee) throws SQLException, EJBTransactionRolledbackException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            EmployeesRecord emp = create.newRecord(Employees.EMPLOYEES);
            emp = employee2employeeRecord(emp, employee);
            emp.store();

            log.log(Level.INFO, "Poprawnie dodano pracownika o Id= " + emp.getId());
            return emp.getId();
        }
    }

    /**
     * This method is used to update employee record in database.
     *
     * @param employee object to update in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(Employees.EMPLOYEES)
                    .set(Employees.EMPLOYEES.EMAIL, employee.getEmail())
                    .set(Employees.EMPLOYEES.POSITION, employee.getPosition())
                    .set(Employees.EMPLOYEES.TEL, employee.getTel())
                    .where(Employees.EMPLOYEES.ID.eq(employee.getId()))
                    .execute();

            log.log(Level.INFO, "poprawnie zaktualizowano pracownika o Id= " + employee.getId());
            return true;
        }


    }
    /**
     * This method is used to convert employee object to employeeRecord object.
     *
     * @param emp employee record object
     * @param employee employee object to convert
     * @return EmployeeRecord record to save in database.
     */
    public EmployeesRecord employee2employeeRecord(EmployeesRecord emp, Employee employee) {
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setDepartment((int) employee.getDepartament().getId());
        emp.setEmail(employee.getEmail());
        emp.setHireDate(employee.getHireDate());
        emp.setPosition(employee.getPosition());
        emp.setTel(employee.getTel());
        emp.setLogin(employee.getLogin());
        emp.setPassword(employee.getPassword());
        emp.setUsertype(employee.getUsertype());

        return emp;
    }
    /**
     * This method is used to delete employee from database.
     *
     * @param id employee's to delete id
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean deleteEmployee(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            EmployeesRecord employeesRecord = create.fetchOne(Employees.EMPLOYEES, Employees.EMPLOYEES.ID.eq(id));
            employeesRecord.delete();
            log.log(Level.INFO, "Poprawnie usunieto pracownika o Id= " + id);
            return true;
        }
    }


}
