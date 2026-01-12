package com.parking.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.parking.parkinglot.ejb.CarsBean;

import java.io.IOException;

@WebServlet(name = "DeleteCarPhoto", value = "/DeleteCarPhoto")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
public class DeleteCarPhoto extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carIdAsString = request.getParameter("id");

        // Verificăm dacă avem un ID valid ca să nu primim eroarea 500
        if (carIdAsString != null && !carIdAsString.isEmpty()) {
            try {
                Long carId = Long.parseLong(carIdAsString);
                carsBean.deletePhoto(carId);
            } catch (NumberFormatException e) {
                // Dacă ID-ul nu e număr, ignorăm cererea
            }
        }

        // Ne întoarcem la lista de mașini
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}