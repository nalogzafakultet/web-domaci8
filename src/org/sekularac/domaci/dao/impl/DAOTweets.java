package org.sekularac.domaci.dao.impl;

import org.sekularac.domaci.dao.IDAOTweets;
import org.sekularac.domaci.entities.Tweets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOTweets extends DAOAbstractDatabase<Tweets> implements IDAOTweets {

    public DAOTweets() {
        super(Tweets.class);
    }

    @Override
    public List<Tweets> getNextTweets(int number, int offset) {
        connection = makeConnection();

        if (connection == null) {
            return null;
        }

        String query = "SELECT * FROM tweets ORDER BY id LIMIT ?, ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, ((offset - 1) * number));
            preparedStatement.setObject(2, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Tweets> resultTweets = new ArrayList<>();
            while (resultSet.next()) {
                resultTweets.add(readFromResultSet(resultSet));
            }

            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            return resultTweets;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    @Override
    public List<Tweets> searchTweetsByUsername(String username) {

        connection = makeConnection();

        // Nismo uspeli da se konektujemo
        if (connection == null) {
            return null;
        }

        String query = "SELECT * FROM tweets WHERE username=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Tweets> returnTweets = new ArrayList<>();
            while (resultSet.next()) {
                returnTweets.add(readFromResultSet(resultSet));
            }

            closeResultSet(resultSet);
            closeStatement(preparedStatement);

            return returnTweets;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }
}
