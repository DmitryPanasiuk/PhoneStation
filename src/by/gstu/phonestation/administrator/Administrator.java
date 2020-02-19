package by.gstu.phonestation.administrator;

import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.dao.*;
import by.gstu.phonestation.subscriber.Subscriber;

import java.util.List;

/**
 * Class describes administrator and their behavior
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class Administrator {
    private int id;
    private String name;
    private static AbstractDAOSubscriber daoSubscriber;
    private static AbstractDAOAccount daoAccount;
    private static AbstractDAOAdministrator daoAdministrator;
    private static AbstractDAOPassword daoPassword;

    static {
        AbstractFactory abstractFactory = new DAOFactory();
        daoSubscriber = abstractFactory.getDAOSubscriber();
        daoAccount = abstractFactory.getDAOAccount();
        daoAdministrator = abstractFactory.getDAOAdministrator();
        daoPassword = abstractFactory.getDAOPassword();
    }

    public Administrator(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Adding subscriber to DB
    public void addSubscriber(Subscriber subscriber) {
        daoSubscriber.addSubscriber(subscriber);
    }

    public static Administrator getAdministrator() {
        return daoAdministrator.getAdministrator();
    }

    //Getting accounts list with negative balance
    public List<Account> getNegativeAccounts() {
        List<Account> accounts = daoAccount.getNegativeAccounts();
        return accounts;
    }

    //Subscriber blocking
    public void blockSubscriber(Subscriber subscriber) {
        daoSubscriber.blockSubscriber(subscriber);
    }

    //Blocking of negative balance accounts
    public void blockNegativeSubscribers() {
        daoSubscriber.blockNegativeSubscribers();
    }

    //Unblocking of positive balance accounts
    public void unblockPositiveSubscribers() {
        daoSubscriber.unblockPositiveSubscribers();
    }

    //Deletion subscriber by id
    public void deleteSubscriberById(Subscriber subscriber) {
        daoSubscriber.deleteSubscriberById(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return daoSubscriber.getAllSubscribers();
    }

    public int checkPassword(String name, String password) {
        int level = daoPassword.checkPassword(name, password);
        return level;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
