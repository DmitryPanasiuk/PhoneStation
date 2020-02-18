package by.gstu.phonestation.dao;

/**
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class DAOFactory extends AbstractFactory {
    @Override
    public AbstractDAOSubscriber getDAOSubscriber() {
        return new DAOSubscriber();
    }

    @Override
    public AbstractDAOAccount getDAOAccount() {
        return new DAOAccount();
    }

    @Override
    public AbstractDAOAdministrator getDAOAdministrator() {
        return new DAOAdministrator();
    }

    @Override
    public AbstractDAOPassword getDAOPassword() {
        return new DAOPassword();
    }
}
