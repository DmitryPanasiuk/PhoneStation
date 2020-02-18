package by.gstu.phonestation.account;

import by.gstu.phonestation.subscriber.Subscriber;

import java.util.Objects;

/**
 * Class describes subscriber account and its behavior
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class Account {
    private Subscriber owner;
    private int idSubscriber;
    private int number;
    static private final int callFee = 5;
    private int allFee = callFee;
    private int state;

    public Account(Subscriber subscriber) {
        this.owner = subscriber;
    }

    public Account(int number, int idSubscriber, int allFee, int state) {
        this.number = number;
        this.idSubscriber = idSubscriber;
        this.allFee = allFee;
        this.state = state;
    }

    public static int getCallFee() {
        return callFee;
    }

    public int getAllFee() {
        return allFee;
    }

    public int getState() {
        return state;
    }

    public int getNumber() {
        return number;
    }

    public int getIdSubscriber() {
        return idSubscriber;
    }

    public void setOwner(Subscriber owner) {
        this.owner = owner;
    }

    public Subscriber getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return idSubscriber == account.idSubscriber &&
                number == account.number &&
                getAllFee() == account.getAllFee() &&
                getState() == account.getState() &&
                Objects.equals(owner, account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, idSubscriber, number, getAllFee(), getState());
    }

    @Override
    public String toString() {
        return "Account{" +
                "idSubscriber=" + idSubscriber +
                ", number=" + number +
                ", allFee=" + allFee +
                ", state=" + state +
                '}';
    }
}
