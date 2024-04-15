package ext;

import model.api.Employee;
import model.db.EmployeeEntity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;



public class IsEmployeeEqual extends TypeSafeMatcher<Employee> {
    private EmployeeEntity employeeEntityDB;
    List<String> errors = new ArrayList<>();

    IsEmployeeEqual(EmployeeEntity employeeEntity) {
        this.employeeEntityDB = employeeEntity;
    }

    @Override
    protected boolean matchesSafely(Employee employee) {
        if (employeeEntityDB.getId() != employee.getId()) errors.add("id");
        if (!isStringsEqual(employeeEntityDB.getFirstName(), employee.getFirstName())) errors.add("firstName");
        if (!isStringsEqual(employeeEntityDB.getLastName(), employee.getLastName())) errors.add("lastName");
        if (!isStringsEqual(employeeEntityDB.getMiddleName(), employee.getMiddleName())) errors.add("middleName");
        if (employeeEntityDB.getCompanyId() != employee.getCompanyId()) errors.add("companyId");
        if (!isStringsEqual(employeeEntityDB.getEmail(), employee.getEmail())) errors.add("email");
        if (!isStringsEqual(employeeEntityDB.getAvatarUrl(), employee.getUrl())) errors.add("url");
        if (!isStringsEqual(employeeEntityDB.getPhone(), employee.getPhone())) errors.add("phone");
        if (!isStringsEqual(employeeEntityDB.getBirthdate(), employee.getBirthdate())) errors.add("birthdate");
        if (employeeEntityDB.isActive() != employee.getIsActive()) errors.add("isActive");
        if (errors.size() == 0) return true;
        return false;
    }

    private <X, Y> boolean isStringsEqual(X x, Y y) {
        if (x == null || y == null) {
            if (x != y) return false;
        } else if (!x.toString().equals(y.toString())) return false;
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Errors in fields:" + errors.toString() +
                ". Expected EmployeeEntity from DB: " + employeeEntityDB.toString());
    }

    public static Matcher<Employee> isEqual(EmployeeEntity employeeEntityDB) {
        return new IsEmployeeEqual(employeeEntityDB);
    }
}
