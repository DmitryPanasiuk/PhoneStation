package by.gstu.phonestation.dao;

import by.gstu.phonestation.Configmanager.ConfigurationManager;
import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.connectionpool.ConnectionPool;
import by.gstu.phonestation.service.Service;
import by.gstu.phonestation.service.ServiceType;
import by.gstu.phonestation.subscriber.Subscriber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import static by.gstu.phonestation.service.ServiceType.defineType;

/**
 * Class for sql queries
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class DAOSubscriber extends AbstractDAOSubscriber {
    private static final ConfigurationManager configuration = ConfigurationManager.getConfiguration();
    private static Logger log = Logger.getLogger(DAOSubscriber.class);

    //Blocking of negative balance accounts
    @Override
    public void blockNegativeSubscribers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getBlockNegativeAccounts());
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

    //Subscriber blocking
    @Override
    public void blockSubscriber(Subscriber subscriber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getBlockSubscriber());
            statement.setInt(1, subscriber.getId());
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

    //Unblocking of positive balance accounts
    @Override
    public void unblockPositiveSubscribers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getUnblockPositiveSubscribers());
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

    //Adding subscriber to DB
    @Override
    public void addSubscriber(Subscriber subscriber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            //adding a subscriber
            statement = connection.prepareStatement(configuration.getAddSubscriber());
            statement.setString(1, subscriber.getName());
            statement.setString(2, subscriber.getAddress());
            statement.setString(3, subscriber.getBlocking());
            statement.executeUpdate();

            //getting of an added subscriber id
            statement = connection.prepareStatement(configuration.getGetLastID());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int lastId = resultSet.getInt(1);

            //creating subscriber's password
            statement = connection.prepareStatement(configuration.getAddUserPassword());
            statement.setString(1, subscriber.getName());
            statement.setString(2, "password");
            statement.setInt(3, 2);
            statement.executeUpdate();

            //creation of an added subscriber account
            statement = connection.prepareStatement(configuration.getAddAccount());
            statement.setInt(1, lastId);
            statement.setInt(2, subscriber.getAccount().getAllFee());
            statement.setInt(3, subscriber.getAccount().getState());
            statement.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                log.warn("A database access error occured. " + e1);
            }
        } finally {
            closeStatement(statement);

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.warn("A database access error occured. " + e);
            }

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }

    //changing the list of services by the subscriber
    @Override
    public void changeServicesList(int subscriberId, List<Service> services) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;
        int payment = Account.getCallFee();

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            //Delete all subscriber services
            statement = connection.prepareStatement(configuration.getDeleteAllService());
            statement.setInt(1, subscriberId);
            statement.executeUpdate();

            //Add services to a subscriber
            statement = connection.prepareStatement(configuration.getAddService());
            for (int i = 0; i < services.size(); i++) {
                payment += services.get(i).getName().getCost();
                statement.setInt(1, subscriberId);
                statement.setInt(2, services.get(i).getId());

                statement.executeUpdate();
            }

            //Setting the cost of services
            statement = connection.prepareStatement(configuration.getSetPayment());
            statement.setInt(1, payment);
            statement.setInt(2, subscriberId);
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                log.warn("A database access error occured. " + e1);
            }
        } finally {
            closeStatement(statement);

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.warn("A database access error occured. " + e);
            }

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }

    //Id definition by string value
    @Override
    public int idServiceByName(String nameService) {
        int id = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getIdServiceByName());
            statement.setString(1, nameService);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

        return id;
    }

    //Deletion subscriber by id
    @Override
    public void deleteSubscriberById(Subscriber subscriber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(configuration.getDeleteSubscriberById());
            statement.setInt(1, subscriber.getId());
            statement.executeUpdate();

            statement = connection.prepareStatement(configuration.getDeteteUserPassword());
            statement.setString(1, subscriber.getName());
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                log.warn("A database access error occured. " + e1);
            }
        } finally {
            closeStatement(statement);

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.warn("A database access error occured. " + e);
            }

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    //Get a subscriber by name
    @Override
    public Subscriber getSubscriberByName(String subscriberName) {
        Subscriber subscriber = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getGetSubscriberByName());
            statement.setString(1, subscriberName);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int idSubscriber = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String blocking = resultSet.getString(4);
            int accountNumber = resultSet.getInt(5);
            int allFee = resultSet.getInt(6);
            int state = resultSet.getInt(7);

            subscriber = new Subscriber(name, address, blocking);
            subscriber.setId(idSubscriber);
            subscriber.setAccount(new Account(accountNumber, idSubscriber, allFee, state));

            if (resultSet.getObject(8) != null) {
                String[] namesServices = resultSet.getString(8).split(",");
                String[] costsServices = resultSet.getString(9).split(",");

                List<Service> services = new ArrayList<>();
                for (int i = 0; i < namesServices.length; i++) {
                    ServiceType serviceType = defineType(namesServices[i]);
                    int id = idServiceByName(namesServices[i]);
                    services.add(new Service(id, serviceType, subscriber));
                }
                subscriber.setServices(services);
            }

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return subscriber;
    }

    //Get a list of all services
    @Override
    public List<Service> getAllServices() {
        List<Service> allServices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getGetAllServices());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idService = resultSet.getInt(1);
                String nameService = resultSet.getString(2);
                int costService = resultSet.getInt(3);
                allServices.add(new Service(idService, defineType(nameService), null));
            }

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return allServices;
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        List<Subscriber> allSubscribers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool connectionPool = null;

        try {
            connectionPool = ConnectionPool.create();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(configuration.getGetAllSubscribers());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idSubscriber = resultSet.getInt(1);
                String nameSubscriber = resultSet.getString(2);
                String address = resultSet.getString(3);
                String lockStatus = resultSet.getString(4);
                Subscriber subscriber = new Subscriber(nameSubscriber, address, lockStatus);
                subscriber.setId(idSubscriber);
                allSubscribers.add(subscriber);
            }

        } catch (SQLException e) {
            log.warn("A database access error occured or database connection not established. " + e);
        } finally {
            closeStatement(statement);
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return allSubscribers;
    }
}
