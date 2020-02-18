package by.gstu.phonestation.service;

import by.gstu.phonestation.subscriber.Subscriber;

import java.util.Objects;

/**
 * Class describes service and its behavior
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public class Service {
    private int id;
    private ServiceType name;
    private Subscriber owner;

    public Service(int id, ServiceType name, Subscriber subscriber) {
        this.id = id;
        this.name = name;
        this.owner = subscriber;
    }

    public ServiceType getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return getName() == service.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), owner);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
