package org.sekularac.domaci.services.impl;

import org.sekularac.domaci.dao.IDAOAbstract;
import org.sekularac.domaci.entities.BasicEntity;
import org.sekularac.domaci.services.IServiceAbstract;

import java.util.List;

public abstract class ServiceAbstract<T extends BasicEntity, DAO extends IDAOAbstract<T>> implements IServiceAbstract<T> {

    private DAO dao;

    public ServiceAbstract(DAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean add(T entity) {
        return dao.add(entity);
    }

    @Override
    public boolean removeById(int id) {
        return dao.removeById(id);
    }

    @Override
    public boolean update(T entity) {
        return dao.update(entity);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public T getById(int id) {
        return dao.getById(id);
    }

    public DAO getDao() {
        return dao;
    }
}
