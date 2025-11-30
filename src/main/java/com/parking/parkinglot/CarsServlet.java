package com.parking.parkinglot;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.ejb.CarsBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CarsServlet", value = "/Cars")
public class CarsServlet extends HttpServlet {

    @EJB
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);
        request.setAttribute("numberOfFreeParkingSpots", 10 - cars.size());
        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract car_ids from checkboxes
        String[] carIdsArray = request.getParameterValues("car_ids");

        if (carIdsArray != null && carIdsArray.length > 0) {
            // Convert String array to Long List
            List<Long> carIds = new ArrayList<>();
            for (String carIdStr : carIdsArray) {
                try {
                    carIds.add(Long.valueOf(carIdStr));
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }

            // Delete selected cars
            if (!carIds.isEmpty()) {
                carsBean.deleteCarsByIds(carIds);
            }
        }

        // Redirect to the same page to refresh the list
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}
