package com.parking.parkinglot;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.ejb.CarsBean;

import java.io.IOException;

@WebServlet(name = "AddCarPhoto", value = "/AddCarPhoto")
@MultipartConfig(maxFileSize = 800 * 1024) // 800KB max
public class AddCarPhoto extends HttpServlet {

    private static final long MAX_FILE_SIZE = 800 * 1024; // 800KB max (space for SQL overhead)

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long carId = Long.parseLong(request.getParameter("id"));
        CarDto car = carsBean.findById(carId);
        request.setAttribute("car", car);
        request.getRequestDispatcher("/WEB-INF/pages/addCarPhoto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long carId = Long.parseLong(request.getParameter("car_id"));

        Part filePart = request.getPart("file");

        // Verifică dimensiunea fișierului
        if (filePart.getSize() > MAX_FILE_SIZE) {
            CarDto car = carsBean.findById(carId);
            request.setAttribute("car", car);
            request.setAttribute("errorMessage", "File too large! Maximum size allowed is 800 KB.");
            request.getRequestDispatcher("/WEB-INF/pages/addCarPhoto.jsp").forward(request, response);
            return;
        }

        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();
        byte[] fileContent = filePart.getInputStream().readAllBytes();

        carsBean.addPhotoToCar(carId, fileName, fileType, fileContent);

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}
