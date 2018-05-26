package org.sekularac.domaci.dao.impl;

import org.sekularac.domaci.dao.IDAOAccounts;
import org.sekularac.domaci.entities.Accounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOAccounts extends DAOAbstractDatabase<Accounts> implements IDAOAccounts {

    public DAOAccounts() {
        super(Accounts.class);
    }

    @Override
    public boolean login(Accounts accounts) {
        connection = makeConnection();

        if (connection == null) {
            return false;
        }

        String queryPassword = null;
        String query = String.format("SELECT password FROM accounts WHERE %s = \"%s\" AND %s = \"%s\"",
                Accounts.COLUMN_USERNAME, accounts.getUsername(), Accounts.COLUMN_PASSWORD, accounts.getPassword());

        System.out.println("Login q: " + query);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (query != null)
                System.out.println("Q PASS:" + queryPassword);

            boolean result = resultSet.next();

            closeResultSet(resultSet);
            closeStatement(preparedStatement);


            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }
}
