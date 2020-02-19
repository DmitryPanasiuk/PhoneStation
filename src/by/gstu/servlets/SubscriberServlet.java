package by.gstu.servlets;

import by.gstu.phonestation.administrator.Administrator;
import by.gstu.phonestation.service.Service;
import by.gstu.phonestation.service.ServiceType;
import by.gstu.phonestation.subscriber.Subscriber;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for subscriber actions
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
@WebServlet("/SubscriberServlet")
public class SubscriberServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name;
        String password;
        List<Service> allServices;
        List<Service> subscriberServices;
        JSONObject jsonOut;
        JSONArray jsonArrayOut;

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        JSONObject jsonIn = new JSONObject(stringBuffer.toString());
        name = jsonIn.getString("name");
        Subscriber subscriber = Subscriber.getSubscriberByName(name);
        Administrator administrator = Administrator.getAdministrator();

        switch (jsonIn.getString("infoType")) {
            case "info":
                jsonOut = new JSONObject(subscriber);
                jsonArrayOut = new JSONArray();
                jsonArrayOut.put(jsonOut);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonArrayOut.toString());
                break;
            case "changePassword":
                password = jsonIn.getString("password");
                subscriber.changePassword(password);
                break;
            case "services":
                allServices = subscriber.receiveAllServices();
                subscriberServices = subscriber.getServices();
                jsonArrayOut = new JSONArray();

                for (int i = 0; i < allServices.size(); i++) {
                    boolean checked = true;
                    jsonOut = new JSONObject();
                    if (subscriberServices.indexOf(allServices.get(i)) == -1) {
                        checked = false;
                    }
                    jsonOut.put("checked", checked);
                    jsonOut.put("id", allServices.get(i).getId());
                    jsonOut.put("name", allServices.get(i).getName().getName());
                    jsonOut.put("cost", allServices.get(i).getName().getCost());
                    jsonArrayOut.put(jsonOut);
                }
                resp.setContentType("application/json");
                resp.getWriter().write(jsonArrayOut.toString());
                break;
            case "change_services":
                JSONArray array = jsonIn.getJSONArray("data");
                List<Service> services = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    int id = obj.getInt("id");
                    ServiceType serviceName = ServiceType.defineType(obj.getString("name"));
                    services.add(new Service(id, serviceName, subscriber));
                }
                subscriber.changeServicesList(services);
                break;
            case "pay":
                int amount = jsonIn.getInt("amount");
                subscriber.pay(amount);
                administrator.unblockPositiveSubscribers();
                break;
        }
    }
}
