package db;

import model.db.CompanyEntity;

import java.sql.SQLException;
import java.util.List;

public interface CompanyRepository {
    List<CompanyEntity> getAll() throws SQLException;

    List<CompanyEntity> getAll(boolean isActive) throws SQLException;

    CompanyEntity getLast() throws SQLException;

    CompanyEntity getById(int id) throws SQLException;

    int create(String name) throws SQLException;

    int create(String name, String description) throws SQLException;

    void deleteById(int id);

    boolean clean(String prefix) throws SQLException;

}
