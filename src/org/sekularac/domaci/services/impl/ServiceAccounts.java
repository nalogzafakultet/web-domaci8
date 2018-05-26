package org.sekularac.domaci.services.impl;

import org.sekularac.domaci.dao.impl.DAOAccounts;
import org.sekularac.domaci.entities.Accounts;
import org.sekularac.domaci.services.IServiceAccounts;

public class ServiceAccounts extends ServiceAbstract<Accounts, DAOAccounts> implements IServiceAccounts {

    public ServiceAccounts(DAOAccounts daoAccounts) {
        super(daoAccounts);
    }

    @Override
    public boolean login(Accounts accounts) {
        return getDao().login(accounts);
    }
}
