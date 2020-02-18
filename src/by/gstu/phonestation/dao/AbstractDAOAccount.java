package by.gstu.phonestation.dao;

import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.subscriber.Subscriber;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAOAccount {
    public abstract void pay(Subscriber subscriber, int amount);

    public abstract List<Account> getNegativeAccounts();

    private static Logger log = Logger.getLogger(AbstractDAOAccount.class);

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
