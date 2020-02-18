package by.gstu.phonestation.dao;

import by.gstu.phonestation.service.Service;
import by.gstu.phonestation.subscriber.Subscriber;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public abstract class AbstractDAOSubscriber {
    public abstract void blockNegativeSubscribers();

    public abstract void blockSubscriber(Subscriber subscriber);

    public abstract void unblockPositiveSubscribers();

    public abstract void addSubscriber(Subscriber subscriber);

    public abstract void changeServicesList(int subscriberId, List<Service> services);

    public abstract void deleteSubscriberById(Subscriber subscriber);

    public abstract int idServiceByName(String nameService);

    public abstract Subscriber getSubscriberByName(String name);

    public abstract List<Service> getAllServices();

    public abstract List<Subscriber> getAllSubscribers();

    private static Logger log = Logger.getLogger(AbstractDAOSubscriber.class);

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.warn("A database access error occured. " + e);
        }
    }
}
