package by.gstu.phonestation.subscriber;

import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.dao.*;
import by.gstu.phonestation.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class describes subscriber and their behavior
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class Subscriber {
    private int id;
    private String name;
    private String address;
    private Account account;
    private String blocking;
    private List<Service> services = new ArrayList<>();
    private static AbstractDAOSubscriber daoSubscriber;
    private static AbstractDAOAccount daoAccount;
    private static AbstractDAOPassword daoPassword;

    static {
        AbstractFactory abstractFactory = new DAOFactory();
        daoSubscriber = abstractFactory.getDAOSubscriber();
        daoAccount = abstractFactory.getDAOAccount();
        daoPassword = abstractFactory.getDAOPassword();
    }

    public Subscriber(String name, String address, String blocking) {
        this.name = name;
        this.address = address;
        this.blocking = blocking;
    }

    public void changeServicesList(List<Service> services) {
        daoSubscriber.changeServicesList(id, services);
    }

    //Creation account for a new subscriber
    public void createAccount() {
        account = new Account(this);
    }

    //subscriber depositing money
    public void pay(int amount) {
        daoAccount.pay(this, amount);
    }

    public static Subscriber getSubscriberByName(String name) {
        Subscriber subscriber = daoSubscriber.getSubscriberByName(name);
        return subscriber;
    }

    public void changePassword(String newPass) {
        daoPassword.changePassword(this, newPass);
    }

    public List<Service> receiveAllServices() {
        List<Service> allServices = daoSubscriber.getAllServices();
        return allServices;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBlocking() {
        return blocking;
    }

    public List<Service> getServices() {
        return services;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                getAddress().equals(that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress());
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' + '}';
    }
}
