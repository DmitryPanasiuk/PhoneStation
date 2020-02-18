package by.gstu.servlets;

import by.gstu.phonestation.administrator.Administrator;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet getting subscriber by account number
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name;
        String password;
        int level;
        Administrator administrator = Administrator.getAdministrator();

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        JSONObject jsonObject = new JSONObject(stringBuffer.toString());

        name = jsonObject.getString("name");
        password = jsonObject.getString("password");

        level = administrator.checkPassword(name, password);

        switch (level) {
            case 1:
                resp.getWriter().write("/admin.html");
                break;
            case 2:
                resp.getWriter().write("/user.html");
                break;
            default:
                resp.sendError(401);
        }

    }
}