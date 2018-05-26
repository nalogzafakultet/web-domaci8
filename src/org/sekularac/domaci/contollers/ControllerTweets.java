package org.sekularac.domaci.contollers;



import org.sekularac.domaci.dao.impl.DAOTweets;
import org.sekularac.domaci.entities.Tweets;
import org.sekularac.domaci.services.IServiceTweets;
import org.sekularac.domaci.services.impl.ServiceTweets;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("/tweets")
public class ControllerTweets {

    private IServiceTweets serviceTweets;

    public ControllerTweets() {
        this.serviceTweets = new ServiceTweets(new DAOTweets());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweets> getAll() {
        return this.serviceTweets.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean add(Tweets tweet) {
        return this.serviceTweets.add(tweet);
    }

    @GET
    @Path("/page/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweets> pagination(@PathParam("pageNumber") Integer pageNumber) {
        return this.serviceTweets.getNextTweets(10, pageNumber);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweets> searchByUsername(@QueryParam("username") String username) {
        return this.serviceTweets.searchTweetsByUsername(username);
    }
}
