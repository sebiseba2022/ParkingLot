package com.parking.parkinglot;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/User")
public class UserServlet extends HttpServlet {

    @EJB
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();

        // Load permissions for each user
        Map<String, List<String>> userPermissions = new HashMap<>();
        for (UserDto user : users) {
            userPermissions.put(user.getUsername(), usersBean.getUserPermissions(user.getUsername()));
        }

        request.setAttribute("users", users);
        request.setAttribute("userPermissions", userPermissions);
        request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String permission = request.getParameter("permission");
        String action = request.getParameter("action");

        if ("grant".equals(action)) {
            usersBean.grantPermission(username, permission);
        } else if ("revoke".equals(action)) {
            usersBean.revokePermission(username, permission);
        }

        // Redirect to refresh the page
        response.sendRedirect(request.getContextPath() + "/User");
    }
}

