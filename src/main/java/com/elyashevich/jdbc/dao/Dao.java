package com.elyashevich.jdbc.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<Entity> {
    List<Entity> findAll() throws SQLException;

    Entity findById(Long id) throws SQLException;

    void create(Entity Entity) throws SQLException;

    void update(Entity Entity) throws SQLException;

    void remove(Entity Entity) throws SQLException;
}
