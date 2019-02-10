package db.dao;

import nu.studer.sample.tables.Department;
import nu.studer.sample.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import rest.models.Departament;

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
 * DepartmentDao is Stateless implementation of interface IDepartmentDao and is using to connect with table department in database.
 * It allows get and create departments.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class DepartmentDao implements IDepartmentDao {
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
    public DepartmentDao() throws NamingException {
    }
    /**
     * This method is used to get List of all departments from database.
     *
     * @return List it return list of all departments from department table in database.
     */
    @Override
    public List<Departament> findAll() throws SQLException {
        List<Departament> departments;

        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            departments = create.select(Department.DEPARTMENT.fields())
                    .from(Department.DEPARTMENT)
                    .fetchInto(Departament.class);
            log.info("Pobrano liste wszystkich departamentow");

            return departments;
        }
    }
    /**
     * This method is used to add new Depertment to database.
     *
     * @param departament object to save in database
     * @return integer id of new record in database.
     */
    @Override
    public Integer addDepartment(Departament departament) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            DepartmentRecord departmentRecord = create.newRecord(Department.DEPARTMENT, departament);
            departmentRecord.store();

            log.log(Level.INFO, "Poprawnie dodano departament o Id= " + departmentRecord.getId());
            return departmentRecord.getId();
        }
    }


}
