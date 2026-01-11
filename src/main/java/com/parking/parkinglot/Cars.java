package com.parking.parkinglot;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Cars", value = "/Cars")
@ServletSecurity
public class Cars extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);

        int numberOfFreeParkingSpots = 10;
        request.setAttribute("numberOfFreeParkingSpots", numberOfFreeParkingSpots);

        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificăm dacă request-ul vine de la un buton individual de Delete
        String deleteId = request.getParameter("delete_id");

        List<Long> carIds = new ArrayList<>();

        if (deleteId != null) {
            // Cazul 1: Ștergere individuală (s-a apăsat butonul roșu de pe rând)
            carIds.add(Long.parseLong(deleteId));
        } else {
            // Cazul 2: Ștergere multiplă (s-a apăsat butonul "Delete Selected" de sus)
            String[] carIdsAsString = request.getParameterValues("car_ids");
            if (carIdsAsString != null) {
                for (String carIdStr : carIdsAsString) {
                    carIds.add(Long.parseLong(carIdStr));
                }
            }
        }

        // Apelăm EJB-ul doar dacă avem ID-uri de șters
        if (!carIds.isEmpty()) {
            carsBean.deleteCarsByIds(carIds);
        }

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}