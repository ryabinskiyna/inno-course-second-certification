package ext;

import model.db.CompanyEntity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;


public class IsCompanyEqual extends TypeSafeMatcher<model.api.Company> {
    private CompanyEntity companyEntityDB;
    List<String> errors = new ArrayList<>();

    IsCompanyEqual(CompanyEntity companyEntity) {
        this.companyEntityDB = companyEntity;
    }

    @Override
    protected boolean matchesSafely(model.api.Company company) {
        if (companyEntityDB.getId() != company.getId()) errors.add("id");
        if (!isStringsEqual(companyEntityDB.getName(), company.getName())) errors.add("name");
        if (!isStringsEqual(companyEntityDB.getDescription(), company.getDescription())) errors.add("description");
        if (companyEntityDB.isActive() != company.isActive()) errors.add("isActive");
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
        description.appendText("Errors in fields: " + errors.toString() + ", CompanyEntity from DB: "
                + companyEntityDB.toString());
    }

    public static Matcher<model.api.Company> isEqual(CompanyEntity companyEntityDB) {
        return new IsCompanyEqual(companyEntityDB);
    }
}
