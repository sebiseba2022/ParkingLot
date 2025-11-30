package com.parking.parkinglot;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddCarServlet", value = "/AddCar")
public class AddCarServlet extends HttpServlet {

    @EJB
    private UsersBean usersBean;

    @EJB
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserDto> users = usersBean.findAllUsers();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Nu s-au putut încărca utilizatorii: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String license = request.getParameter("license_plate");
        String spot = request.getParameter("parking_spot");
        String ownerIdStr = request.getParameter("owner_id");

        Long ownerId = null;
        try {
            if (ownerIdStr != null && !ownerIdStr.isEmpty()) {
                ownerId = Long.valueOf(ownerIdStr);
            }
        } catch (NumberFormatException e) {
            // Ignorăm, ownerId va rămâne null
        }

        // Validare simplă
        if (license == null || license.isBlank() || spot == null || spot.isBlank() || ownerId == null) {
            request.setAttribute("error", "Toate câmpurile sunt obligatorii!");
            doGet(request, response);
            return;
        }

        try {
            carsBean.createCar(license.trim(), spot.trim(), ownerId);
            response.sendRedirect(request.getContextPath() + "/Cars");
        } catch (Exception ex) {
            request.setAttribute("error", "Eroare la salvarea mașinii: " + ex.getMessage());
            doGet(request, response);
        }
    }
}
