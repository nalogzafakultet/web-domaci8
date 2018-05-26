package org.sekularac.domaci.dao;

import org.sekularac.domaci.entities.BasicEntity;

import java.util.List;

public interface IDAOAbstract<T extends BasicEntity> {
    boolean add(T entity);
    boolean removeById(int id);
    boolean update(T entity);
    List<T> getAll();
    T getById(int id);
}
