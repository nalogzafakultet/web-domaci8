package org.sekularac.domaci.dao;

import org.sekularac.domaci.entities.Tweets;

import java.util.List;

public interface IDAOTweets extends IDAOAbstract<Tweets> {
    List<Tweets> getNextTweets(int number, int offset);
    List<Tweets> searchTweetsByUsername(String username);
}
