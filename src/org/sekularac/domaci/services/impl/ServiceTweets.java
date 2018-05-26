package org.sekularac.domaci.services.impl;

import org.sekularac.domaci.dao.impl.DAOTweets;
import org.sekularac.domaci.entities.Tweets;
import org.sekularac.domaci.services.IServiceTweets;

import java.util.List;

public class ServiceTweets extends ServiceAbstract<Tweets, DAOTweets> implements IServiceTweets {

    public ServiceTweets(DAOTweets daoTweets) {
        super(daoTweets);
    }

    @Override
    public List<Tweets> getNextTweets(int number, int offset) {
        return getDao().getNextTweets(number, offset);
    }

    @Override
    public List<Tweets> searchTweetsByUsername(String username) {
        return getDao().searchTweetsByUsername(username);
    }
}
