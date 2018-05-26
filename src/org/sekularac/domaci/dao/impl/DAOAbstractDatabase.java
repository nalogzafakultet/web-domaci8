package org.sekularac.domaci.dao.impl;

import org.sekularac.domaci.dao.IDAOAbstract;
import org.sekularac.domaci.entities.BasicEntity;
import org.sekularac.domaci.utils.Utils;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class DAOAbstractDatabase<T extends BasicEntity> implements IDAOAbstract<T> {

    private Class<T> mClass;
    private static final String DB_NAME = "web_domaci";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    protected Connection connection;

    public DAOAbstractDatabase(Class<T> classParam) {
        mClass = classParam;
    }


    public Connection makeConnection() {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean add(T entity) {
        // Ne postoji objekt koji dodajemo
        if (entity == null) {
            return false;
        }

        connection = makeConnection();

        // Neuspesna konekcija na bazu
        if (connection == null) {
            return false;
        }

        StringJoiner columnJoiner = new StringJoiner(", ");
        StringJoiner replacerJoiner = new StringJoiner(", ");
        StringJoiner valueJoiner = new StringJoiner(", ");

        for (String columnName: entity.columnNames()) {

            // Preskacemo dodavanje kolone ID, jer je autoincrement
            if (!entity.primaryKeyColumnName().equals(columnName)) {
                columnJoiner.add(columnName);
                replacerJoiner.add("?");
                valueJoiner.add(entity.getValueForColumnName(columnName).toString());
            }
        }

        String addQuery = String.format("INSERT INTO %s (%s) VALUES (%s);",
                this.mClass.getSimpleName().toLowerCase(), columnJoiner.toString(), replacerJoiner.toString());


        try {
            PreparedStatement statement = connection.prepareStatement(addQuery);
            int idx = 1;
            for (String columnName: entity.columnNames()) {
                if (!columnName.equals(entity.primaryKeyColumnName())) {
                    statement.setObject(idx++, entity.getValueForColumnName(columnName).toString());
                }
            }
            // Rezultat operacije: true / false

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            closeConnection();
        }
        return false;
    }

    /**
     *  Nepotreban za projekat
     * @param id
     * @return
     */
    @Override
    public boolean removeById(int id) {
        return false;
    }

    /**
     * Nepotrebno
     * @param object
     * @return
     */
    @Override
    public boolean update(T object) {
        return false;
    }

    @Override
    public List<T> getAll() {
        connection = makeConnection();

        if (connection == null) {
            return null;
        }
        List<T> returnObjects = new ArrayList<>();

        String allStatement = "SELECT * FROM " + mClass.getSimpleName().toLowerCase();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(allStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                returnObjects.add(readFromResultSet(resultSet));
            }

            closeResultSet(resultSet);
            closeStatement(preparedStatement);

            return returnObjects;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }


        return returnObjects;
    }

    @Override
    public T getById(int id) {

        connection = makeConnection();

        if (connection == null) {
            return null;
        }

        try {
            T newObject = mClass.newInstance();
            String query = "SELECT * FROM ? WHERE ?=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, mClass.getSimpleName().toLowerCase());
            preparedStatement.setObject(2, newObject.primaryKeyColumnName());
            preparedStatement.setObject(3, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            T returnedObject = null;

            if (resultSet.next()) {
                returnedObject = readFromResultSet(resultSet);
            }

            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            return returnedObject;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public T readFromResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        try {
            T newObject = mClass.newInstance();

            for (String columnName: newObject.columnNames()) {
                newObject.setValueForColumnName(columnName, resultSet.getObject(columnName));
            }

            return newObject;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
