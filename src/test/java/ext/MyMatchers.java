package ext;

import model.db.CompanyEntity;

public class MyMatchers {
    public static boolean isCompaniesEqual(model.api.Company companyByAPI, CompanyEntity companyEntityByDB) {
        if (companyEntityByDB.getId() != companyByAPI.getId()) return false;
        if (!companyEntityByDB.getName().equals(companyByAPI.getName())) return false;
        if (!companyEntityByDB.getDescription().equals(companyByAPI.getDescription())) return false;
        if (companyEntityByDB.isActive() != companyByAPI.isActive()) return false;
        return true;
    }
}
