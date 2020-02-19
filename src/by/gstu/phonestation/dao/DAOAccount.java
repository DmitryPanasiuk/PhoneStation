package by.gstu.phonestation.dao;

import by.gstu.phonestation.Configmanager.ConfigurationManager;
import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.connectionpool.ConnectionPool;
import by.gstu.phonestation.subscriber.Subscriber;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for sql queries
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class DAOAccount extends AbstractDAOAccount {
    private static final ConfigurationManager configuration = ConfigurationManager.getConfiguration();
    private static Logger log = Logger.getLogger(DAOAccount.class);

    //subscriber depositing money
    @Override
    public void pay(Subscriber subscriber, int amount) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getPaymentBySubscriberId());
            statement.setInt(1, amount);
            statement.setInt(2, subscriber.getId());
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

    //Getting accounts list with negative balance
    @Override
    public List<Account> getNegativeAccounts() {
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getGetNegativeAccounts());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int accountNumber = resultSet.getInt(5);
                int idSubscriber = resultSet.getInt(6);
                int allFee = resultSet.getInt(7);
                int state = resultSet.getInt(8);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String blocking = resultSet.getString(4);
                Account account = new Account(accountNumber, idSubscriber, allFee, state);
                account.setOwner(new Subscriber(name, address, blocking));
                accounts.add(account);
            }

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return accounts;
    }
}
