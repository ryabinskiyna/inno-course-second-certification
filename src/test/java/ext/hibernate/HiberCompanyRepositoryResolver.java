package ext.hibernate;

import db.CompanyRepository;
import db.CompanyRepositoryHiber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class HiberCompanyRepositoryResolver implements ParameterResolver {
    private final String EMF_GLOBAL_KEY = "EntityManagerFactory";  //Название ключа EntityManagerFactory в хранилище

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CompanyRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        //Вытаскиваем сохранённый EntityManager из extensionContext
        EntityManagerFactory entityManagerFactory =
                (EntityManagerFactory) extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get(EMF_GLOBAL_KEY);

        //Для каждого теста создаём свой EntityManager, т.к. он не потокобезопасный
        // (п. 4.3. https://translated.turbopages.org/proxy_u/en-ru.ru.5b18764a-64d1f0a4-194f148e-74722d776562/https/www.baeldung.com/hibernate-entitymanager)
        EntityManager em = entityManagerFactory.createEntityManager();

        return new CompanyRepositoryHiber(em);
    }
}
