package ru.kpfu.itis.Kolodeznikova.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void create(T entity) throws SQLException;
    T findById(int id) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}
