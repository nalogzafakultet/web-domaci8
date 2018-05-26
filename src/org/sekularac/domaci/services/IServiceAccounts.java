package org.sekularac.domaci.services;

import org.sekularac.domaci.entities.Accounts;

public interface IServiceAccounts extends IServiceAbstract<Accounts> {
    boolean login(Accounts accounts);
}
