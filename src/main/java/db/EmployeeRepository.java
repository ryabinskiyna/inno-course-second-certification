package db;

import model.db.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository {

    List<EmployeeEntity> getAllByCompanyId(int companyId);

    EmployeeEntity getById(int id);

    int create(EmployeeEntity e);

    EmployeeEntity create(int companyId);

    int update(EmployeeEntity e);

    void deleteById(int id);

    EmployeeEntity getLast();

    List<EmployeeEntity> getAll();

    boolean deleteAllByCompanyId(int companyId);

    boolean clean(String prefix);

}
