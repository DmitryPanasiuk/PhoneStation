package by.gstu.phonestation.dao;

import by.gstu.phonestation.subscriber.Subscriber;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAOPassword {
    public abstract int checkPassword(String name, String password);
    public abstract void changePassword(Subscriber subscriber, String newPass);

    private static Logger log = Logger.getLogger(AbstractDAOPassword.class);

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
