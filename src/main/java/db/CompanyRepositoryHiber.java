package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.db.CompanyEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CompanyRepositoryHiber implements CompanyRepository {
    private final EntityManager em;

    public CompanyRepositoryHiber(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<CompanyEntity> getAll() {
        TypedQuery<CompanyEntity> query = em.createQuery(
                "SELECT c FROM CompanyEntity c WHERE c.deletedAt is null", CompanyEntity.class);
        return query.getResultList();


    }

    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        TypedQuery<CompanyEntity> query = em.createQuery(
                "SELECT c FROM CompanyEntity c WHERE c.deletedAt is null and isActive = :isActive",
                CompanyEntity.class);

        query.setParameter("isActive", isActive);
        return query.getResultList();
    }

    @Override
    public CompanyEntity getLast() {
        TypedQuery<CompanyEntity> query = em.createQuery(
                "SELECT c FROM CompanyEntity c ORDER BY c.id DESC LIMIT 1", CompanyEntity.class);
        return query.getSingleResult();
    }

    @Override
    public CompanyEntity getById(int id) {
//        TypedQuery<CompanyEntity> query = em.createQuery(
//                "SELECT c FROM CompanyEntity c WHERE c.deletedAt is null and c.id =:id", CompanyEntity.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();

        //Или через Hibernate API
        return em.find(CompanyEntity.class, id);
    }

    @Override
    public int create(String name) throws SQLException {
        CompanyEntity company = new CompanyEntity();
        int lastId = getLast().getId();
        company.setName(name);
        company.setId(++lastId);
        Timestamp tmp = Timestamp.valueOf(LocalDateTime.now());
        company.setCreateDateTime(tmp);
        company.setChangedTimestamp(tmp);

        //Сохранение компании в БД
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
        return company.getId();
    }

    @Override
    public int create(String name, String description) throws SQLException {
        CompanyEntity company = new CompanyEntity();
        int lastId = getLast().getId();
        company.setName(name);
        company.setDescription(description);
        company.setId(++lastId);
        Timestamp tmp = Timestamp.valueOf(LocalDateTime.now());
        company.setCreateDateTime(tmp);
        company.setChangedTimestamp(tmp);

        //Сохранение компании в БД
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
        return company.getId();
    }

    @Override
    public void deleteById(int id) {
        CompanyEntity company = em.find(CompanyEntity.class, id);
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        if (company == null) return;
        em.remove(company);
        em.getTransaction().commit();
        System.out.println("Удалена компания с id = " + id);
    }

    @Override
    public boolean clean(String prefix) {
        if (prefix.equals("")) prefix = "TS_";
        TypedQuery<CompanyEntity> query = em.createQuery(
                "SELECT c FROM CompanyEntity c WHERE name like 'TS_%'", CompanyEntity.class);
//        query.setParameter("prefix", prefix);
        List<CompanyEntity> list = query.getResultList();
        for (CompanyEntity c : list) {
            deleteById(c.getId());
        }
        return true;
    }
}
