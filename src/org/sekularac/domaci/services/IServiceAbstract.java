package org.sekularac.domaci.services;

import org.sekularac.domaci.entities.BasicEntity;

import java.util.List;

public interface IServiceAbstract<T extends BasicEntity> {
    boolean add(T entity);
    boolean removeById(int id);
    boolean update(T entity);
    List<T> getAll();
    T getById(int id);
}
