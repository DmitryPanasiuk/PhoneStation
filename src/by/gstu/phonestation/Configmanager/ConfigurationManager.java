package by.gstu.phonestation.Configmanager;

import org.apache.log4j.Logger;
import java.util.ResourceBundle;

/**
 * Class for configuration and sql queries reading from .properties files
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class ConfigurationManager {

    private static ConfigurationManager instance = null;

    private static ResourceBundle dbProperties;
    private static ResourceBundle sqlQueries;
    private static Logger log = Logger.getLogger(ConfigurationManager.class);

    private ConfigurationManager() {
    }

    public static ConfigurationManager getConfiguration() {
        if (instance == null) {
            instance = new ConfigurationManager();
            dbProperties = ResourceBundle.getBundle("DBproperties");
            sqlQueries = ResourceBundle.getBundle("SQLqueries");
        }
        return instance;
    }

    public String getUrl() {
        return dbProperties.getString("url");
    }

    public String getUser() {
        return dbProperties.getString("user");
    }

    public String getPassword() {
        return dbProperties.getString("password");
    }

    public String getAddSubscriber() {
        return sqlQueries.getString("addSubscriber");
    }

    public String getGetLastID() {
        return sqlQueries.getString("getLastID");
    }

    public String getAddAccount() {
        return sqlQueries.getString("addAccount");
    }

    public String getAddService() {
        return sqlQueries.getString("addService");
    }

    public String getAddUserPassword() {
        return sqlQueries.getString("addUserPassword");
    }

    public String getChangeUserPassword() {
        return sqlQueries.getString("changeUserPassword");
    }

    public String getDeteteUserPassword() {
        return sqlQueries.getString("deleteUserPassword");
    }

    public String getDeleteAllService() {
        return sqlQueries.getString("deleteAllService");
    }

    public String getSetPayment() {
        return sqlQueries.getString("setPayment");
    }

    public String getGetNegativeAccounts() {
        return sqlQueries.getString("getNegativeAccounts");
    }

    public String getBlockNegativeAccounts() {
        return sqlQueries.getString("blockNegativeAccounts");
    }

    public String getPaymentBySubscriberId() {
        return sqlQueries.getString("paymentBySubscriberId");
    }

    public String getUnblockPositiveSubscribers() {
        return sqlQueries.getString("unblockPositiveSubscribers");
    }

    public String getDeleteSubscriberById() {
        return sqlQueries.getString("deleteSubscriberById");
    }

    public String getIdServiceByName() {
        return sqlQueries.getString("idServiceByName");
    }

    public String getGetAdministrator() {
        return sqlQueries.getString("getAdministrator");
    }

    public String getCheckUserPassword() {
        return sqlQueries.getString("checkUserPassword");
    }

    public String getGetSubscriberByName() {
        return sqlQueries.getString("getSubscriberByName");
    }

    public String getGetAllServices() {
        return sqlQueries.getString("getAllServices");
    }

    public String getGetAllSubscribers() {
        return sqlQueries.getString("getAllSubscribers");
    }

    public String getBlockSubscriber() {
        return sqlQueries.getString("blockSubscriber");
    }
}
