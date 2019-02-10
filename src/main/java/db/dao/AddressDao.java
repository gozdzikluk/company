package db.dao;

import rest.models.Address;
import rest.models.Employee;
/**
 * AddressDao interface is using to connect with table address in database.
 *
 * @author Lukasz Gozdziewski
 */
public interface AddressDao {
    /**
     * This method is used to add new Address to database.
     *
     * @param employee object to save in database
     * @return boolean true if success or false if fails.
     */
    boolean addAddress(Employee employee);
    /**
     * This method is used to update address in database.
     *
     * @param address object to update in database
     * @param id employee id
     * @return Integer id of new department.
     */
    boolean updateAddress(Address address, int id);
    /**
     * This method is used to delete address from database.
     *
     * @param id id of the employee to be deleted
     * @return boolean true if success or false if fails.
     */
    boolean deleteAddress(int id);

}
