package services;

import rest.models.Departament;

import java.util.List;

public interface DepartmentService {

    List<Departament> findAll();
    Integer addDepartment(Departament departament);
}
