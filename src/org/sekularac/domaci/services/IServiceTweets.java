package org.sekularac.domaci.services;

import org.sekularac.domaci.entities.Tweets;

import java.util.List;

public interface IServiceTweets extends IServiceAbstract<Tweets>{
    List<Tweets> getNextTweets(int number, int offset);
    List<Tweets> searchTweetsByUsername(String username);
}
