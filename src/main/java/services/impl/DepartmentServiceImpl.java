package services.impl;

import db.dao.IDepartmentDao;
import exceptions.AddRecordException;
import exceptions.ErrorMessages;
import exceptions.NotFoundRecordException;
import rest.models.Departament;
import services.DepartmentService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service for department object
 * contains business logic.
 *
 * @author Lukasz Gozdziewski
 */
@Stateless
public class DepartmentServiceImpl implements DepartmentService {
    protected final Logger log = Logger.getLogger(getClass().getName());

    @EJB
    IDepartmentDao departmentDao;

    /**
     * This method is used to get list of all department.
     *
     * @return List<Departament> This returns list of all department.
     */
    @Override
    public List<Departament> findAll() {
        try {
            return Optional.ofNullable(departmentDao.findAll()).orElseThrow(() -> new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.toString()));
        } catch (SQLException e) {
            log.warning("Blad podczas pobierania listy wszystkich departamentow! " + e.getSQLState());
            throw new NotFoundRecordException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }
    /**
     * This method is used to add new Department to database.
     *
     * @param departament object to save in database
     * @return Integer id of new department.
     */
    @Override
    public Integer addDepartment(Departament departament) {
        try {
            return departmentDao.addDepartment(departament);
        } catch (SQLException e) {
            log.warning("Blad podczas dodawania nowego dzialu! " + e.getSQLState());
            throw new AddRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
        }
    }
}
