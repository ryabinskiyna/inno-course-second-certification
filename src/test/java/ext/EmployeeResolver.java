package ext;

import db.EmployeeRepository;
import db.EmployeeRepositoryHiber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.db.EmployeeEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.ArrayList;
import java.util.List;

public class EmployeeResolver implements ParameterResolver {
    private EmployeeRepository employeeRepository;
    private final String EMF_GLOBAL_KEY = "EntityManagerFactory";  //Название ключа EntityManagerFactory в хранилище
    private final String TEST_NUM_COMPANY_GLOBAL_KEY = "COMPANY";  //Название ключа EntityManagerFactory в хранилище

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        if (parameterContext.getParameter().getType().equals(EmployeeEntity.class)) return true;
        if (parameterContext.isAnnotated(TestProperties.class)) {
            if (parameterContext.findAnnotation(TestProperties.class).get().itemCount() > 1)
                return true;   //Если указано количество больше 1, то вернётся List<EmployeeEntity>
        }
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        //Вытаскиваем сохранённый EntityManager из extensionContext
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) extensionContext
                .getStore(ExtensionContext.Namespace.GLOBAL).get(EMF_GLOBAL_KEY);
        EntityManager em = entityManagerFactory.createEntityManager();

        employeeRepository = new EmployeeRepositoryHiber(em);
        int companyId = 0;

        //Должен быть указан номер теста, чтобы получить соответствующий номер компании из хранилища
        if (parameterContext.isAnnotated(TestProperties.class)) {
            int testNum = parameterContext.findAnnotation(TestProperties.class).get().testNum();
            companyId = (int) extensionContext.getStore(ExtensionContext.Namespace.GLOBAL)
                    .get(TEST_NUM_COMPANY_GLOBAL_KEY + testNum);
        }

        //Если задано количество Employee
        if (parameterContext.isAnnotated(TestProperties.class)) {
            int count = parameterContext.findAnnotation(TestProperties.class).get().itemCount();

            if (count > 1) {
                List<EmployeeEntity> employeeEntities = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    employeeEntities.add(i, employeeRepository.create(companyId));
                }
                return employeeEntities;
            }
        }

        //Если количество не указано, или указано неправильно
        return employeeRepository.create(companyId);
    }
}
