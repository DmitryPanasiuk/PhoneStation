package by.gstu.phonestation.dao;

import by.gstu.phonestation.Configmanager.ConfigurationManager;
import by.gstu.phonestation.administrator.Administrator;
import by.gstu.phonestation.connectionpool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for sql queries
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class DAOAdministrator extends AbstractDAOAdministrator {
    private static final ConfigurationManager configuration = ConfigurationManager.getConfiguration();
    private static Logger log = Logger.getLogger(DAOAdministrator.class);

    @Override
    public Administrator getAdministrator() {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;
        int id = 0;
        String name = "";

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getGetAdministrator());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
            name = resultSet.getString(2);

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return new Administrator(id, name);
    }
}
