package by.gstu.phonestation.dao;

import by.gstu.phonestation.administrator.Administrator;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public abstract class AbstractDAOAdministrator {
    public abstract Administrator getAdministrator();

    private static Logger log = Logger.getLogger(AbstractDAOAdministrator.class);

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
