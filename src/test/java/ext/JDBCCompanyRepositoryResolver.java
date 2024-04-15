package ext;

import db.CompanyRepository;
import db.CompanyRepositoryJDBC;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.sql.Connection;

public class JDBCCompanyRepositoryResolver implements ParameterResolver {
    public static String KEY = "connection";    //KEY для глобального хранилища в extensionContext

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        return parameterContext.getParameter().getType().equals(CompanyRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        Connection connection = (Connection) extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get(KEY);
        return new CompanyRepositoryJDBC(connection);
    }
}
