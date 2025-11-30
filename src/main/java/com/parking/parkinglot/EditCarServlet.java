package com.parking.parkinglot;

import com.parking.parkinglot.common.CarDto;
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

@WebServlet(name = "EditCarServlet", value = "/EditCar")
public class EditCarServlet extends HttpServlet {

    @EJB
    private UsersBean usersBean;

    @EJB
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract car id from request parameter
        String carIdStr = request.getParameter("id");

        if (carIdStr == null || carIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Cars");
            return;
        }

        try {
            Long carId = Long.valueOf(carIdStr);

            // Find car by id
            CarDto car = carsBean.findCarById(carId);

            if (car == null) {
                response.sendRedirect(request.getContextPath() + "/Cars");
                return;
            }

            // Find all users for the dropdown
            List<UserDto> users = usersBean.findAllUsers();

            // Inject as attributes
            request.setAttribute("car", car);
            request.setAttribute("users", users);

            // Forward to editCar.jsp
            request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Cars");
        } catch (Exception ex) {
            request.setAttribute("error", "Eroare la încărcarea mașinii: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/Cars");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract car id from hidden input
        String carIdStr = request.getParameter("car_id");
        String license = request.getParameter("license_plate");
        String spot = request.getParameter("parking_spot");
        String ownerIdStr = request.getParameter("owner_id");

        Long carId = null;
        Long ownerId = null;

        try {
            if (carIdStr != null && !carIdStr.isEmpty()) {
                carId = Long.valueOf(carIdStr);
            }
            if (ownerIdStr != null && !ownerIdStr.isEmpty()) {
                ownerId = Long.valueOf(ownerIdStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID invalid!");
            doGet(request, response);
            return;
        }

        // Validation
        if (license == null || license.isBlank() || spot == null || spot.isBlank() || ownerId == null || carId == null) {
            request.setAttribute("error", "Toate câmpurile sunt obligatorii!");
            doGet(request, response);
            return;
        }

        try {
            // Update the car
            carsBean.updateCar(carId, license.trim(), spot.trim(), ownerId);

            // Redirect to Cars page
            response.sendRedirect(request.getContextPath() + "/Cars");
        } catch (Exception ex) {
            request.setAttribute("error", "Eroare la actualizarea mașinii: " + ex.getMessage());
            doGet(request, response);
        }
    }
}
