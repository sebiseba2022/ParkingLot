package com.parking.parkinglot;

import com.parking.parkinglot.ejb.UsersBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // read credentials
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        boolean ok;
        try {
            ok = usersBean.authenticate(username.trim(), password);
        } catch (Exception ex) {
            // forward with generic error to avoid leaking internals
            request.setAttribute("error", "Authentication failed (server error).");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        if (ok) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username.trim());
            // redirect to application root (menu.jsp will now show Welcome)
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("error", "Username or password incorrect");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
