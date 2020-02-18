package by.gstu.servlets;

import by.gstu.phonestation.account.Account;
import by.gstu.phonestation.administrator.Administrator;
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
import java.util.List;

@WebServlet("/AdministratorServlet")
public class AdministratorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonOut;
        JSONArray jsonArrayOut;

        Administrator administrator = Administrator.getAdministrator();

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        JSONObject jsonIn = new JSONObject(stringBuffer.toString());

        switch (jsonIn.getString("action")) {
            case "add":
                Subscriber subscriberFromDb = Subscriber.getSubscriberByName(jsonIn.getString("name"));
                Subscriber subscriber = getSubscriberFromJSON(jsonIn);
                subscriber.createAccount();

                if (subscriber.getName().equals(subscriberFromDb.getName())) {
                    resp.getWriter().write("exist");
                } else {
                    administrator.addSubscriber(subscriber);
                }
                break;
            case "getSubscribers":
                jsonArrayOut = new JSONArray();
                List<Subscriber> subscribers = administrator.getAllSubscribers();
                for (int i = 0; i < subscribers.size(); i++) {
                    jsonOut = new JSONObject(subscribers.get(i));
                    jsonArrayOut.put(jsonOut);
                }
                resp.getWriter().write(jsonArrayOut.toString());
                break;
            case "deleteSubscribers":
                Subscriber subscriberDelete = getSubscriberFromJSON(jsonIn);
                subscriberDelete.setId(jsonIn.getInt("id"));
                administrator.deleteSubscriberById(subscriberDelete);
                break;
            case "getNegativeAccounts":
                List<Account> accounts = administrator.getNegativeAccounts();
                jsonArrayOut = new JSONArray();
                for (int i = 0; i < accounts.size(); i++) {
                    jsonOut = new JSONObject(accounts.get(i));
                    jsonArrayOut.put(jsonOut);
                }
                resp.getWriter().write(jsonArrayOut.toString());
                break;
            case "blockingSubscribers":
                Subscriber blockingSubscriber = getSubscriberFromJSON(jsonIn);
                blockingSubscriber.setId(jsonIn.getInt("id"));
                administrator.blockSubscriber(blockingSubscriber);
                break;
            case "blockAll":
                administrator.blockNegativeSubscribers();
                break;
        }
    }

    private Subscriber getSubscriberFromJSON(JSONObject json) {
        String name = json.getString("name");
        String address = json.getString("address");
        String blocking = json.getString("blocking");
        return new Subscriber(name, address, blocking);
    }
}


