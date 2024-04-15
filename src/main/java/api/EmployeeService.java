package api;

import model.api.Employee;

import java.util.List;

public interface EmployeeService {
    void setURI(String uri);

    List<Employee> getAllByCompanyId(int companyId);

    Employee generateEmployee();

    Employee getById(int id);

    int create(Employee employee);

    int update(Employee employee);

    void deleteById(int id);

    void deleteByCompanyId(int companyId);

    void logIn(String login, String password);

    void logOut();
}
