package ext;

import org.junit.jupiter.api.extension.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static ext.CommonHelper.getProperties;

public class JDBCConnectionResolver implements ParameterResolver, AfterAllCallback {
    private final String PROP_FILE_PATH = "src/main/resources/JDBC_x_client.properties";  //Путь к настройкам подключения к БД
    Connection connection;
    public static String KEY = "connection";    //KEY для глобального хранилища в extensionContext

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Connection.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        connection = (Connection) extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get(KEY);
        try {
            if (connection != null) return connection;
            Properties properties = getProperties(PROP_FILE_PATH);
            String connectionString = properties.getProperty("connectionString");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(connectionString, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).put(KEY, connection);
        return connection;
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        connection.close();

    }
}
