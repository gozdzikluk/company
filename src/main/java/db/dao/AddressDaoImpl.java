package db.dao;

import nu.studer.sample.tables.Address;
import nu.studer.sample.tables.records.AddressRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import rest.models.Employee;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.jooq.SQLDialect.POSTGRES;

/**
 * SddressDaoImpl is statless implementation of interface AddressDao and is using to connect and modify table address in database.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class AddressDaoImpl implements AddressDao {
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
     * Keeps logger instance.
     */
    protected final Logger log = Logger.getLogger(getClass().getName());

    /**
     * Empty constructor.
     */
    public AddressDaoImpl() throws NamingException {
    }

    /**
     * This method is used to add new Address to database.
     *
     * @param employee object to save in database
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean addAddress(Employee employee) {

        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            AddressRecord addressRecord = create.newRecord(Address.ADDRESS);
            addressRecord.setCity(employee.getAddress().getCity());
            addressRecord.setStreet(employee.getAddress().getStreet());
            addressRecord.setEmployeeid(employee.getId());
            addressRecord.setNumber(employee.getAddress().getNumber());
            addressRecord.setZip(employee.getAddress().getZip());

            addressRecord.store();
            log.info("Dodano adres do DB dla pracownika o id=" + employee.getId());
            return true;
        } catch (SQLException e) {
            log.warning("Blad podczas dodawania adresu dla pracownika o id=" + employee.getId() + ". Error=" + e.getSQLState());
            return false;
        }
    }

    /**
     * This method is used to update address in database.
     *
     * @param address object to update in database
     * @param id employee id
     * @return Integer id of new department.
     */
    @Override
    public boolean updateAddress(rest.models.Address address, int id) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            create.update(Address.ADDRESS)
                    .set(Address.ADDRESS.CITY, address.getCity())
                    .set(Address.ADDRESS.ZIP, address.getZip())
                    .set(Address.ADDRESS.NUMBER, address.getNumber())
                    .set(Address.ADDRESS.STREET, address.getStreet())
                    .where(Address.ADDRESS.EMPLOYEEID.eq(id))
                    .execute();

            log.info("Zaktualizowano adres dla pracownika o Id=" + id);
            return true;
        } catch (SQLException e) {
            log.warning("Blad podczas aktualizacji adresu dla pracownika o id=" + id + " error=" + e.getSQLState());
            return false;
        }

    }

    /**
     * This method is used to delete address from database.
     *
     * @param empId id of the employee to be deleted
     * @return boolean true if success or false if fails.
     */
    @Override
    public boolean deleteAddress(int empId) {
        try (Connection conn = dataSource.getConnection();
             DSLContext create = DSL.using(conn, POSTGRES)) {

            AddressRecord addressRecord = create.fetchOne(Address.ADDRESS, Address.ADDRESS.EMPLOYEEID.eq(empId));
            addressRecord.delete();

            log.info("Usunieto adres dla pracownika o id=" + empId);
            return true;
        } catch (SQLException e) {
            log.warning("Blad podczas usuwania adresu dla pracownika o id=" + empId + " error=" + e.getSQLState());
            return false;
        }
    }
}
