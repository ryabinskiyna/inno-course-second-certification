package ext;

import api.CompanyService;
import api.CompanyServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Properties;

import static ext.CommonHelper.getProperties;

public class CompanyServiceResolver implements ParameterResolver {
    private final static String PROPERTIES_FILE_PATH = "src/main/resources/API_x_client.properties";
    private final static String BASE_URI_PROP = "baseURI";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CompanyService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Properties properties = getProperties(PROPERTIES_FILE_PATH);
        String baseUri = properties.getProperty(BASE_URI_PROP);
        return new CompanyServiceImpl(baseUri);
    }
}
