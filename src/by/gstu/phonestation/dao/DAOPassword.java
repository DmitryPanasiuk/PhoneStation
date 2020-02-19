package by.gstu.phonestation.dao;

import by.gstu.phonestation.Configmanager.ConfigurationManager;
import by.gstu.phonestation.connectionpool.ConnectionPool;
import by.gstu.phonestation.subscriber.Subscriber;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOPassword extends AbstractDAOPassword {
    private static final ConfigurationManager configuration = ConfigurationManager.getConfiguration();
    private static Logger log = Logger.getLogger(DAOPassword.class);

    //Getting account access level
    @Override
    public int checkPassword(String name, String password) {
        int level = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getCheckUserPassword());
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            level = resultSet.getInt(3);

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return level;
    }

    //Subscriber password change
    @Override
    public void changePassword(Subscriber subscriber, String newPass) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getChangeUserPassword());
            statement.setString(1, newPass);
            statement.setString(2, subscriber.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
