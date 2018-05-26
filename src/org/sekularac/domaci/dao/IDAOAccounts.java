package org.sekularac.domaci.dao;

import org.sekularac.domaci.entities.Accounts;

public interface IDAOAccounts extends IDAOAbstract<Accounts> {
    boolean login(Accounts accounts);
}
