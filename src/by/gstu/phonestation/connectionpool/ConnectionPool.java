package by.gstu.phonestation.connectionpool;

import by.gstu.phonestation.Configmanager.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for connections pool creation
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class ConnectionPool {
    private String url;
    private String user;
    private String password;
    private static ConnectionPool connectionPool = null;
    private List<Connection> pool = new ArrayList<>();
    private List<Connection> usingConnections = new ArrayList<>();
    private static int poolSize = 10;

    private ConnectionPool() {
    }

    public static ConnectionPool create() throws SQLException {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            ConfigurationManager configuration = ConfigurationManager.getConfiguration();
            connectionPool.url = configuration.getUrl();
            connectionPool.user = configuration.getUser();
            connectionPool.password = configuration.getPassword();

            for (int i = 0; i < poolSize; i++) {
                connectionPool.pool.add(createConnection(connectionPool.url,
                        connectionPool.user, connectionPool.password));
            }
        }
        return connectionPool;
    }

    //Taking DB connection from pool
    public Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            pool.add(createConnection(url, user, password));
        }
        Connection connection = pool.remove(pool.size() - 1);
        usingConnections.add(connection);
        return connection;
    }

    //Return DB connection to pool
    public void releaseConnection(Connection connection) {
        pool.add(connection);
        usingConnections.remove(connection);
    }

    //Create DB connection
    private static Connection createConnection(String url, String user, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
}
