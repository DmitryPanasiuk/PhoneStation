package by.gstu.phonestation.dao;

/**
 * @version 1.0
 * @author Dmitry Panasiuk
 */
public abstract class AbstractFactory {
    public abstract AbstractDAOSubscriber getDAOSubscriber();
    public abstract AbstractDAOAccount getDAOAccount();
    public abstract AbstractDAOAdministrator getDAOAdministrator();
    public abstract AbstractDAOPassword getDAOPassword();
}