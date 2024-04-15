package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.db.EmployeeEntity;
import net.datafaker.Faker;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeRepositoryHiber implements EmployeeRepository {
    private EntityManager em;
    private final static String PREFIX = "TS_";
    private Faker faker = new Faker(new Locale("RU"));

    public EmployeeRepositoryHiber(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<EmployeeEntity> getAllByCompanyId(int companyId) {
        TypedQuery<EmployeeEntity> query = em.createQuery(
                "SELECT e FROM EmployeeEntity e WHERE companyId = :id", EmployeeEntity.class);
        query.setParameter("id", companyId);
        return query.getResultList();
    }

    @Override
    public EmployeeEntity getById(int id) {
        return em.find(EmployeeEntity.class, id);
    }

    @Override
    public int create(EmployeeEntity e) {
        int lastId = getLast().getId();
        e.setId(++lastId);

        //Сохранение сотрудника в БД
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        return e.getId();
    }

    @Override
    public EmployeeEntity create(int companyId) {

        EmployeeEntity employee = new EmployeeEntity();
        int lastId = getLast().getId();
        employee.setId(lastId + 1);

        String[] name = faker.name().nameWithMiddle().split(" ");
        employee.setFirstName(PREFIX + name[0]);
        employee.setLastName(name[2]);
        employee.setMiddleName(name[1]);

        employee.setCompanyId(companyId);

        employee.setEmail(faker.internet().emailAddress("a" + faker.number().digits(5)));

        employee.setAvatarUrl(faker.internet().url());
//        employee.setPhone(faker.phoneNumber().phoneNumber()); //Не проходит по формату

        //TODO: 1. Написать BUG-репорт - при создании с неправильным телефоном возвращается ошибка 500 вместо 400
        employee.setPhone(faker.number().digits(10));

        Timestamp tmp = Timestamp.valueOf(LocalDateTime.now());
        employee.setCreateTimestamp(tmp);
        employee.setChangeTimestamp(tmp);

        employee.setBirthdate(Date.valueOf(faker.date().birthday("YYYY-MM-dd")));

        employee.setActive(true);

        //Сохранение сотрудника в БД
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        return employee;
    }

    @Override
    public int update(EmployeeEntity e) {
        e.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        return 0;
    }

    @Override
    public void deleteById(int id) {
        EmployeeEntity employee = em.find(EmployeeEntity.class, id);
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        if (employee == null) return;
        em.remove(employee);
        em.getTransaction().commit();
        System.out.println("Удален сотрудник с id = " + id);
    }

    @Override
    public EmployeeEntity getLast() {
        TypedQuery<EmployeeEntity> query = em.createQuery(
                "SELECT e FROM EmployeeEntity e ORDER BY e.id DESC LIMIT 1", EmployeeEntity.class);
        return query.getSingleResult();
    }

    @Override
    public List<EmployeeEntity> getAll() {
        TypedQuery<EmployeeEntity> query = em.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class);
        return query.getResultList();
    }

    @Override
    public boolean deleteAllByCompanyId(int companyId) {
        if (companyId < 0) return false;
        List<EmployeeEntity> employees = new ArrayList<>();
        try {
            TypedQuery<EmployeeEntity> query = em.createQuery(
                    "SELECT e FROM EmployeeEntity e WHERE companyId = :id", EmployeeEntity.class);
            query.setParameter("id", companyId);
            employees = query.getResultList();
        } catch (Exception e) {
            return true;    //Если ничего не нашли и при парсинге результата выпало исключение
        }

        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        for (EmployeeEntity empl : employees) {
            em.remove(empl);
        }
        em.getTransaction().commit();

        return true;
    }

    @Override
    public boolean clean(String prefix) {
        if (prefix.equals("")) prefix = "TS_";
        TypedQuery<EmployeeEntity> query = em.createQuery(
                "SELECT e FROM EmployeeEntity e WHERE firstName like 'TS_%'", EmployeeEntity.class);
//        query.setParameter("prefix", prefix);
        List<EmployeeEntity> list = query.getResultList();
        for (EmployeeEntity e : list) {
            deleteById(e.getId());
        }
        return true;
    }
}
