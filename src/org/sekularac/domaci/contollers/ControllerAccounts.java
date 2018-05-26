package org.sekularac.domaci.contollers;

import org.sekularac.domaci.dao.impl.DAOAccounts;
import org.sekularac.domaci.entities.Accounts;
import org.sekularac.domaci.services.IServiceAccounts;
import org.sekularac.domaci.services.impl.ServiceAccounts;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@LocalBean
@Path("/users")
public class ControllerAccounts {

    private IServiceAccounts serviceAccounts;

    public ControllerAccounts() {
        this.serviceAccounts = new ServiceAccounts(new DAOAccounts());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean registerUser(Accounts user) {
        return this.serviceAccounts.add(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Accounts> listAll() {
        return this.serviceAccounts.getAll();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean login(Accounts accounts) {
        return serviceAccounts.login(accounts);
    }

}
